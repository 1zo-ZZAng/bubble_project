package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.dto.BoardType;

@Service
public interface BoardMybatisService {


    //게시글 분류
    public List<BoardType> selectlistBType();

    //게시판 분류 -  중복 제거
    public List<BoardType> selectlistBTypeCodeName();

    //말머리 분류 - 중복 제거
    public List<BoardType> selectlistBTypeCodeDetail();


    /* ===================================== */
    

    //글작성
    public int writeBoard(Board obj);

    //글 전체 조회
    public List<Board> selectlistBoard();

    //공지사항만 조회
    public List<Board> selectlistBoardTypeNotice();

    //분실물만 조회
    public List<Board> selectlistBoardTypeLost();

    //유실물만 조회
    public List<Board> selectlistBoardTypeGet();

    //자유게시판만 조회
    public List<Board> selectlistBoardTypeGeneral();

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

    //다음글로 넘기기
    public int nextBoardOne(long no);

    //이전글로 넘기기
    public int preBoardOne(long no);

    //페이징
    public List<Board> selectBoardListPage(int start, int end);



    
}
