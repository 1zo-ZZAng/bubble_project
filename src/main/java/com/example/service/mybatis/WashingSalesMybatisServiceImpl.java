package com.example.service.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.mapper.WashingMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WashingSalesMybatisServiceImpl implements WashingSalesMybatisService {


    final WashingMapper wMapper;


    //일매출
    @Override
    public List<Map<String, Object>> selectDaySales(String wname) {
        try {

            return wMapper.selectDaySales(wname);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //해당업체의 월매출
    @Override
    public List<Map<String, Object>> selectMonthSales(String wname) {
        try {

            return wMapper.selectMonthSales(wname);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //해당업체의 연매출
    @Override
    public List<Map<String, Object>> selectYearSales(String wname) {
        try {
            return wMapper.selectYearSales(wname);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //모든업체의 월 매출
    @Override
    public List<Map<String, Object>> selectAllMonthSales() {
        try {
            return wMapper.selectAllMonthSales();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    

    
    
}
