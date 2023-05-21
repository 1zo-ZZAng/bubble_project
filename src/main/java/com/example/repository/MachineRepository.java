package com.example.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, BigInteger> {

    
    
}
