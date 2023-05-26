package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Board;
import com.example.dto.BoardType;

@Mapper
public interface BoardMapper {

    //글 분류 조회 //따로 만들기엔 복잡해서 여기에 뒀음
    @Select({" SELECT * FROM BOARDTYPE "})
    public List<BoardType> selectlistBType();

    /* =====================메인======================== */

    //글 작성
    @Insert({" INSERT INTO BOARD(title, content, writer, hit, code) VALUES(#{obj.title}, #{obj.content}, #{obj.writer}, #{obj.hit}, #{obj.code}) "})
    public int writeBoard(@Param("obj") Board obj);

    //글 수정
    @Update({" UPDATE BOARD SET title=#{obj.title}, content=#{obj.content}, code=#{obj.code} WHERE no=#{obj.no}"})
    public int updateBoard(@Param("obj") Board obj);

    //글 삭제
    @Delete({" DELETE FROM BOARD WHERE no=#{no} "})
    public int deleteBoard(@Param("no") long no);

    //글 전체 조회 //내림차순
    @Select({" SELECT * FROM BOARD ORDER BY NO DESC "})
    public List<Board> selectlistBoard();

    //글 1개 조회
    @Select({" SELECT * FROM BOARD WHERE no=#{no} "})
    public Board selectOneBoard(@Param("no") long no);

    //조회수 증가
    @Update({"UPDATE BOARD SET hit = hit + 1 WHERE no=#{no}"})
    public int updateHit(@Param("no") long no);


    /* ====================페이지 네이션======================= */

    //게시글 전체 수
    @Select({" SELECT COUNT(*) cnt FROM BOARD "})
    public int countBoard();
    
    //페이징? => 아직 게시글 없어서 안했음



    
}
