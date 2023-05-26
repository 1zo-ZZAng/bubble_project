package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.dto.BoardType;

@Service
public interface BoardMybatisService {


    //게시글 분류
    public List<BoardType> selectlistBType();


    /* ===================================== */
    

    //글작성
    public int writeBoard(Board obj);

    //글 전체 조회
    public List<Board> selectlistBoard();

    //공지사항만 조회
    public List<Board> selectlistBoardTypeNotice();

    //분실물만 조회
    public List<Board> selectlistBoardTypeLost();

    //글 1개 조회
    public Board selectOneBoard(long no);

    //글 수정
    public int updateBoard(Board obj);

    //글 삭제
    public int deleteBoard(long no);

    //조회수 증가
    public int updateHit(long no);

    



    /* ===================================== */


    //게시글 전체 개수
    public int countBoard();

    //페이징징아 




    
}
