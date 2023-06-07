package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Washing;



@Repository
public interface WashingRepository extends JpaRepository<Washing, String> {

    //아이디 중복 확인
    public int countById(String id);

    //아이디 찾기
    public Washing findByCeoAndEmail(String ceo, String email);

    //비밀번호 찾기
    public Washing findByIdAndEmail(String id, String email);

}
