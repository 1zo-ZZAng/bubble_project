package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Washingmachine;

@Repository
public interface WashingMachineRepository extends JpaRepository<Washingmachine, String> {
    
    //세탁기수
    @Query("SELECT name,ceo,address,count(*),phone FROM WASHINGMACHINE w WHERE w.id=#{obj.id} AND w.TYPE=#{obj.type}")
        public List<Washingmachine> selectwmlist();


    
}
