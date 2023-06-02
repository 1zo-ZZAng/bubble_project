package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.WashingMachine;
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

    // 예약 - 사업자번호, 기기명, 기기별 번호 => no 기기번호(시퀀스) 반환
    @Override
    public Long selectWashingmachineNo(String wnumber, String type, Long typeno) {
        try {
            return wmMapper.selectWashingmachineNo(wnumber, type, typeno);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(-1);
        }
    }
    
    // 예약 - 사업자번호로 업체명, 업체주소, 업체전화번호, 가격, 이용시간 조회
    @Override
    public WashingMachine selectWashingNameAddressPhone(String wnumber, String type, BigInteger typeno) {
        try {
            return wmMapper.selectWashingNameAddressPhone(wnumber, type, typeno);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
