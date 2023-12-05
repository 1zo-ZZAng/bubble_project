package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.dto.BoardAdmin;
import com.example.dto.BoardView;


@Mapper
public interface BoardViewMapper {


    //전체 조회
    @Select({" SELECT * FROM boardview WHERE code IN (1,2,3) BETWEEN #{start} AND #{end} ORDER BY code ASC, regdate desc "})
    public List<BoardView> selectBoardView(@Param("start") int start, @Param("end") int end);

    /* 카테고리 별 조회 */


    // 게시글 조회

    //공지사항(관리자)
    @Select({" SELECT * FROM boardview WHERE code IN (1,2) ORDER BY code ASC, regdate desc "})
    public List<BoardView> selectBoardViewNoticeAdmin();

    //공지사항(업체)
    @Select({" SELECT * FROM boardview WHERE code=3 ORDER BY regdate desc; "})
    public List<BoardView> selectBoardViewNoticeWashing();

    //분실물 습득물 
    @Select({" SELECT * FROM boardview WHERE code IN(4,5) ORDER BY no DESC "})
    public List<BoardView> selectBoardViewGetLost();

    //분실물
    @Select({" SELECT * FROM boardview WHERE code=4 ORDER BY no DESC "})
    public List<BoardView> selectBoardViewLost();

    //습득물
    @Select({" SELECT * FROM boardview WHERE code=5 ORDER BY no DESC "})
    public List<BoardView> selectBoardViewGet();

    //자유게시판
    @Select({" SELECT * FROM boardview WHERE code=6 ORDER BY no DESC "})
    public List<BoardView> selectBoardViewGeneral();
    

    
}
