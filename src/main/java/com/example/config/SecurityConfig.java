package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.handler.CustomLogoutSuccessHandler;
import com.example.handler.CustomLoginSuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration //환경설정파일, 서버가 구동되기 전에 호출됨
@EnableWebSecurity //시큐리티를 사용
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    final UserDetailsService userDetailsService; //구현한 서비스 SecurityServiceImpl.java

    @Bean //객체 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("SecurityConfig => {}","start filter chain");

        //권한설정
        http.authorizeRequests()
            .antMatchers("/customer/join.bubble").permitAll()
            .antMatchers("/washing/join.bubble").permitAll()
            .antMatchers("/admin/join.bubble").permitAll()
            .antMatchers("/admin", "/admin/*").hasAuthority("ROLE_ADMIN") //주소가 9090/bubble_bumul/admin ~~ 모든것
            .antMatchers("/washing", "/washing/*").hasAnyAuthority("ROLE_ADMIN","ROLE_WASHING")
            .antMatchers("/customer","/customer/*").hasAnyAuthority("ROLE_CUSTOMER","ROLE_ADMIN","ROLE_WASHING")

            .anyRequest().permitAll();

        //403페이지 설정(접근권한 불가 시 표시할 화면)
        http.exceptionHandling().accessDeniedPage("/403page.bubble");
        
        //로그인 처리
        http.formLogin()
            .loginPage("/login.bubble")
            .loginProcessingUrl("/loginaction.bubble")  //action은
            .usernameParameter("id") //input type name="id"
            .passwordParameter("password") //input type name="password"
            .successHandler(new CustomLoginSuccessHandler())
            // .defaultSuccessUrl("/home.bubble") //로그인 성공시 이동할 페이지
            .permitAll();

        //로그아웃 처리(GET은 안됨 반드시 POST로 호출해야 됨)
        http.logout()
            .logoutUrl("/logout.bubble")
            // .logoutSuccessUrl("/home.bubble")
            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll();

        //post는 csrf를 전송해야하지만, 주소가 /api 로 시작하는 모든 url은 csrf가 없어도 허용하도록 설정
        http.csrf().ignoringAntMatchers("/api/**");

        // http.userDetailsService(userDetailsService); //서비스 등록(자동 등록됨 생략 가능)

        
        return http.build();
    }

    //정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정 ,resources/static은 시큐리티 적용받지 않음
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //회원가입에서 사용했던 암호화 알고리즘 설정, 로그인에서도 같은것을 사용해야 하니까?
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
