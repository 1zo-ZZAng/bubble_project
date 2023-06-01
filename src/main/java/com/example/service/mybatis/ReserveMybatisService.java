package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ReserveMybatisService {
    // 기기별 시간
    public List<String> selectMachineTime(String machine);

    // 업체별, 기기별, 기기번호별, 날짜별 사용 가능한 시간
    public List<String> selectUseableTime(String wnumber, String machine, BigInteger machineno, String rvdate);

    // 업체별, 기기별, 기기번호별, 날짜별 사용 불가능한 시간
    // public List<String> selectUnuseableTime(String wnumber, String machine, BigInteger machineno, String rvdate);

    // 예약하기
    public int insertReserve(String cid, Long mno, String rvdate, String rvtime);
}
