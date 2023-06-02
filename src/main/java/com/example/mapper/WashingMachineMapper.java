package com.example.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.dto.WashingMachine;


@Mapper
public interface WashingMachineMapper {
    // 예약 - 사업자번호, 기기명 => 보유 중인 기기별 번호 반환
    @Select({"SELECT typeno FROM washingmachine WHERE wnumber=#{wnumber} AND type=#{type} ORDER BY typeno ASC"})
    public List<Long> selectmachineno(@Param("wnumber") String wnumber, @Param("type") String type);

    // 예약 - 사업자번호, 기기명, 기기별 번호 => no 기기번호(시퀀스) 반환
    @Select({"SELECT no FROM washingmachine WHERE wnumber=#{wnumber} AND type=#{type} AND typeno=#{typeno}"})
    public Long selectWashingmachineNo(@Param("wnumber") String wnumber, @Param("type") String type, @Param("typeno") Long typeno);

    // 예약 - 사업자번호로 업체명, 업체주소, 업체전화번호, 가격, 이용시간 조회
	@Select({"SELECT name, address, phone, price, time FROM washingmachine WHERE wnumber=#{wnumber} AND type=#{type} AND typeno=#{typeno} "})
	public WashingMachine selectWashingNameAddressPhone(@Param("wnumber") String wnumber,
                                                        @Param("type") String type,
                                                        @Param("typeno") BigInteger typeno);
}
