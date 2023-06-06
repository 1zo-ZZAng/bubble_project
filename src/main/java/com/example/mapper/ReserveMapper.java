package com.example.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Reserve;

@Mapper
public interface ReserveMapper {
    // 기기별 시간
    @Select({"SELECT starttime FROM runtime WHERE type=#{type}"})
    public List<String> selectMachineTime(@Param("type") String type);

    // 업체별, 기기별, 기기번호별, 날짜별 사용 가능한 시간
    @Select({" SELECT starttime FROM runtime "
           + " WHERE type=#{type} "
           + " AND starttime NOT IN (SELECT rvtime FROM reserve WHERE wnumber=#{wnumber} AND mtype=#{type} AND mtypeno=#{mtypeno} AND rvdate=#{rvdate} AND state != '예약 취소') "})
    public List<String> selectUseableTime(@Param("wnumber") String wnumber,
                                          @Param("type") String machine,
                                          @Param("mtypeno") BigInteger machineno,
                                          @Param("rvdate") String rvdate);

    // 업체별, 기기별, 기기번호별, 날짜별 사용 불가능한 시간
//     @Select({" SELECT starttime FROM runtime "
//            + " WHERE type=#{type} "
//            + " AND starttime IN (SELECT rvtime FROM reserve WHERE wnumber=#{wnumber} AND mtype=#{mtype} AND mtypeno=#{mtypeno} AND rvdate=#{rvdate}) "})
//     public List<String> selectUnuseableTime(@Param("wnumber") String wnumber,
//                                             @Param("type") String machine,
//                                             @Param("mtypeno") BigInteger machineno,
//                                             @Param("rvdate") String rvdate);

    // 예약 내역 조회 - 목록 (예약번호, 세탁소명, 세탁소 주소, 예약일, 예약시간, 현황)
    @Select({"SELECT rvno, wname, waddress, rvdate, rvtime, state FROM reserve WHERE id=#{id} AND rvdate IS NOT NULL AND rvtime IS NOT NULL ORDER BY rdate DESC"})
    public List<Reserve> selectReserveList(@Param("id") String id); 

    // 예약 내역 상세
    @Select({"SELECT rvno, wname, waddress, wphone, rvdate, rvtime, mtype, mtypeno, mprice, mtime, state FROM reserve WHERE rvno=#{rvno}"})
    public Reserve selectReserveDetail(@Param("rvno") String rvno);

    // 예약 취소 - reservation 테이블의 rvdate, rvtime 컬럼 null로 update
    @Update({"UPDATE RESERVATION SET state='예약 취소' WHERE no=#{rvno}"})
    public int deleteReserveOne(@Param("rvno") String rvno);

    // 예약하기
    @Insert({"INSERT INTO reservation(no, cid, mno, rvdate, rvtime) VALUES(#{no}, #{cid}, #{mno}, #{rvdate}, #{rvtime})"})
    public int insertReserve(@Param("no") String no,
                                @Param("cid") String cid,
                                @Param("mno") Long mno,
                                @Param("rvdate") String rvdate,
                                @Param("rvtime") String rvtime);


    /* == 예약 내역 전체 조회업체 == */

    //전체조회
    @Select({" SELECT * FROM RESERVE WHERE wid=#{wid} ORDER BY rdate desc "})
    public List<Reserve> selectReserveAllList(@Param("wid") String wid);

    //이용완료
    @Select({" SELECT * FROM RESERVE WHERE wid=#{wid} and state = '이용 완료' ORDER BY rdate desc "})
    public List<Reserve> selectReserveStateUseComplete(@Param("wid") String wid);

    //예약 완료
    @Select({" SELECT * FROM RESERVE WHERE wid=#{wid} and state = '예약 완료' ORDER BY rdate desc "})
    public List<Reserve> selectReserveStateRevComplete(@Param("wid") String wid);

    //예약 취소
    @Select({" SELECT * FROM RESERVE WHERE wid=#{wid} and state = '예약 취소' ORDER BY rdate desc "})
    public List<Reserve> selectReserveStateRevCancle(@Param("wid") String wid);

    /* 스케쥴러 사용 */

    @Select({ " SELECT rvdate, rvtime FROM RESERVE " })
    public List<Reserve> selectReserveListSch();

    


    
    
}
