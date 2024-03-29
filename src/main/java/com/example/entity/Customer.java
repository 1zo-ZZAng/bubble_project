package com.example.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    private String id; // 고객 아이디

    private String password; // 고객 비밀번호

    private String name; // 고객 이름

    private String phone;// 고객 전화번호

    private String email; // 고객 이메일

    private String address; // 고객 주소(시~구~도로명)
    private String detailaddress; // 고객 상세 주소 (고객이 입력하는 주소)
    private String extraaddress; // 고객 참고 주소 (건물이름)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth", columnDefinition = "DATE")
    private Date birth; // 고객 생년월일

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", insertable = true, updatable = true)
    private Date regdate; 
    
    // 고객 등급 (0 : 탈퇴 / 1 : 세탁 초보 / 2 : 세탁 프로 / 3 : 세탁 요정)
    private BigInteger grade = BigInteger.valueOf(1); 

    private String role = "CUSTOMER"; // 권한 (CUSTOMER)

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Reservation> reservation = new ArrayList<>();
}
