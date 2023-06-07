package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reply;
import com.example.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyMybatisServiceImpl implements ReplyMybatisService {


    final ReplyMapper replyMapper;


/* ========================================= */


    //댓글 작성
    @Override
    public int writeReply(Reply obj) {
        try {

            return replyMapper.writeReply(obj);
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //수정
    @Override
    public int updateReply(Reply obj) {
        try {

            return replyMapper.updateReply(obj);

            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //삭제
    @Override
    public int deleteReply(long bno) {
        try {

            return replyMapper.deleteReply(bno);

            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //전체 조회
    @Override
    public List<Reply> selectlistReply(long bno) {
        try {

            return replyMapper.selectlistReply(bno);

            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //댓글 개수
    @Override
    public int countReply(long bno) {
        try {
            
            return replyMapper.countReply(bno);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
