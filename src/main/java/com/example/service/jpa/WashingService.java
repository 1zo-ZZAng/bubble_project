package com.example.service.jpa;

import org.springframework.stereotype.Service;

import com.example.entity.Washing;

@Service
public interface WashingService {


	//회원가입
	public Washing joinWashing(Washing obj);
	
	// 아이디 중복 확인
	public int washingIDCheck(String id);

	// 업체 비번 찾기
	public Washing findWashingPw(Washing washing);

	



    
}
