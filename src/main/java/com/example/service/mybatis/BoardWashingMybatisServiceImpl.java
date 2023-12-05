package com.example.service.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.example.dto.BoardAdmin;
import com.example.dto.BoardGetLost;
import com.example.dto.BoardView;
import com.example.dto.BoardWashing;
import com.example.mapper.BoardWashingMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardWashingMybatisServiceImpl implements BoardWashingMybatisService {

    final BoardWashingMapper bwMapper;

    // 최신 공지사항글 
    @Override
    public List<BoardWashing> selectListLimitBoardWashing() {
        try {
            
            return bwMapper.selectListLimitBoardWashing();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ------------------------------------------------------------------------------------------
    // 게시글 조회
    // (1) 공지사항 (관리자)
    @Override
    public List<BoardAdmin> selectBoardAdminNotice(int start, int end) {
        try {
            return bwMapper.selectBoardAdminNotice(start, end);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // (2) 공지사항 (세탁업체)
    @Override
    public List<BoardWashing> selectBoardWashingNotice(int start, int end) {
        try {
            return bwMapper.selectBoardWashingNotice(start, end);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // (3) 분실물/습득물
    @Override
    public List<BoardGetLost> selectBoardGetLost(int code, int start, int end) {
        try {
            return bwMapper.selectBoardGetLost(code, start, end);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ------------------------------------------------------------------------------------------
    // 페이지네이션
    // 전체 글 개수

    //공지사항( 전체 )
    @Override
    public int selectBoardAllNoticeCount() {
         try {
            return bwMapper.selectBoardAllNoticeCount();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // (1) 공지사항 (관리자)
    @Override
    public int selectBoardAdminNoticeCount() {
        try {
            return bwMapper.selectBoardAdminNoticeCount();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // (2) 공지사항 (세탁업체)
    @Override
    public int selectBoardWashingNoticeCount() {
        try {
            return bwMapper.selectBoardWashingNoticeCount();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // (3) 분실물/습득물
    @Override
    public int selectBoardGetLostCount(int code) {
        try {
            return bwMapper.selectBoardGetLostCount(code);
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    }
