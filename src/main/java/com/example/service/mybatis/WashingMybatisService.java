package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;
import com.example.dto.Washing;

@Service
public interface WashingMybatisService {

    //업체탈퇴
    public int deleteWashingOne(Washing obj);

    // 예약 페이지에서 지역에 맞는 세탁소 리스트 조회
    public List<Washing> selectWashingList(String cityname, String townname);

    //1명 조회 (업체명으로)
    public Washing selectWashingnameOne(String name);

    //예약조회(업체별)
    public List<Reserve> selectReserveList(String wname);

    
    
}
