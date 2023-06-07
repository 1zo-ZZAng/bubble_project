package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.dto.BoardView;

@Mapper
public interface BoardViewMapper {


    //전체 조회
    @Select({" SELECT * FROM boardview"})
    public List<BoardView> selectBoardView();

    /* 카테고리 별 조회 */

    //공지사항
    @Select({" SELECT * FROM boardview WHERE code=1"})
    public List<BoardView> selectBoardViewNotice();

    //분실물
    @Select({" SELECT * FROM boardview WHERE code=2"})
    public List<BoardView> selectBoardViewLost();

    //습득물
    @Select({" SELECT * FROM boardview WHERE code=3"})
    public List<BoardView> selectBoardViewGet();

    //자유게시판
    @Select({" SELECT * FROM boardview WHERE code=4"})
    public List<BoardView> selectBoardViewGeneral();
    
}
