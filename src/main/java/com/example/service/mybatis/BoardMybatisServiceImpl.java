package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.dto.BoardType;
import com.example.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardMybatisServiceImpl implements BoardMybatisService {

    final BoardMapper bMapper;


    //분류코드 전체조회
    @Override
    public List<BoardType> selectlistBType() {
        try {

            return bMapper.selectlistBType();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //게시판 분류 - 중복 제거
    @Override
    public List<BoardType> selectlistBTypeCodeName() {
        try {
            return bMapper.selectlistBTypeCodeName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //말머리 분류 - 중복 제거 null값 제외
    @Override
    public List<BoardType> selectlistBTypeCodeDetail() {
        try {
            return bMapper.selectlistBTypeCodeDetail();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //말머리 분류 - 중복 제거 null값 제외
    @Override
    public List<BoardType> selectlistBTypeCodeDetailTest(String codename) {
        try {
            return bMapper.selectlistBTypeCodeDetailTest(codename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* =============================메인=============================== */


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

    //게시판 공지사항만 조회
    @Override
    public List<Board> selectlistBoardTypeNotice() {
        try {
            
            return bMapper.selectlistBoardTypeNotice();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //게시판 분실물만 조회
    @Override
    public List<Board> selectlistBoardTypeLost() {
        try {
            
            return bMapper.selectlistBoardTypeLost();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
            
    }



    //게시판 유실물만 조회
    @Override
    public List<Board> selectlistBoardTypeGet() {
        try {
            
            return bMapper.selectlistBoardTypeGet();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //게시판 자유게시글만 조회
    @Override
    public List<Board> selectlistBoardTypeGeneral() {
        try {
            
            return bMapper.selectlistBoardTypeGeneral();

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



    /* ============================페이지네이션================================ */


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

    //다음글
    @Override
    public int nextBoardOne(long no) {
        try {

            return bMapper.nextBoardOne(no);

            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //이전글
    @Override
    public int preBoardOne(long no) {
        try {

            return bMapper.preBoardOne(no);

            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //전체 페이지 123456 
    @Override
    public List<Board> selectBoardListPage(int start, int end) {
        try {

            return bMapper.selectBoardListPage(start, end);

            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //최신글 5개만 조회
    @Override
    public List<Board> selectListLimitBoard() {
        try {
            
            return bMapper.selectListLimitBoard();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    




    
    
}
