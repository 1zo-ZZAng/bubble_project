package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mapper.WashingMachineMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WashingMachineMybatisServiceImpl implements WashingMachineMybatisService {
    final WashingMachineMapper wmMapper;

    // 예약 - 사업자번호, 기기명 => 보유 중인 기기번호 반환
    @Override
    public List<Long> selectmachineno(String wnumber, String type) {
        try {
            return wmMapper.selectmachineno(wnumber, type);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
