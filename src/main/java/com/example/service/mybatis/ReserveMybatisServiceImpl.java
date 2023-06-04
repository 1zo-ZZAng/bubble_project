package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;
import com.example.mapper.ReserveMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveMybatisServiceImpl implements ReserveMybatisService {
    final ReserveMapper rMapper;

    // 기기별 시간
    @Override
    public List<String> selectMachineTime(String machine) {
        try {
            return rMapper.selectMachineTime(machine);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 업체별, 기기별, 기기번호별, 날짜별 사용 가능한 시간
    @Override
    public List<String> selectUseableTime(String wnumber, String machine, BigInteger machineno, String rvdate) {
        try {
            return rMapper.selectUseableTime(wnumber, machine, machineno, rvdate);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // // 업체별, 기기별, 기기번호별, 날짜별 사용 불가능한 시간
    // @Override
    // public List<String> selectUnuseableTime(String wnumber, String machine, BigInteger machineno, String rvdate) {
    //     try {
    //         return rMapper.selectUnuseableTime(wnumber, machine, machineno, rvdate);
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // 예약하기
    @Override
    public int insertReserve(String cid, Long mno, String rvdate, String rvtime) {
        try {
            return rMapper.insertReserve(cid, mno, rvdate, rvtime);
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 예약 내역 조회 - 목록 (예약번호, 세탁소명, 세탁소 주소, 세탁소 연락처, 예약일, 예약시간)
    @Override
    public List<Reserve> selectReserveList(String id) {
        try {
            return rMapper.selectReserveList(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 예약 내역 상세
    @Override
    public Reserve selectReserveOne(BigInteger rvno) {
        try {
            return rMapper.selectReserveOne(rvno);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 예약 취소 - 예약번호로 해당 예약 찾기
    @Override
    public Reserve selectReserveRvno(BigInteger rvno) {
        try {
            return rMapper.selectReserveRvno(rvno);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
