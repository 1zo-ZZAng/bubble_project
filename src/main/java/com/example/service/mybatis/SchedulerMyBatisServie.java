package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;

@Service
public interface SchedulerMyBatisServie {

    //스케쥴러
    public List<Reserve> selectReserveListSch();


    
}
