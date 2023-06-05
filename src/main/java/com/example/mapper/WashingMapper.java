package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Washing;


@Mapper
public interface WashingMapper {

    // 세탁업체 등록
	@Insert({ 
		" INSERT INTO washing( id, password, wnumber, email, name, address, phone, ceo) ",
		" VALUES(#{obj.id}, #{obj.password}, #{obj.wnumber}, #{obj.email}, #{obj.name}, #{obj.address}, #{obj.phone}, #{obj.ceo}) " 
		})
	public int joinWashing(@Param("obj") Washing obj);
	
	// 아이디 중복 확인 
	@Select({
		" SELECT COUNT(*) cnt FROM washing WHERE id = #{id} "
		})
	public int washingIDCheck(@Param("id") String id);

	// 업체 로그인
	@Select({
		" SELECT id, email, name, address, phone, ceo FROM washing ",
		" WHERE id = #{id} AND password = #{password} "
	})
	public Washing loginWashing(@Param("id") String id, @Param("password") String password);
	
	// 1명 조회 (아이디)
	@Select({ 
		"SELECT * FROM washing WHERE id = #{id} "  
	})
	public Washing selectWashingOne(@Param("id") String id);

	//1명 조회 (업체명)
	@Select({" SELECT * FROM washing WHERE name=#{name} "})
	public Washing selectWashingnameOne(@Param("name") String name);

	// 업체 정보 수정
	@Update({
        " UPDATE washing SET address = #{obj.address}, name=#{obj.name}, phone = #{obj.phone}, ceo = #{obj.ceo}, email=#{obj.email} ",
        " WHERE id = #{obj.id} "
	})
	public int updateWashingOne(@Param("obj") Washing obj);
	
	// 업체 아이디 찾기
	@Select({
		" SELECT id FROM washing ",
		" WHERE name=#{obj.name} AND phone=#{obj.phone} OR email=#{obj.email} "
	})
	public String findWashingId(@Param("obj") Washing obj);
	
	// 업체 비번 찾기
	@Select({
		" SELECT password FROM washing ",
		" WHERE id=#{obj.id} AND phone=#{obj.phone} OR email=#{obj.email} "
	})
	public String findWashingPw(@Param("obj") Washing obj);
	
	// 비번 변경
	@Update({" UPDATE washing SET password=#{obj.newpassword} WHERE id = #{obj.id} AND password=#{obj.password} "})
	public int updateWashingPw (@Param("obj") Washing obj);
	
	// 업체 탈퇴
	@Update({ " UPDATE washing SET password=null, wnumber=null, email=null, name='탈퇴', address=null, phone=null, ceo=null, role=null, chk=0 WHERE id=#{obj.id} AND password=#{obj.password} "})
	public int deleteWashingOne (@Param("obj") Washing obj);


	/* === 예약 === */

    // 예약 페이지에서 지역에 맞는 세탁소 리스트 조회
	@Select({"SELECT wnumber, name, address, phone FROM WASHING WHERE address LIKE #{cityname} || '%' || #{townname} || '%' AND chkno=1 ORDER BY name ASC"})
	public List<Washing> selectWashingList(@Param("cityname") String cityname, @Param("townname") String townname);

	//예약 내역 조회 (업체별)
	// @Select({" SELECT * FROM RESERVE WHERE wname=#{wname} ORDER BY rvno DESC"})
	// public List<Reserve> selectReserveList(@Param("wname") String wname);



	/* === 매출 === */

	//일매출 //오라클은 sysdate mysql은 curdate()로 오늘날짜를 조회할 수 있다
	@Select({" SELECT SUBSTRING(rvdate, 0,10) daysales, sum(mprice) total FROM RESERVE WHERE wid=#{wid} AND CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now() AND rvdate IS NOT NULL AND STATE = '이용 완료'  GROUP BY SUBSTRING(rvdate, 0,10) "})
	public List<Map<String, Object>> selectDaySales(@Param("wid") String wid);

	//월매출 
	@Select({" SELECT SUBSTRING(rvdate, 0,7) monthsales, sum(mprice) total  FROM RESERVE WHERE wid=#{wid} AND CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now() AND rvdate IS NOT NULL AND STATE = '이용 완료' GROUP BY SUBSTRING(rvdate, 0,7) "})
	public List<Map<String, Object>> selectMonthSales(@Param("wid") String wid);

	//연매출
	@Select({" SELECT SUBSTRING(rvdate, 0,4) yearsales, sum(mprice) total FROM RESERVE WHERE wid=#{wid} AND CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now() AND rvdate IS NOT NULL AND STATE = '이용 완료'  GROUP BY SUBSTRING(rvdate, 0,4) "})
	public List<Map<String, Object>> selectYearSales(@Param("wid") String wid);

	//모든 업체의 연 매출 
	@Select({" SELECT wname, SUBSTRING(rvdate, 0,4) yearsales, sum(mprice) total FROM RESERVE WHERE CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now AND rvdate IS NOT NULL AND STATE = '이용 완료' GROUP BY SUBSTRING(rvdate, 0,4), wname"})
	public List<Map<String, Object>> selectAllMonthSales();

	//월별사용자수
	@Select({" SELECT SUBSTRING(rvdate, 0,7) monthly, count(*) usercnt FROM RESERVE WHERE wid=#{wid} AND CONCAT(SUBSTRING(RVDATE, 0, 10), ' ', SUBSTRING(rvtime, 0, 5)) <= now() AND rvdate IS NOT NULL AND STATE = '이용 완료'  GROUP BY SUBSTRING(rvdate, 0,7) "})
	public List<Map<String, Object>> selectUserCnt(@Param("wid") String wid);

	//최근 일주일 사용자 수 (메인페이지에)
	@Select({" SELECT rvdate as weekly ,COUNT(*) usercnt FROM RESERVE WHERE  wid=#{wid} AND rvdate >= now()-7  AND CONCAT(rvdate, ' ', SUBSTRING(rvtime, 0, 5)) <= TO_CHAR(now(), 'YYYY-MM-DD HH:MI') AND rvdate IS NOT NULL AND STATE = '이용 완료'  GROUP BY rvdate "})
	public List<Map<String, Object>> selectWeekUserCnt(@Param("wid") String wid);

	

}
