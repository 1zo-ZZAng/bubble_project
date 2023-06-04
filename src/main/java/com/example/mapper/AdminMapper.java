package com.example.mapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.AdminChkList;
import com.example.dto.MachineCount;
import com.example.dto.Reserve;
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
    @Select({ " SELECT * FROM MACHINECOUNT WHERE WNUMBER=#{WNUMBER} " })
    public List<MachineCount> selectMCount(@Param("wnumber") String wnumber);

    @Select({"SELECT DISTINCT(name) FROM MACHINECOUNT WHERE WNUMBER = #{wnumber}"})
    public String selectWashingNameOne(@Param("wnumber") String wnumber);

    @Select({" SELECT * FROM Washing WHERE WNUMBER = #{wnumber} "})
    public Washing selectWashingOne(@Param("wnumber") String wnumber);

    //제휴 승인 update
    @Update({" UPDATE washing SET chkno=1 WHERE wnumber=#{wnumber} "})
    public int updateChk(@Param("wnumber") String wnumber );

    //승인 대기/완료 업체 목록
    @Select({" SELECT * FROM ADMINCHKLIST WHERE wnumber IS NOT NULL AND chk=#{chk} GROUP BY address  "})
    public List<AdminChkList> selectWlistUnchecked(@Param("chk") String chk);


    //제휴 업체 수
    @Select({" SELECT count(*) FROM washing WHERE address IS NOT NULL AND chkno=1 "})
    public int washingCount();

    //업체 별 오늘의 예약 건 
    @Select({"   SELECT count(*) FROM reserve WHERE CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) = now() AND wnumber = #{wnumber} "})
    public int todayRVWashingCount(@Param("wnumber") String wnumber);


    //---------------------------차트------------------------------
    
    //차트의 월별 선택
    @Select({" SELECT DISTINCT (SUBSTRING(rvdate,0,7) ) FROM reserve "})
    public List<Reserve> selectMonthBox();


    ///월 총 매출 조회(오늘의 날짜 포함) 
    @Select({" SELECT SUBSTRING(rvdate, 0,7) mdate, sum(mprice) mtotal FROM RESERVE WHERE CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now()  GROUP BY SUBSTRING(rvdate, 0,7) "})
    public List<Map<String,Object>> selectMonthAllSales();

    //월- 일별 총 매출 조회(오늘의 날짜 포함) ex)2023-04 (1~30)
    @Select({" SELECT SUBSTRING(rvdate,0,10) day, sum(mprice) dtotal FROM reserve WHERE SUBSTRING(RVDATE,0,7)='#{selectdate}'  AND CONCAT(SUBSTRING(rvdate,0,10),' ',SUBSTRING(RVTIME ,0,5)) <= now()  GROUP BY SUBSTRING(rvdate,0,10) "})
    public List<Map<String,Object>> selectMonthChart(@Param("selectdate") String selectdate);

    //오늘 총 예약 건 
    @Select({" SELECT count(*) TODAY FROM reserve WHERE CONCAT(SUBSTRING(rvdate,0,10),' ',SUBSTRING(RVTIME ,0,5)) = now() "})
    public int todayRVCount();

    //예약 목록 최신순
    @Select({" SELECT * FROM reserve WHERE CONCAT(SUBSTRING(rvdate,0,10),' ',SUBSTRING(RVTIME ,0,5)) <= now()  GROUP BY rvno ORDER by rvdate desc "})
    public Reserve selectRvList();
    

    // -------------------업체별 페이지 차트--------------------------

    //업체별 모든 매출 조회
    @Select({" SELECT SUBSTRING(rvdate, 0,10) rvday, wname, sum(mprice) mtotal FROM RESERVE WHERE CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now() AND wnumber=#{wnumber} GROUP BY SUBSTRING(rvdate, 0,10) "})
    public List<Map<String,Object>> selectMonthDateWashingChart(@Param("wnumber") String wnumber);

    //월별 매출 조회 box
    @Select({" SELECT SUBSTRING(rvdate, 0,7) rvday FROM RESERVE WHERE CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now() AND Wnumber=#wnumber GROUP BY SUBSTRING(rvdate, 0,7) "})
    public List<String> selectMonthBoxList(@Param("wnumber") String wnumber);

    //월별 매출 조회
    @Select({"  SELECT SUBSTRING(rvdate, 0,7) rvday, sum(mprice) mtotal FROM RESERVE WHERE AND CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now() AND WNUMBER=#{WNUMBER} GROUP BY SUBSTRING(rvdate, 0,7)"})
    public List<Map<String,Object>> selectMonthWashingChart(@Param("wnumber") String wnumber);






    




}
