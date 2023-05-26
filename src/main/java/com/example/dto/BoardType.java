package com.example.dto;

import lombok.Data;

@Data
public class BoardType {

    private long code; //  분류코드 (시퀀스) 

    private String codename; // 분류코드명 (공지사항, 분실물, 커뮤니티, 제휴등록)
    
    private String codedetail; // 말머리 (분실물 - 찾아주세요, 찾아가세요) //혹시 오류날까봐 말머리 공지사항 - 공지사항 , 제휴등록 - 제휴등록 해놨음
    
}
