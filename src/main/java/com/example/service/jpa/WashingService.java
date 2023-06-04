package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Reserve;
import com.example.entity.Washing;

@Service
public interface WashingService {


	//회원가입
	public Washing joinWashing(Washing obj);
	
	// 아이디 중복 확인
	public int washingIDCheck(String id);

	//아이디 찾기
	public Washing findId(String ceo, String email);

	//비밀번호 찾기
	public Washing findPassword(String id, String email);





	



    
}
