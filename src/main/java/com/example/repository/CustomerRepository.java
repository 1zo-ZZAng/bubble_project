package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	// 아이디 중복 확인
    int countByid(String id);

    // 아이디 찾기(이름, 전화번호, 이메일이 모두 일치해야함)
    Customer findByNameAndPhoneAndEmail(String name, String phone, String email);

    // 비밀번호 찾기(아이디, 이메일이 모두 일치해야함)
    Customer findByIdAndEmail(String id, String email);

    // 소셜 로그인(카카오) - 이메일 유무 확인
    int countByEmail(String email);

    // 소셜 로그인(카카오) - 이메일로 회원 1명 조회
    Customer findByEmail(String email);
}
