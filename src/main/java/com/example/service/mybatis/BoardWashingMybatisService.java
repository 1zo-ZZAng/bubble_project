package com.example.service.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.BoardAdmin;
import com.example.dto.BoardGetLost;
import com.example.dto.BoardView;
import com.example.dto.BoardWashing;

@Service
public interface BoardWashingMybatisService {
    // //최신 공지사항글 6개 조회
    public List<BoardWashing> selectListLimitBoardWashing();
    
    // ------------------------------------------------------------------------------------------
    // 게시글 조회
    // (1) 공지사항 (관리자)
    public List<BoardAdmin> selectBoardAdminNotice(@Param("start") int start, @Param("end") int end);

    // (2) 공지사항 (세탁업체)
    // public List<BoardWashing> selectBoardWashingNotice();
    public List<BoardWashing> selectBoardWashingNotice(@Param("start") int start, @Param("end") int end);

    // (3) 분실물/습득물
    public List<BoardGetLost> selectBoardGetLost(@Param("code") int code, @Param("start") int start, @Param("end") int end);

    // ------------------------------------------------------------------------------------------
    // 페이지네이션
    // 전체 글 개수
    public int selectBoardAllNoticeCount();

    // (1) 공지사항 (관리자)
    public int selectBoardAdminNoticeCount();

    // (2) 공지사항 (세탁업체)
    public int selectBoardWashingNoticeCount();

    // (3) 분실물/습득물
    public int selectBoardGetLostCount(@Param("code") int code);
}
