package com.example.entity;

import java.util.Date;

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

@Entity
@Data
@Table(name = "RESERVATION")
@SequenceGenerator(name = "SEQ_RESERVATION_NO", sequenceName = "SEQ_RESERVATION_NO", initialValue = 1, allocationSize = 1) 
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESERVATION_NO") //시퀀스
    private String no; //예약 번호(시퀀스)

    private Date rdate; //사용날짜

    private String rvdate; //예약날짜

    private String rvtime; //예약시간

    //외래키 - 기기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", referencedColumnName = "no")  //소문자로 해뒀는데 문제있을경우 대문자로 바꿔야함 기억하자고
    private Machine machine;

    //외래키 - 고객
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid", referencedColumnName = "id")  //소문자로 해뒀는데 문제있을경우 대문자로 바꿔야함 기억하자고
    private Customer customer;


    
}
