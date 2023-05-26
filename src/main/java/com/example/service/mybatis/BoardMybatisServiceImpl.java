package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardMybatisServiceImpl implements BoardMybatisService {

    final BoardMapper bMapper;

    //글 작성
    @Override
    public int writeBoard(Board obj) {
        try {

            return bMapper.writeBoard(obj);
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //글 전체 조회
    @Override
    public List<Board> selectlistBoard() {
        try {
            
            return bMapper.selectlistBoard();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //글 1개 조회
    @Override
    public Board selectOneBoard(long no) {
        try {

            return bMapper.selectOneBoard(no);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    //글 수정
    @Override
    public int updateBoard(Board obj) {
        try {

            return bMapper.updateBoard(obj);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //글 삭제
    @Override
    public int deleteBoard(long no) {
        try {

            return bMapper.deleteBoard(no);
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //조회수 증가
    @Override
    public int updateHit(long no) {
        try {
            
            return bMapper.updateHit(no);
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //게시글 전체 개수
    @Override
    public int countBoard() {
        try {

            return bMapper.countBoard();

            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
