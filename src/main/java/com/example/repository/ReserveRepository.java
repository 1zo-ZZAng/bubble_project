package com.example.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, BigInteger> {




    /* === 고객용 ==== */

    


    /* === 업체용 ==== */


    //예약내역조회 (업체별)
    List<Reserve> findByWidOrderByRvnoDesc(String wid);

    List<Reserve> findByWidAndState(String wid, String state);



    /* === 관리자용 ==== */
    
}
