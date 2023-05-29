package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.MachineCount;
import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Admin;
import com.example.entity.Customer;
import com.example.entity.Washingmachine;
import com.example.mapper.AdminMapper;
import com.example.repository.AdminRepository;
import com.example.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    
    final AdminRepository aRepository;
    final AdminMapper aMapper;


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
    public List<Washing> selectWlistUnchecked(int chk) {
       try {
            return aMapper.selectWlistUnchecked(chk);
       } catch (Exception e) {
        e.printStackTrace();
        return null;
       }
    
    }

    @Override
    public List<MachineCount> selectMCount(String wnumber) {
        try {
            return aMapper.selectMCount(wnumber);
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

    @Override
    public List<String> selctChkList() {
        try {
            return aMapper.selctChkList();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }






    



}
