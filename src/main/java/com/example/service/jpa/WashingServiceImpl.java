package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Reserve;
import com.example.entity.Washing;
import com.example.repository.ReserveRepository;
import com.example.repository.WashingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WashingServiceImpl implements WashingService {
    
    final WashingRepository wRepository;
    final ReserveRepository rRepository;

    //아이디 중복 확인
    @Override
    public int washingIDCheck(String id) {
        try {

            int ret = wRepository.countById(id);

            if(ret == 0){
                return 0;
            }else{
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //회원가입
    @Override
    public Washing joinWashing(Washing obj) {
        try {

            Washing ret = wRepository.save(obj);

            return ret;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //아이디 찾기
    @Override
    public Washing findId(String ceo, String email) {
        try {

            return wRepository.findByCeoAndEmail(ceo, email);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //비밀번호 찾기
    @Override
    public Washing findPassword(String id, String email) {
        try {
            
            return wRepository.findByIdAndEmail(id, email);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    



    
}
