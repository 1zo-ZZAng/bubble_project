package com.example.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.dto.AdminBoardView;
import com.example.dto.BoardView;
import com.example.dto.BoardWashing;


//업체명 없는 뷰
//boardview와의 차이점은 업체명이 있고 없고 차이

@Mapper
public interface BoardWashingMapper {
        // //최신 공지사항 글 5개 조회
        @Select({" SELECT no, title, writer, hit, MAX(regdate) AS redate, code, name FROM boardwashing WHERE code=1 GROUP BY regdate ORDER BY regdate DESC LIMIT 6 "})
        public List<BoardWashing> selectListLimitBoardWashing();
                
        // ------------------------------------------------------------------------------------------
        // 게시글 조회
        // (1) 공지사항 (관리자)
        @Select({" SELECT * FROM (SELECT adb.*, ROW_NUMBER() OVER (ORDER BY code ASC, regdate DESC) rown FROM adminboard adb) " 
                + " WHERE rown BETWEEN #{start} AND #{end} ORDER BY rown ASC "})
        public List<AdminBoardView> selectBoardAdminNotice(@Param("start") int start, @Param("end") int end);

        // (2) 공지사항 (세탁업체)
        @Select({" SELECT * FROM (SELECT bw.*, ROW_NUMBER() OVER (ORDER BY bw.regdate DESC) rown FROM BOARDWASHING bw WHERE code=3) " 
                + " WHERE rown BETWEEN #{start} AND #{end} ORDER BY rown ASC "})
        public List<BoardWashing> selectBoardWashingNotice(@Param("start") int start, @Param("end") int end);

        // (3) 분실물
        @Select({" SELECT * FROM BOARDVIEW WHERE code=4 ORDER BY regdate DESC "})
        public List<BoardView> selectBoardWashingLost();

        // (4) 습득물
        @Select({" SELECT * FROM BOARDVIEW WHERE code=5 ORDER BY regdate DESC "})
        public List<BoardView> selectBoardWashingGet();

        // ------------------------------------------------------------------------------------------
        // 페이지네이션
        // 전체 글 개수
        // (1) 공지사항 (관리자)
        @Select({" SELECT COUNT(*) FROM adminboard "})
        public int selectBoardAdminNoticeCount();

        // (2) 공지사항 (세탁업체)
        @Select({" SELECT COUNT(*) FROM BOARDWASHING WHERE code=3 "})
        public int selectBoardWashingNoticeCount();

        // (3) 분실물
        // (4) 습득물
}
