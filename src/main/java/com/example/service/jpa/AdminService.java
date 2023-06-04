package com.example.service.jpa;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.dto.AdminChkList;
import com.example.dto.MachineCount;
import com.example.dto.Reserve;
import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Customer;
import com.example.entity.WashingCheck;

@Service
public interface AdminService {

    //회원가입된 업체 승인


    //업체별 매출 조회


    //업체리스트 전체 조회
    public List<Washing> selectWList();

    //업체리스트 전체 조회(제휴 유무 x)
    public List<Washing> selectAllWasingList();

    //업체별 오늘의 예약 건
    public int todayRVWashingCount(String wnumber);

    //(1)업체별 보유기기목록 조회
    public List<WashingMachine> selectWmList(@Param("wnumber") String wnumber);

    //(1-1)업체별 보유기기 목록 조회
    public List<MachineCount> selectMCount(@Param("wnumber") String wnumber);
    public String selectWashingNameOne(@Param("wnumber") String wnumber);

    //회원목록
    public List<Customer> selectCustomer();

    //제휴 업체 수
    public int washingCount();

    //예약 날짜 목록 최신순
    public Reserve selectRvdateList();

    //예약등록 날짜 목록 최신순
    public Reserve selectRdateList();


    //업체승인 유무 select박스
    public List<WashingCheck> selctChkList();


    //---------------------------차트------------------------------
    
    //차트의 월별 선택
    public List<Reserve> selectMonthBox();


    ///월 총 매출 조회(오늘의 날짜 포함) 
    public List<Map<String,Object>> selectMonthAllSales();

    //월- 일별 총 매출 조회(오늘의 날짜 포함) ex)2023-04 (1~30)
    public List<Map<String,Object>> selectMonthChart(@Param("selectdate") String selectdate);
    
    //월-일별 업체별 매출 조회
    public List<Map<String,Object>> selectMonthDateWashingChart(@Param("wnumber") String wnumber);

    //월별 업체별 매출 조회
   public List<Map<String,Object>> selectMonthWashingChart(@Param("wnumber") String wnumber);


    //오늘 총 예약 건 

    public int todayRVCount();


    //월별 매출 조회 box
    public List<String> selectMonthBoxList(@Param("wnumber") String wnumber);





    //전체 게시판 조회


    //게시판 조회 및 삭제

    
    
}

