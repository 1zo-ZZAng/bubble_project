package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
public interface WashingMachineMapper {
    // 예약 - 사업자번호, 기기명 => 보유 중인 기기별 번호 반환
    @Select({"SELECT typeno FROM washingmachine WHERE wnumber=#{wnumber} AND type=#{type} ORDER BY typeno ASC"})
    public List<Long> selectmachineno(@Param("wnumber") String wnumber, @Param("type") String type);

    // 예약 - 사업자번호, 기기명, 기기별 번호 => no 기기번호(시퀀스) 반환
    @Select({"SELECT no FROM washingmachine WHERE wnumber=#{wnumber} AND type=#{type} AND typeno=#{typeno}"})
    public Long selectWashingmachineNo(@Param("wnumber") String wnumber, @Param("type") String type, @Param("typeno") Long typeno);
}
