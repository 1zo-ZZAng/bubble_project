package com.example.service.jpa;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.dto.MachineCount;
import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Admin;
import com.example.entity.Customer;
import com.example.entity.Washingmachine;

@Service
public interface AdminService {

    //회원가입된 업체 승인


    //업체별 매출 조회


    //업체리스트 전체 조회
    public List<Washing> selectWList();

    //(1)업체별 보유기기목록 조회
    public List<WashingMachine> selectWmList(@Param("wnumber") String wnumber);

    //(1-1)업체별 보유기기 목록 조회
    public List<MachineCount> selectMCount(@Param("wnumber") String wnumber);
    public String selectWashingNameOne(@Param("wnumber") String wnumber);

    
    //승인 대기/완료 업체 목록
    public List<Washing> selectWlistUnchecked(@Param("chk") String chk);


    //업체승인 유무 select박스
    public List<String> selctChkList();


    //전체 게시판 조회


    //게시판 조회 및 삭제

    
    
}

