package com.example.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

		
		
		// log.info("login fail handler로 오긴 옴");

		
		// if(exception instanceof BadCredentialsException) {
		// 	request.setAttribute("failmsg", "아이디 또는 비밀번호가 틀렸습니다");
		// } else if (exception instanceof UsernameNotFoundException) {
		// 	request.setAttribute("failmsg", "존재하지 않는 계정입니다");
		// } else{
		// 	request.setAttribute("failmsg", "알 수 없는 오류");
		// }


		// //여기 문제인것같음 --- 다시 확인해라
		// //로그인 페이지로 다시 포워딩
		// RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
		// dispatcher.forward(request, response);
		

		String errorMessage;
		if (exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
		} else if (exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
		} else {
			errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
		}
		
		setDefaultFailureUrl("/customer/login?error=true&exception="+errorMessage);

	}

	private void setDefaultFailureUrl(String string) {
	}



	// private void setDefaultFailureUrl(String defaultFailureUrl) {
	// 	Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), () -> "'" + defaultFailureUrl + "'is not a vaild redirect URL");
	// }


	
    
    
}
