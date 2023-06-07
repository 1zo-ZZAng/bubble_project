package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Reply;

@Mapper
public interface ReplyMapper {

    //댓글 작성
    @Insert({" INSERT INTO REPLY(content, writer, bno) VALUES(#{obj.content}, #{obj.writer}, #{obj.bno}) "}) // #{obj.filename}, #{obj.filedata}, #{obj.filetype}, #{obj.filesize}
    public int writeReply(@Param("obj") Reply obj);

    //댓글 수정
    @Update({" UPDATE REPLY SET content=#{obj.content} WHERE no=#{obj.no} "})
    public int updateReply(@Param("obj") Reply obj);

    //댓글 삭제 (해당 게시글의 모든 댓글)
    @Delete({" DELETE FROM REPLY WHERE bno=#{bno} "})
    public int deleteReply(@Param("bno") long bno);

    //해당 댓글만 삭제
    @Delete({" DELETE FROM REPLY WHERE no=#{no} "})
    public int deleteOneReply(@Param("no") long no);

    //댓글 전체 조회 (해당 게시글만)
    @Select({" SELECT * FROM REPLY WHERE bno=#{bno} ORDER BY no DESC "})
    public List<Reply> selectlistReply(@Param("bno") long bno);

    //댓글 개수(해당 게시글)
    @Select({" SELECT count(*) cnt FROM reply WHERE bno=#{bno} "})
    public int countReply(@Param("bno") long bno);

    
}
