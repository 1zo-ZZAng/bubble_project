package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.WashingMachine;

@Service
public interface WashingMachineMybatisService {
    // 예약 - 사업자번호, 기기명 => 보유 중인 기기번호 반환
    public List<Long> selectmachineno(String wnumber, String type);

    // 예약 - 사업자번호, 기기명, 기기별 번호 => no 기기번호(시퀀스) 반환
    public Long selectWashingmachineNo(String wnumber,  String type,  Long typeno);

    // 예약 - 사업자번호로 업체명, 업체주소, 업체전화번호, 가격, 이용시간 조회
	public WashingMachine selectWashingNameAddressPhone(String wnumber, String type, BigInteger typeno);
}
