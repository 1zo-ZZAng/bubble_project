package com.example.service.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.SendMail;
import com.example.entity.Customer;

@Service
public interface CustomerService {
	// 회원가입
	public Customer insertCustomer(Customer customer);

	// 회원 1명 조회
	public Customer selectCustomerOne(String id);

	// 아이디 중복 확인 => 1 : 중복 O, 사용 불가능 / 0 : 중복 X, 사용 가능
	public int selectCustomerIDCheck(@Param("id") String id);

	// 아이디 찾기(이름, 전화번호, 이메일, 생년월일이 모두 일치해야함)
	public Customer selectCustomerId(@Param("name") String name,
								     @Param("phone") String phone,
								     @Param("email") String email);
	
	// 비밀번호 찾기(아이디, 이메일이 모두 일치해야함)
	public Customer selectCustomerPw(@Param("id") String id, @Param("email") String email);

	// 소셜 로그인(카카오) - 이메일 유무 확인
	public int countCustomerEmailCheck(String email);

	// 소셜 로그인(카카오) - 이메일로 회원 1명 조회
	public Customer selectCustomerEmail(String email);
}
