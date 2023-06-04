package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Washingmachine;

@Repository
public interface WashingMachineRepository extends JpaRepository<Washingmachine, String> {
    
    
}
