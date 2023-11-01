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

    //게시판 분류 조회
    @Select({" SELECT code, codename FROM BOARDTYPE "})
    public List<BoardType> selectlistBTypeCodeName();

    //말머리 분류 - null값인거 제외
    @Select({" SELECT * FROM BOARDTYPE WHERE codedetail IS NOT null "})
    public List<BoardType> selectlistBTypeCodeDetail();

    //말머리 분류 - 관리자 작성용(공지사항만 가능)
    @Select({" SELECT * FROM BOARDTYPE WHERE code=1 "})
    public BoardType selectlistBTypeAdmin();

    //공지사항 날짜기준 최신 3개 조회
    @Select({" SELECT * FROM (SELECT * FROM board WHERE code=1 ORDER BY regdate DESC) WHERE ROWNUM <= 3 "})
    public List<Board> selectlistBoardTopNotice();


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

    //모든 게시글 전체 조회
    @Select({" SELECT * FROM BOARD ORDER BY NO DESC "})
    public List<Board> selectlistBoard();

    //공지사항만 조회 
    @Select({" SELECT * FROM BOARD WHERE code=1 ORDER BY NO DESC "})
    public List<Board> selectlistBoardTypeNotice();

    //분실물만 조회
    @Select({" SELECT * FROM BOARD WHERE code=2 ORDER BY NO DESC "})
    public List<Board> selectlistBoardTypeLost();

    //유실물만 조회
    @Select({" SELECT * FROM BOARD WHERE code=3 ORDER BY NO DESC "})
    public List<Board> selectlistBoardTypeGet();

    //자유게시판만 조회
    @Select({" SELECT * FROM BOARD WHERE code=4 ORDER BY NO DESC "})
    public List<Board> selectlistBoardTypeGeneral();

    //최신글 5개 조회 (일단은 공지사항)
    @Select({" SELECT no, title, writer, hit, MAX(regdate) AS redate, code FROM board WHERE code=1 GROUP BY regdate ORDER BY regdate DESC LIMIT 6 "})
    public List<Board> selectListLimitBoard();

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

    //다음글로 넘기기
    @Select({" SELECT NVL(MIN(no),0) FROM BOARD WHERE no > #{no} "})
    public int nextBoardOne(@Param("no") long no);

    //이전글로 넘기기
    @Select({" SELECT NVL(MAX(no),0) FROM BOARD WHERE no < #{no} "})
    public int preBoardOne(@Param("no") long no);
    
    //페이징
    @Select({" SELECT b.* FROM( SELECT b.*, ROW_NUMBER() OVER (ORDER BY no DESC) rown FROM BOARD b )b WHERE rown >= #{start} AND rown <= #{end} ORDER BY no DESC "})
	public List<Board> selectBoardListPage(@Param("start") int start, @Param("end") int end);

    
}
