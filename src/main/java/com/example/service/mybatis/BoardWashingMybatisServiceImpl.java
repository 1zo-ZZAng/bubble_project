package com.example.service.mybatis;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.AdminBoardView;
import com.example.dto.BoardView;
import com.example.dto.BoardWashing;
import com.example.mapper.BoardWashingMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardWashingMybatisServiceImpl implements BoardWashingMybatisService {

    final BoardWashingMapper bwMapper;

    @Override
    public List<AdminBoardView> selectBoardAdminNotice(int start, int end) {
        try {
            return bwMapper.selectBoardAdminNotice(start, end);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BoardWashing> selectBoardWashingNotice(int start, int end) {
        try {
            return bwMapper.selectBoardWashingNotice(start, end);
            
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

    


    

    
}
