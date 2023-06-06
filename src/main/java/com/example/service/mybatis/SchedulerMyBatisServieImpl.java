package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;
import com.example.mapper.ReserveMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerMyBatisServieImpl implements SchedulerMyBatisServie{
    
    final ReserveMapper rMapper;
    
    
    //스케줄러
    @Override
    public List<Reserve> selectReserveListSch() {
        try {

            return rMapper.selectReserveListSch();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
