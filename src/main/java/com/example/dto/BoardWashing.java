package com.example.dto;

import java.util.Date;

import lombok.Data;

//BoardView + Washing 합친 뷰

@Data
public class BoardWashing {
    
    private long no;

    private String title;

    private String writer;

    private String content;

    private long hit;

    private Date regdate;

    private long code;

    private String codename;

    private String codedetail;

    private String name; //업체명

    private Date redate; // mapper에서 쓰여서
    

}
