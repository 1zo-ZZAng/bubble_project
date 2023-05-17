package com.example.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "MACHINE")
@SequenceGenerator(name = "SEQ_MACHINE_NO", sequenceName = "SEQ_MACHINE_NO", initialValue = 1, allocationSize = 1) 
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MACHINE_NO") //시퀀스
    private BigInteger no; // 보유기기번호

    private String type; // 기기 종류

    private BigInteger typeno; // 기기종류별 번호

    private BigInteger time; // 작동 시간

    private BigInteger price; // 가격

    //외래키 생성 - 업체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wid", referencedColumnName = "id")  //소문자로 해뒀는데 문제있을경우 대문자로 바꿔야함 기억하자고
    private Washing washing;

}
