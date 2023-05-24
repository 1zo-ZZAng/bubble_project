package com.example.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, BigInteger> {


    //해당아이디의 정보
    Machine findByWashing_id(String wid);

    //기기 목록하려고 있음
    List<Machine> findByWashing_idOrderByNoDesc(String id);


    //기기 선택 삭제
    List<Machine> deleteByNo(BigInteger[] no);

    //기기 선택 삭제
    // List<Machine> deleteByNo(List<BigInteger> no);






    
    
}
