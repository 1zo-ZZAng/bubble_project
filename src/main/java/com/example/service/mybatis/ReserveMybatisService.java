package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;

@Service
public interface ReserveMybatisService {
    // 기기별 시간
    public List<String> selectMachineTime(String machine);

    // 업체별, 기기별, 기기번호별, 날짜별 사용 가능한 시간
    public List<String> selectUseableTime(String wnumber, String machine, BigInteger machineno, String rvdate);

    // 업체별, 기기별, 기기번호별, 날짜별 사용 불가능한 시간
    // public List<String> selectUnuseableTime(String wnumber, String machine, BigInteger machineno, String rvdate);

    // 예약 내역 조회 - 목록 (예약번호, 세탁소명, 세탁소 주소, 세탁소 연락처, 예약일, 예약시간)
    public List<Reserve> selectReserveList(String id); 

    // 예약 내역 상세
    public Reserve selectReserveDetail(String rvno);

    // 예약 취소 - reservation 테이블의 rvdate, rvtime 컬럼 null로 update
    public int deleteReserveOne(String rvno);

    // 예약하기
    public int insertReserve(String no, String cid, Long mno, String rvdate, String rvtime);
}
