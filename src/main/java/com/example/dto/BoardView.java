package com.example.dto;

import java.util.Date;

import lombok.Data;


@Data
public class BoardView {

    private long no;

    private String title;

    private String writer;

    private String content;

    private long hit;

    private Date regdate;

    private long code;

    private String codename;

    private String codedetail;

}
