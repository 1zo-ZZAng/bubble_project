package com.example.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Immutable  //뷰일 경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "WASHINGMACHINE")
public class Washingmachine {
    @Id
    private String id;

    private String wnumber;

    private String email;

    private String name;

    private String address;

    private String phone;

    private String ceo;

    private String role;

    private BigInteger chkno;

    private BigInteger no;

    private String type;

    private BigInteger typeno;
    
    private BigInteger price;

    private BigInteger time;
}
