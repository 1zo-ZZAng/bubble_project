package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.config.web.servlet.SecurityMarker;

import com.example.dto.BoardAdmin;
import com.example.dto.BoardGetLost;
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
        @Select({" SELECT * FROM (SELECT ba.*, ROW_NUMBER() OVER (ORDER BY code ASC, regdate DESC) rown FROM boardadmin ba) "
                + " WHERE rown BETWEEN #{start} AND #{end} ORDER BY rown ASC "})
        public List<BoardAdmin> selectBoardAdminNotice(@Param("start") int start, @Param("end") int end);

        // (2) 공지사항 (세탁업체)
        @Select({" SELECT * FROM (SELECT bw.*, ROW_NUMBER() OVER (ORDER BY bw.regdate DESC) rown FROM BOARDWASHING bw) "
                + " WHERE rown BETWEEN #{start} AND #{end} ORDER BY rown ASC "})
        public List<BoardWashing> selectBoardWashingNotice(@Param("start") int start, @Param("end") int end);

        // (3) 분실물/습득물
        @Select({" SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY regdate DESC) rown FROM BOARDGETLOST WHERE code=#{code}) " 
                + " WHERE rown BETWEEN #{start} AND #{end} ORDER BY rown ASC "})
        public List<BoardGetLost> selectBoardGetLost(@Param("code") int code, @Param("start") int start, @Param("end") int end);

        // ------------------------------------------------------------------------------------------
        // 페이지네이션
        // 전체 글 개수
        //공지사항( 전체 )
        @Select({" SELECT COUNT(*) FROM BoardView "})
        public int selectBoardAllNoticeCount();

        // (1) 공지사항 (관리자)
        @Select({" SELECT COUNT(*) FROM BoardAdmin "})
        public int selectBoardAdminNoticeCount();

        // (2) 공지사항 (세탁업체)
        @Select({" SELECT COUNT(*) FROM BOARDWASHING "})
        public int selectBoardWashingNoticeCount();

        // (3) 분실물/습득물
        @Select({" SELECT COUNT(*) FROM BOARDGETLOST WHERE code=#{code} "})
        public int selectBoardGetLostCount(@Param("code") int code);
}
