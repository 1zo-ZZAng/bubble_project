package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.AdminChkList;

@Service
public interface AdminListMybatisService {
   //업체승인 유무 select박스 - chk 값
    public List<String> selectBoxList();

    //selected된 값에 따른 업체 리스트
    public List<AdminChkList> selectWlistUnchecked(String chk);

    //승인 대기 -> 승인 
    public int updateChk(String wnumber);
}
