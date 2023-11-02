package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.dto.BoardView;
import com.example.dto.BoardWashing;


//업체명 없는 뷰
//boardview와의 차이점은 업체명이 있고 없고 차이

@Mapper
public interface BoardWashingMapper {

        // //전체 조회
        // @Select({" SELECT * FROM boardwashing ORDER BY no DESC "})
        // public List<BoardWashing> selectBoardWashing();

        // /* 카테고리 별 조회 */

        // //공지사항
        // @Select({" SELECT * FROM boardwashing WHERE code=1 ORDER BY no DESC "})
        // public List<BoardWashing> selectBoardWashingNotice();

        // //분실물
        // @Select({" SELECT * FROM boardwashing WHERE code=2 ORDER BY no DESC "})
        // public List<BoardWashing> selectBoardWashingLost();

        // //습득물
        // @Select({" SELECT * FROM boardwashing WHERE code=3 ORDER BY no DESC "})
        // public List<BoardWashing> selectBoardWashingGet();

        // //자유게시판
        // @Select({" SELECT * FROM boardwashing WHERE code=4 ORDER BY no DESC "})
        // public List<BoardWashing> selectBoardWashingGeneral();
        

        // //최신 공지사항 글 5개 조회
        @Select({" SELECT no, title, writer, hit, MAX(regdate) AS redate, code, name FROM boardwashing WHERE code=1 GROUP BY regdate ORDER BY regdate DESC LIMIT 6 "})
        public List<BoardWashing> selectListLimitBoardWashing();



        // 공지사항 (관리자)
        @Select({" SELECT * FROM BOARDVIEW WHERE code IN (1, 2) ORDER BY code ASC, regdate DESC "})
        public List<BoardView> selectBoardAdminNotice();

        // 공지사항 (세탁업체)
        @Select({" SELECT * FROM BOARDVIEW WHERE code=3 ORDER BY regdate DESC "})
        public List<BoardView> selectBoardWashingNotice();

        // 분실물
        @Select({" SELECT * FROM BOARDVIEW WHERE code=4 ORDER BY regdate DESC "})
        public List<BoardView> selectBoardWashingLost();

        // 습득물
        @Select({" SELECT * FROM BOARDVIEW WHERE code=5 ORDER BY regdate DESC "})
        public List<BoardView> selectBoardWashingGet();
}
