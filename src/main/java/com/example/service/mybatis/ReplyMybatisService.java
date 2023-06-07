package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reply;

@Service
public interface ReplyMybatisService {


    //댓글 작성
    public int writeReply(Reply obj);

    //댓글 수정 (이미지 빼고)
    public int updateReply(Reply obj);

    //댓글 삭제
    public int deleteReply(long bno);

    //댓글 전체 조회 (해당 게시글의)
    public List<Reply> selectlistReply(long bno);

    //댓글 개수 (해당 게시글)
    public int countReply(long bno);

}
