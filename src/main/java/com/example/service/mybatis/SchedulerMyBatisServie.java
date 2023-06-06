package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;

@Service
public interface SchedulerMyBatisServie {

    //스케쥴러 - 예약 조회 (reserve)
    public List<Reserve> selectReserveListSch();

    //스케쥴러 - 예약 업데이트 (reservation)
    public int updateReserveState(String rvno);


    
}
