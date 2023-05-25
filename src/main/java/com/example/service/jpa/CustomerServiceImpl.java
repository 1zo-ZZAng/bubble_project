package com.example.service.jpa;

import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository cRepository;

	// 회원가입
	@Override
	public Customer insertCustomer(Customer customer) {
		try {
			return cRepository.save(customer);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 회원 1명 조회
	@Override
	public Customer selectCustomerOne(String id) {
		try {
			return cRepository.findById(id).orElse(null);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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

	// 비밀번호 찾기(아이디, 이메일이 모두 일치해야함)
	@Override
	public Customer selectCustomerPw(String id, String email) {
		try {
			return cRepository.findByIdAndEmail(id, email);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 소셜 로그인(카카오) - 이메일 유무 확인
	@Override
	public int countCustomerEmailCheck(String email) {
		try {
			return cRepository.countByEmail(email);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 소셜 로그인(카카오) - 이메일로 회원 1명 조회
	@Override
	public Customer selectCustomerEmail(String email) {
		try {
			return cRepository.findByEmail(email);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
