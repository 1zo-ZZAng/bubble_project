package com.example.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CustomLoginFailHandler implements AuthenticationFailureHandler {
    

    @Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		
		
		log.info("login fail handler로 오긴 옴");

		
		if(exception instanceof BadCredentialsException) {
			request.setAttribute("failmsg", "아이디 또는 비밀번호가 틀렸습니다");
		} else if (exception instanceof UsernameNotFoundException) {
			request.setAttribute("failmsg", "존재하지 않는 계정입니다");
		} else{
			request.setAttribute("failmsg", "알 수 없는 오류");
		}


		//여기 문제인것같음 --- 다시 확인해라
		//로그인 페이지로 다시 포워딩
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
		dispatcher.forward(request, response);
		

		
		
	}



	// private void setDefaultFailureUrl(String defaultFailureUrl) {
	// 	Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), () -> "'" + defaultFailureUrl + "'is not a vaild redirect URL");
	// }


	
    
    
}
