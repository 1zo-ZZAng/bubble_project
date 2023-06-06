package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Reply {
    
    //댓글 번호
    private long no;

    //댓글 내용
    private String content;

    //댓글 작성자
    private String writer;

    //등록일
    private Date regdate;

    //게시글 번호
    private long bno;


    /* 댓글 이미지 */

    private String filename;

	private long filesize;

	private byte[] filedata; // blob

	private String filetype;
    

}
