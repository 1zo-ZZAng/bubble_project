package com.example.service.jpa;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Admin;
import com.example.entity.Washingmachine;

@Service
public interface AdminService {

    //로그인
    public Admin selectAdmin(String id);

    //회원가입된 업체 승인


    //업체별 매출 조회


    //업체리스트 전체 조회
    public List<Washing> selectWList();

    //업체별 보유기기목록 조회
    public List<WashingMachine> selectWmList(@Param("wnumber") String wnumber);


    //회원가입되어있는 회원 리스트
    

    //전체 게시판 조회


    //게시판 조회 및 삭제


    public String selectWashingNameOne(@Param("wnumber") String wnumber);
}

