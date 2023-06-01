package com.example.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class Washing {

    private String id;  //아이디

    private String password; //암호

    private String wnumber; // 사업자 등록 번호

    private String email; // 이메일

    private String name; // 업체명

    private String address; // 업체 주소

    private String phone; // 업체 전화번호

    private String ceo; // 대표자명

    private String role; //권한 default 값 washing

    private BigInteger chkno; // 제휴 승인 여부 (0, 1) 체크 조건 있음
}
