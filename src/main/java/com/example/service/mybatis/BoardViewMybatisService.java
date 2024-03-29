package com.example.service.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.example.dto.BoardView;

@Service
public interface BoardViewMybatisService {


    //전체 조회
    public List<BoardView> selectBoardView(@Param("start") int start, @Param("end") int end);


    /* 카테고리 별 조회 */

    //공지사항(관리자)
    public List<BoardView> selectBoardViewNoticeAdmin();

    //공지사항(업체)
    public List<BoardView> selectBoardViewNoticeWashing();

    //분실물 습득물 
    public List<BoardView> selectBoardViewGetLost();

    //분실물
    public List<BoardView> selectBoardViewLost();

    //습득물
    public List<BoardView> selectBoardViewGet();

    //자유게시판
    public List<BoardView> selectBoardViewGeneral();
    
}
