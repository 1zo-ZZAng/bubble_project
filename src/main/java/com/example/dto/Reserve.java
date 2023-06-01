package com.example.dto;

import java.util.Date;

import org.springframework.data.annotation.Immutable;

import lombok.Data;


@Data
public class Reserve {

    private long rvno;

    private Date rdate;

    private String id;

    private String name;
    
    private String phone;
    
    private String address;
    
    private String detailaddress;
    
    private String extraaddress;
    
    private String wname;
    
    private String waddress;
    
    private String wphone;
    
    private long mno;

    private String mtype;

    private long mtypeno;

    private long mprice;

    private long mtime;
    
}
