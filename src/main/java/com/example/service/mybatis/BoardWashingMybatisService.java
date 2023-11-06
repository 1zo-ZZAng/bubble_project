package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.example.dto.AdminBoardView;
import com.example.dto.BoardView;
import com.example.dto.BoardWashing;

@Service
public interface BoardWashingMybatisService {
    // //최신 공지사항글 6개 조회
    public List<BoardWashing> selectListLimitBoardWashing();
    
    // ------------------------------------------------------------------------------------------
    // 게시글 조회
    // (1) 공지사항 (관리자)
    public List<AdminBoardView> selectBoardAdminNotice(@Param("start") int start, @Param("end") int end);

    // (2) 공지사항 (세탁업체)
    // public List<BoardWashing> selectBoardWashingNotice();
    public List<BoardWashing> selectBoardWashingNotice(@Param("start") int start, @Param("end") int end);

    // (3) 분실물
    public List<BoardView> selectBoardWashingLost();

    // (4) 습득물
    public List<BoardView> selectBoardWashingGet();

    // ------------------------------------------------------------------------------------------
    // 페이지네이션
    // 전체 글 개수
    // (1) 공지사항 (관리자)
    public int selectBoardAdminNoticeCount();

    // (2) 공지사항 (세탁업체)
    public int selectBoardWashingNoticeCount();

    // (3) 분실물
    // (4) 습득물
}
