package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.handler.CustomLoginSuccessHandler;
import com.example.handler.CustomLogoutSuccessHandler;
import com.example.service.SecurityServiceImpl1;
import com.example.service.SecurityServiceImpl2;
import com.example.service.SecurityServiceImpl3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration // 환경설정파일 -> 서버가 구동되기 전에 호출됨
@EnableWebSecurity // 시큐리티를 사용
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    // Customer 테이블과 연동되는 서비스
    final SecurityServiceImpl1 customerTableService;

    // Washing 테이블과 연동되는 서비스
    final SecurityServiceImpl2 washingTableService; 
    
    // Admin 테이블과 연동되는 서비스
    final SecurityServiceImpl3 adminTableService; 
    
    @Bean
    @Order(value = 1)
    public SecurityFilterChain filterChain3(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain");

        // 127.0.0.1:9090/bubble_bumul/customer/login.bubble
        // 127.0.0.1:9090/bubble_bumul/customer/loginaction.bubble
        // 해당 주소들만 필터함
        http.antMatcher("/admin/login.bubble")
            .antMatcher("/admin/loginaction.bubble")
            .authorizeRequests().anyRequest().authenticated().and();

        // (1) 로그인 처리
        http.formLogin()
            .loginPage("/admin/login.bubble") // 로그인하는 get 주소는?
            .loginProcessingUrl("/admin/loginaction.bubble") // action은? => login.html
            .usernameParameter("id") // 아이디의 name값은? => login.html
            .passwordParameter("password") // 암호의 name값은? => login.html
            .successHandler(new CustomLoginSuccessHandler())
            // .defaultSuccessUrl("/home.bubble") // 로그인 성공시 이동할 페이지
            .permitAll();  

        // 서비스 등록 (자동 등록됨. 생략가능)
        http.userDetailsService(adminTableService);

        return http.build();
    }

    @Bean
    @Order(value = 2)
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain");

        // 127.0.0.1:9090/bubble_bumul/washing/login.bubble
        // 127.0.0.1:9090/bubble_bumul/washing/loginaction.bubble
        // 해당 주소들만 필터함
        http.antMatcher("/washing/login.bubble")
            .antMatcher("/washing/loginaction.bubble")
            .authorizeRequests().anyRequest().authenticated().and();

        // (1) 로그인 처리
        http.formLogin()
            .loginPage("/washing/login.bubble") // 로그인하는 get 주소는?
            .loginProcessingUrl("/washing/loginaction.bubble") // action은? => login.html
            .usernameParameter("id") // 아이디의 name값은? => login.html
            .passwordParameter("password") // 암호의 name값은? => login.html
            .successHandler(new CustomLoginSuccessHandler())
            // .failureHandler(new CustomLoginFailHandler())
            // .defaultSuccessUrl("/washing/home.bubble") // 로그인 성공시 이동할 페이지
            .permitAll();  

        // 서비스 등록 (자동 등록됨. 생략가능)
        http.userDetailsService(washingTableService);

        return http.build();
    }

    @Bean // 객체를 생성(자동으로 호출됨)
    @Order(value = 3) // 마지막 숫자로 변경(로그인 해야하는 테이블의 개수)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain");

        // 권한 설정
        http.authorizeRequests()
            .antMatchers("/customer/home.bubble").permitAll()
            .antMatchers("/customer/join.bubble").permitAll()
            .antMatchers("/customer/kakaojoin.bubble").permitAll()
            .antMatchers("/customer/login.bubble").permitAll()
            .antMatchers("/customer/findid.bubble").permitAll()
            .antMatchers("/customer/showid.bubble").permitAll()
            .antMatchers("/customer/findpw.bubble").permitAll()
            .antMatchers("/washing/join.bubble").permitAll()
            .antMatchers("/washing/login.bubble").permitAll()
            .antMatchers("/washing/findid.bubble").permitAll()
            .antMatchers("/washing/findpw.bubble").permitAll()
            .antMatchers("/washing/showid.bubble").permitAll()
            .antMatchers("/washing/bncheck.bubble").permitAll()
            .antMatchers("/admin/join.bubble").permitAll()
            .antMatchers("/admin/login.bubble").permitAll()
            .antMatchers("/admin", "/admin/*").hasAuthority("ROLE_ADMIN") // 주소가 9090/bubble_bumul/admin ~ 인 모든 것
            .antMatchers("/washing", "/washing/*", "/machine/*","/wboard/*", "/washingsales/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_WASHING")
            .antMatchers("/customer", "/customer/*", "/reserve/*").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")
            .anyRequest().permitAll();
            
        // 403 페이지 설정(접근권한 불가 시 표시할 화면)
        http.exceptionHandling().accessDeniedPage("/403page.bubble");

        // 로그인, 로그아웃, 권한 설정 ...
        // (1) 로그인 처리
        http.formLogin()
            .loginPage("/customer/login.bubble") // 로그인하는 get 주소는?
            .loginProcessingUrl("/customer/loginaction.bubble") // action은? => login.html
            .usernameParameter("id") // 아이디의 name값은? => login.html
            .passwordParameter("password") // 암호의 name값은? => login.html
            .successHandler(new CustomLoginSuccessHandler())
            // .defaultSuccessUrl("/home.bubble") // 로그인 성공시 이동할 페이지
            .permitAll();
        
        // (2) 로그아웃 처리 (get은 안됨. 반드시 post로 호출해야 됨)
        http.logout()
            .logoutUrl("/logout.bubble") // 로그아웃하는 주소
            // .logoutSuccessUrl("/home.bubble") // 로그아웃 성공시 이동할 페이지
            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll();

        // post는 csrf를 전송해야하지만, 주소가 /api로 시작하는 모든 url은 csrf가 없어도 허용하도록 설정
        http.csrf().ignoringAntMatchers("/api/**");

        // 서비스 등록 (자동 등록됨. 생략가능)
        http.userDetailsService(customerTableService);

        return http.build();
    }

    // 정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정, resources/static은 시큐리티 적용받지 않음
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 회원가입에서 사용했던 암호화 알고리즘 설정, 로그인에서도 같은 것을 사용해야 하니까?
    @Bean // 서버 구동시 자동으로 실행됨
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
