package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Admin;
import com.example.entity.Washingmachine;
import com.example.mapper.AdminMapper;
import com.example.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    
    final AdminRepository aRepository;
    final AdminMapper aMapper;

    //관리자 로그인
    @Override
    public Admin selectAdmin(String id) {
        try {
            return aRepository.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Washing> selectWList() {
        try {
            return aMapper.selectWList();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<WashingMachine> selectWmList(String wnumber) {
       try {
            return aMapper.selectWmList(wnumber);
            
       } catch (Exception e) {
        e.printStackTrace();
        return null;
       }
    }

    @Override
    public String selectWashingNameOne(String wnumber) {
        try {
            return aMapper.selectWashingNameOne(wnumber);
            
       } catch (Exception e) {
        e.printStackTrace();
        return null;
       }
    }

    



}
