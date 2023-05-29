package com.example.service.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;



@Service
public interface WashingSalesMybatisService {

    //해당업체의 일매출
    public List<Map<String, Object>> selectDaySales(String wname);

    //해당업체의 월매출
    public List<Map<String, Object>> selectMonthSales(String wname);

    //모든 업체의 월 매출
    public List<Map<String, Object>> selectAllMonthSales();
    
}
