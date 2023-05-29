package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.example.dto.MachineCount;
import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Customer;



@Mapper
public interface AdminMapper {
    

    //업체리스트 전체 조회 (업체명, 대표자명, 주소, 사업자등록번호, 전화번호)
    @Select({ " SELECT * FROM washing WHERE address IS NOT NULL GROUP BY name " })
    public List<Washing> selectWList();

    //업체승인 유무 select박스
    @Select({" SELECT DISTINCT(chk) FROM washing "})
    public List<String> selctChkList();

    //업체별 보유기기 목록 조회
    @Select({" SELECT NAME, WNUMBER, TYPE, count(type) count FROM WASHINGMACHINE WHERE WNUMBER=#{WNUMBER} GROUP BY type "})
    public List<WashingMachine> selectWmList(@Param("wnumber") String wnumber);

    //보유기기 목록
    @Select({ " SELECT * FROM MACHINECOUNT WHERE WNUMBER=#{WNUMBER}  " })
    public List<MachineCount> selectMCount(@Param("wnumber") String wnumber);

    @Select({"SELECT DISTINCT(name) FROM MACHINECOUNT WHERE WNUMBER = #{wnumber}"})
    public String selectWashingNameOne(@Param("wnumber") String wnumber);



    //승인 대기/완료 업체 목록
    @Select({" SELECT name,ceo,wnumber,phone,address FROM washing WHERE address IS NOT NULL and CHK=#{CHK} GROUP BY NAME  "})
    public List<Washing> selectWlistUnchecked(@Param("chk") String chk);

    




}
