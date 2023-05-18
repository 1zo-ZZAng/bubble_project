package com.example.service.jpa;

import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository cRepository;

	// 아이디 중복 확인
    @Override
    public int selectCustomerIDCheck(String id) {
        try {
			return cRepository.countByid(id);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }

	// 고객 비밀번호 찾기
    @Override
    public String selectCustomerPw(Customer obj) {
        try {
			return cRepository.findByPassword(obj);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

	// 고객 등급 표시
    @Override
    public long selectCustomerGrade(String id) {
        try {
			return cRepository.findByGrade(id);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
    
}
