package com.example.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
