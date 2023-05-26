package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Board {

    private long no;    // 게시글 번호

    private String content; // 내용

    private String writer;  // 작성자

    private long hit; // 조회수

    private Date regdate; // 작성일

    private long code; // 분류코드 - 외래키 BoardType
    
}
