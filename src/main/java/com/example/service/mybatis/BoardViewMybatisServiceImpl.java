package com.example.service.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.BoardView;
import com.example.mapper.BoardViewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardViewMybatisServiceImpl implements BoardViewMybatisService{

    final BoardViewMapper bvMapper;


    //공지사항(전체)
    @Override
    public List<BoardView> selectBoardView(int start, int end) {
       
    try {

            return bvMapper.selectBoardView(start,end);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }    
    }


    //공지사항(관리자)
    @Override
    public List<BoardView> selectBoardViewNoticeAdmin() {
        try {

            return bvMapper.selectBoardViewNoticeAdmin();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //공지사항(업체)
    @Override
    public List<BoardView> selectBoardViewNoticeWashing() {
        try {

            return bvMapper.selectBoardViewNoticeWashing();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //분실물 습득물
    @Override
    public List<BoardView> selectBoardViewGetLost() {
        try {

            return bvMapper.selectBoardViewGetLost();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //분실뮬
    @Override
    public List<BoardView> selectBoardViewLost() {
       try {

            return bvMapper.selectBoardViewLost();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //습득물
    @Override
    public List<BoardView> selectBoardViewGet() {
       try {

            return bvMapper.selectBoardViewGet();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    //자유게시판
    @Override
    public List<BoardView> selectBoardViewGeneral() {
        try {

            return bvMapper.selectBoardViewGeneral();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    
}
