package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Reserve;

@Service
public interface ReserveService {


    // == 업체 ==

    //예약 내역 조회(업체별)
	public List<Reserve> selectReserve(String wid);

    // 예약 내역 조회 - state와 wid를 기준으로
	public List<Reserve> selectReserveList(String wid, String state);
    
}
