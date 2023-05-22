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

	// 아이디 찾기(이름, 전화번호, 이메일, 생년월일이 모두 일치해야함)
	@Override
	public Customer selectCustomerId(String name, String phone, String email) {
		try {
			return cRepository.findByNameAndPhoneAndEmail(name, phone, email);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
