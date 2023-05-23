package com.example.service.jpa;

import org.springframework.stereotype.Service;

import com.example.entity.Washing;

@Service
public interface WashingService {


	// findById
	Washing findById(String id);

	//회원가입
	public int joinWashing(Washing obj);

	//로그인
	public int loginWashing(Washing obj);

	//탈퇴
	public int deleteWashing(Washing obj);

	//정보수정
	public int updateWashing(Washing obj);

	//비밀번호 수정
	public int pwupdateWashing(Washing obj);

	
	// 아이디 중복 확인
	public int washingIDCheck(String id);

	// 업체 비번 찾기
	public Washing findWashingPw(Washing washing);

	



    
}
