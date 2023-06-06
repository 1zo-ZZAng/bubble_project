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
    
    
    //스케쥴러 조회
    @Override
    public List<Reserve> selectReserveListSch() {
        try {

            return rMapper.selectReserveListSch();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //스케줄러 업데이트
    @Override
    public int updateReserveState(String rvno) {
        try {

            return rMapper.updateReserveState(rvno);
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
