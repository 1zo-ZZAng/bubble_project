package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.handler.CustomLoginSuccessHandler;
import com.example.handler.CustomLogoutSuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration //환경설정파일, 서버가 구동되기 전에 호출됨
@EnableWebSecurity //시큐리티를 사용
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    // 고객 테이블 (1)
    // 업체 테이블 (2)
    // 관리자 테이블 (3)
    
    final UserDetailsService userDetailsService; //구현한 서비스 SecurityServiceImpl.java

    

    //고객
    @Bean   // 객체를 생성함. (자동으로 호출됨.)
    @Order(value = 1) // 순서를 먼저 설정 //가장먼저 처리
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain2");

        // 127.0.0.1:9090/ROOT/student2/login.do
        // 127.0.0.1:9090/ROOT/student2/loginaction.do
        // 127.0.0.1:9090/ROOT/student2/logout.do
        // 위의 두개의 주소만 필터함.
        http.antMatcher("/student2/login.do")
            .antMatcher("/student2/loginaction.do") 
            .authorizeRequests()
            .anyRequest().authenticated().and();

        // 로그인 처리
        http.formLogin()
            .loginPage("/student2/login.do")
            .loginProcessingUrl("/student2/loginaction.do")
            .usernameParameter("id") //login.html의 email name값
            .passwordParameter("password") // login.html의 password name값
            .defaultSuccessUrl("/student2/home.do")
            .permitAll();

        //로그아웃 처리는 한번만 하면 됨 밑에서 굳이 여기서 다 안해도 됨


        http.userDetailsService(student2TableService);
        return http.build();
    }


    //업체
    @Bean   // 객체를 생성함. (자동으로 호출됨.)
    @Order(value = 2) // 순서를 먼저 설정 //가장먼저 처리
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain2");

        // 127.0.0.1:9090/ROOT/student2/login.do
        // 127.0.0.1:9090/ROOT/student2/loginaction.do
        // 127.0.0.1:9090/ROOT/student2/logout.do
        // 위의 두개의 주소만 필터함.
        http.antMatcher("/student2/login.do")
            .antMatcher("/student2/loginaction.do") 
            .authorizeRequests()
            .anyRequest().authenticated().and();

        // 로그인 처리
        http.formLogin()
            .loginPage("/student2/login.do")
            .loginProcessingUrl("/student2/loginaction.do")
            .usernameParameter("id") //login.html의 email name값
            .passwordParameter("password") // login.html의 password name값
            .defaultSuccessUrl("/student2/home.do")
            .permitAll();

        //로그아웃 처리는 한번만 하면 됨 밑에서 굳이 여기서 다 안해도 됨


        http.userDetailsService(student2TableService);
        return http.build();
    }




    //관리자
    @Bean //객체 생성
    @Order(value=3)
    public SecurityFilterChain filterChain3(HttpSecurity http) throws Exception{
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
