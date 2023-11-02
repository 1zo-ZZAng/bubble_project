package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BoardView;
import com.example.dto.BoardWashing;
import com.example.mapper.BoardWashingMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardWashingMybatisServiceImpl implements BoardWashingMybatisService {

    final BoardWashingMapper bwMapper;

    @Override
    public List<BoardView> selectBoardAdminNotice() {
        try {
            return bwMapper.selectBoardAdminNotice();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BoardView> selectBoardWashingNotice() {
        try {
            return bwMapper.selectBoardWashingNotice();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BoardView> selectBoardWashingLost() {
        try {
            return bwMapper.selectBoardWashingLost();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BoardView> selectBoardWashingGet() {
        try {
            return bwMapper.selectBoardWashingGet();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // //전체 조회
    // @Override
    // public List<BoardWashing> selectBoardWashing() {
    //     try {

    //         return bwMapper.selectBoardWashing();
            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }


    // /* 카테고리 별 조회 */

    // //공지사항
    // @Override
    // public List<BoardWashing> selectBoardWashingNotice() {
    //     try {
            
    //         return bwMapper.selectBoardWashingNotice();

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // //분실물
    // @Override
    // public List<BoardWashing> selectBoardWashingLost() {
    //     try {
            
    //         return bwMapper.selectBoardWashingLost();

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // //습득물
    // @Override
    // public List<BoardWashing> selectBoardWashingGet() {
    //     try {
            
    //         return bwMapper.selectBoardWashingGet();

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // //자유게시판
    // @Override
    // public List<BoardWashing> selectBoardWashingGeneral() {
    //     try {
            
    //         return bwMapper.selectBoardWashingGeneral();

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }


    // //최신 공지사항글 
    @Override
    public List<BoardWashing> selectListLimitBoardWashing() {
        try {
            
            return bwMapper.selectListLimitBoardWashing();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    

    
}
