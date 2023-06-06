package com.example.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Immutable  //뷰일 경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "RESERVE")
public class Reserve {
    @Id
    private String rvno;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "RDATE", insertable = true, updatable = false)
    private Date rdate;

    private String rvdate;

    private String rvtime;

    private String id;

    private String name;
    
    private String phone;
    
    private String address;
    
    private String detailaddress;
    
    private String extraaddress;

    private String wid;
    
    private String wnumber;
    
    private String wname;
    
    private String waddress;
    
    private String wphone;
    
    private BigInteger mno;

    private String mtype;

    private BigInteger mtypeno;

    private BigInteger mprice;

    private BigInteger mtime;

    private String state;
}
