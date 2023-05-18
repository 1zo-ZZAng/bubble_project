package com.example.service.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.entity.Customer;

@Service
public interface CustomerService {
	// 아이디 중복 확인 => 1 : 중복 O, 사용 불가능 / 0 : 중복 X, 사용 가능
	public int selectCustomerIDCheck(@Param("id") String id);
	
	// 고객 비밀번호 찾기
	public String selectCustomerPw(@Param("obj") Customer obj);
	
	// 고객 등급 표시(조회)
	public long selectCustomerGrade(@Param("id") String id);
}
