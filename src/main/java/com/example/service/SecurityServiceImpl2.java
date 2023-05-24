package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.Washing;
import com.example.repository.WashingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// (1) 로그인에서 로그인 버튼 -> loadUserByusername으로 이메일 정보를 넘김
// student2 테이블과 연동되는 서비스
@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityServiceImpl2 implements UserDetailsService {
    final String format = "SecurityServiceImpl => {}";
    final WashingRepository wRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // (2) 아이디를 이용해서 Washing 테이블에서 정보를 꺼낸 후 User 타입으로 변환해서 리턴하면
        // 시큐리티가 비교 후에 로그인 처리를 자동으로 수행함
        log.info(format, username);

        Washing obj = wRepository.findById(username).orElse(null);
        

        
        if (obj != null) { // 아이디가 있는 경우
            return User.builder()
                        .username(obj.getId())
                        .password(obj.getPassword())
                        .roles("WASHING")
                        .build();
        }

        // 아이디가 없는 경우는 User로 처리
        return User.builder()
            .username("_")
            .password("_")
            .roles("_")
            .build();
    }

    
}
