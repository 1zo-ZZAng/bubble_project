package com.example.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.example.dto.AdminChkList;
import com.example.dto.MachineCount;
import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.WashingCheck;




@Mapper
public interface AdminMapper {
    

    //업체리스트 전체 조회 (업체명, 대표자명, 주소, 사업자등록번호, 전화번호)
    @Select({ " SELECT * FROM washing WHERE address IS NOT NULL GROUP BY name " })
    public List<Washing> selectWList();

    //업체승인 유무 select박스
    @Select({" SELECT no,chk FROM washingcheck "})
    public List<WashingCheck> selctChkList();

    //업체승인 유무 select박스 - chk 값
    @Select({" SELECT distinct(chk) FROM ADMINCHKLIST "})
    public List<String> selectBoxList();

    //업체별 보유기기 목록 조회
    @Select({" SELECT NAME, WNUMBER, TYPE, count(type) count FROM WASHINGMACHINE WHERE WNUMBER=#{WNUMBER} GROUP BY type "})
    public List<WashingMachine> selectWmList(@Param("wnumber") String wnumber);

    //보유기기 목록
    @Select({ " SELECT * FROM MACHINECOUNT WHERE WNUMBER=#{WNUMBER}  " })
    public List<MachineCount> selectMCount(@Param("wnumber") String wnumber);

    @Select({"SELECT DISTINCT(name) FROM MACHINECOUNT WHERE WNUMBER = #{wnumber}"})
    public String selectWashingNameOne(@Param("wnumber") String wnumber);

    //제휴 승인 update

    @Update({" UPDATE washing SET chkno=1 WHERE wnumber=#{wnumber} "})
    public int updateChk(@Param("wnumber") String wnumber );

    //승인 대기/완료 업체 목록
    @Select({" SELECT * FROM ADMINCHKLIST WHERE wnumber IS NOT NULL AND chk=#{chk} GROUP BY address  "})
    public List<AdminChkList> selectWlistUnchecked(@Param("chk") String chk);

    




}
