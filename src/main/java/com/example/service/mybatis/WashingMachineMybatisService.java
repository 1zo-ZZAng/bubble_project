package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface WashingMachineMybatisService {
    // 예약 - 사업자번호, 기기명 => 보유 중인 기기번호 반환
    public List<Long> selectmachineno(String wnumber, String type);
}
