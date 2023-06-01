package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

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
}
