package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BoardWashing;

@Service
public interface BoardWashingMybatisService {


    //전체 조회
    public List<BoardWashing> selectBoardWashing();


    /* 카테고리 별 조회 */

    //공지사항
    public List<BoardWashing> selectBoardWashingNotice();

    //분실물
    public List<BoardWashing> selectBoardWashingLost();

    //습득물
    public List<BoardWashing> selectBoardWashingGet();

    //자유게시판
    public List<BoardWashing> selectBoardWashingGeneral();

    //최신 공지사항글 6개 조회
    public List<BoardWashing> selectListLimitBoardWashing();
    
}
