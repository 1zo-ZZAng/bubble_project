package com.example.service.jpa;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.entity.Machine;

@Service
public interface MachineService {

    //기기 삭제
    public List<Machine> deleteAllByNo(BigInteger[] chk);

    //기기삭제 2번ㅉ ㅐ버전
    public int deleteMachine(Map<String, Object> map);
    
}
