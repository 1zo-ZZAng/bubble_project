package com.example.service.jpa;

import org.springframework.stereotype.Service;

import com.example.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    
    final AdminRepository aRepository;

}
