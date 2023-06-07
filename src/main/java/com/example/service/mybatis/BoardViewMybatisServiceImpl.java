package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BoardView;
import com.example.mapper.BoardViewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardViewMybatisServiceImpl implements BoardViewMybatisService{

    final BoardViewMapper bvMapper;


    //전체조회
    @Override
    public List<BoardView> selectBoardView() {
        try {

            return bvMapper.selectBoardView();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* 카테고리 조회 */

    //공지사항
    @Override
    public List<BoardView> selectBoardViewNotice() {
        try {

            return bvMapper.selectBoardViewNotice();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //분실물
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
