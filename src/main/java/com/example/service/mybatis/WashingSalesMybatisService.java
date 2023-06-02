package com.example.service.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;



@Service
public interface WashingSalesMybatisService {

    //해당업체의 일매출
    public List<Map<String, Object>> selectDaySales(String wid);

    //해당업체의 월매출
    public List<Map<String, Object>> selectMonthSales(String wid);

    //해당업체의 연매출
    public List<Map<String, Object>> selectYearSales(String wid);

    //월별 사용자 수
    public List<Map<String, Object>> selectUserCnt(String wid);

    //주별 사용자 수

}
