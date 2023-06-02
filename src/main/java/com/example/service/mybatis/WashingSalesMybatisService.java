package com.example.service.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;



@Service
public interface WashingSalesMybatisService {

    //해당업체의 일매출
    public List<Map<String, Object>> selectDaySales(String wid);

    //해당업체의 월매출
    public List<Map<String, Object>> selectMonthSales(String wid);

    //해당업체의 연매출
    public List<Map<String, Object>> selectYearSales(String wid);

    //모든 업체의 월 매출
    public List<Map<String, Object>> selectAllMonthSales();


    //해당 업체의 월매출 - controller
    public List<Reserve> selectMonthControllerSales(String wid);
    
}
