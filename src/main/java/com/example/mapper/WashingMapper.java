package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Reserve;
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
	@Update({
		" UPDATE washing SET password=#{obj.newpassword} WHERE id = #{obj.id} AND password=#{obj.password} "
	})
	public int updateWashingPw (@Param("obj") Washing obj);
	
	// 업체 탈퇴
	@Update({
		" UPDATE washing SET password=null, wnumber=null, email=null, name='탈퇴', address=null, phone=null, ceo=null, role=null, chk=0 ",
		" WHERE id=#{obj.id} AND password=#{obj.password} "
	})
	public int deleteWashingOne (@Param("obj") Washing obj);

    // 예약 페이지에서 지역에 맞는 세탁소 리스트 조회
	@Select({"SELECT name, address, phone FROM WASHING WHERE address LIKE #{cityname} || '%' || #{townname} || '%'"})
	public List<Washing> selectWashingList(@Param("cityname") String cityname, @Param("townname") String townname);


	
	/* === 매출 부분 === */

	//일매출
	@Select({" SELECT TO_CHAR(RDATE, 'YYYY-MM-DD') date, SUM(mprice) DAYSALES FROM RESERVE WHERE wname=#{wname} GROUP BY TO_CHAR(RDATE, 'YYYY-MM-DD') ORDER BY TO_CHAR(RDATE, 'YYYY-MM-DD') DESC "})
	public List<Map<String, Object>> selectDaySales(@Param("wname") String wname);

	//월매출
	@Select({" SELECT TO_CHAR(RDATE, 'YYYY-MM') date, SUM(mprice) DAYSALES FROM RESERVE WHERE wname=#{wname} GROUP BY TO_CHAR(RDATE, 'YYYY-MM') ORDER BY TO_CHAR(RDATE, 'YYYY-MM') DESC "})
	public List<Map<String, Object>> selectMonthSales(@Param("wname") String wname);

	//연매출
	@Select({" SELECT TO_CHAR(RDATE, 'YYYY') date, SUM(mprice) DAYSALES FROM RESERVE WHERE wname=#{wname} GROUP BY TO_CHAR(RDATE, 'YYYY') ORDER BY TO_CHAR(RDATE, 'YYYY') DESC "})
	public List<Map<String, Object>> selectYearSales(@Param("wname") String wname);

	//모든 업체의 월 매출
	@Select({" SELECT WNAME, TO_CHAR(RDATE, 'YYYY-MM') monthdate, SUM(mprice) mprice FROM reserve GROUP BY TO_CHAR(RDATE, 'YYYY-MM'), wname ORDER BY TO_CHAR(RDATE, 'YYYY-MM') DESC "})
	public List<Map<String, Object>> selectAllMonthSales();

}
