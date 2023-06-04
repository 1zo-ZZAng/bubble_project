package com.example.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.dto.Reserve;

@Mapper
public interface ReserveMapper {
    // 기기별 시간
    @Select({"SELECT starttime FROM runtime WHERE type=#{type}"})
    public List<String> selectMachineTime(@Param("type") String type);

    // 업체별, 기기별, 기기번호별, 날짜별 사용 가능한 시간
    @Select({" SELECT starttime FROM runtime "
           + " WHERE type=#{type} "
           + " AND starttime NOT IN (SELECT rvtime FROM reserve WHERE wnumber=#{wnumber} AND mtype=#{type} AND mtypeno=#{mtypeno} AND rvdate=#{rvdate}) "})
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

    // 예약 내역 조회 - 목록 (예약번호, 세탁소명, 세탁소 주소, 예약일, 예약시간)
    @Select({"SELECT rvno, wname, waddress, rvdate, rvtime FROM reserve WHERE id=#{id} ORDER BY rvno DESC"})
    public List<Reserve> selectReserveList(@Param("id") String id); 

    // 예약 내역 상세
    @Select({"SELECT rvno, wname, waddress, wphone, rvdate, rvtime, mtype, mtypeno, mprice, mtime FROM reserve WHERE rvno=#{rvno}"})
    public Reserve selectReserveOne(@Param("rvno") BigInteger rvno);

    // 예약 취소 - 예약번호로 해당 예약 찾기
    @Select({"SELECT * FROM reserve WHERE rvno=#{rvno}"})
    public Reserve selectReserveRvno(@Param("rvno") BigInteger rvno);

    // 예약하기
    @Insert({"INSERT INTO reservation(cid, mno, rvdate, rvtime) VALUES(#{cid}, #{mno}, #{rvdate}, #{rvtime})"})
    public int insertReserve(@Param("cid") String cid,
                                 @Param("mno") Long mno,
                                 @Param("rvdate") String rvdate,
                                 @Param("rvtime") String rvtime);
}
