package com.example.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;


@Data
@Entity
@Table(name = "WASHING")
public class Washing {
    

    @Id
    private String id;  //아이디

    private String password; //암호

    private String wnumber; // 사업자 등록 번호

    private String email; // 이메일

    private String name; // 업체명

    private String address; // 업체 주소

    private String phone; // 업체 전화번호

    private String ceo; // 대표자명

    private String role; //권한 default 값 washing

    private BigInteger chk; // 제휴 승인 여부 (0, 1) 체크 조건 있음


    //machine쪽 외래키 여기서 
    @ToString.Exclude
    @OneToMany(mappedBy = "washing", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Machine> machine = new ArrayList<>();





}
