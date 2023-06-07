package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BoardView;

@Service
public interface BoardViewMybatisService {


    //전체 조회
    public List<BoardView> selectBoardView();


    /* 카테고리 별 조회 */

    //공지사항
    public List<BoardView> selectBoardViewNotice();

    //분실물
    public List<BoardView> selectBoardViewLost();

    //습득물
    public List<BoardView> selectBoardViewGet();

    //자유게시판
    public List<BoardView> selectBoardViewGeneral();
    
}
