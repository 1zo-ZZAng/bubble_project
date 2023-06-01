package com.example.repository;

import java.math.BigInteger;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.WashingCheck;


@Repository
public interface WashingCheckRepository extends JpaRepository<WashingCheck, BigInteger> {
    
    
}
