package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	// 아이디 중복 확인
    int countByid(String id);

    // 고객 비밀번호 찾기
    String findByPassword(Customer obj);

    // 고객 등급 표시
    long findByGrade(String id);
}
