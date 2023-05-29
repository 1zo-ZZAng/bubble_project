package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.mybatis.WashingMybatisService;
import com.example.service.mybatis.WashingSalesMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/api/washingsales")
@RequiredArgsConstructor
@Slf4j

//업체 매출
public class RestWashingSalesController {

    final WashingSalesMybatisService wSalesMybatisService;
    final WashingMybatisService wMybatisService;

    //해당 업체의 일 매출
    //주소 127.0.0.1:8282/bubble_bumul/api/washingsales/daysales.bubble?wname='업체명'
    @GetMapping(value="/daysales.bubble")
    public Map<String,Object> daysalesGET(@RequestParam(name = "wname") String wname) {

        Map<String, Object> retMap = new HashMap<>();

        try {

            log.info("업체명  출력 => {}",wname); //그치.. 내가 쓴거 나오겠지...

            List<Map<String, Object>> list = wSalesMybatisService.selectDaySales(wname);

            log.info("매출조회 => {}", list.toString()); //여기가 나와야 하는 상황
            
            retMap.put("status", 200);
            retMap.put("list", list);

            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 
        }

        return retMap;
        
    }

    // 해당 업체의 월 매출
    //주소 127.0.0.1:8282/bubble_bumul/api/washingsales/monthsales.bubble?wname='업체명'
    @GetMapping(value="/monthsales.bubble")
    public Map<String,Object> monthsalesGET(@RequestParam(name = "wname") String wname) {

        Map<String, Object> retMap = new HashMap<>();

        try {

            log.info("업체명 내놔 => {}",wname); //그치.. 내가 쓴거 나오겠지...

            List<Map<String, Object>> list = wSalesMybatisService.selectMonthSales(wname);

            log.info("매출조회 => {}", list.toString()); //여기가 나와야 하는 상황
            
            retMap.put("status", 200);
            retMap.put("list", list);

            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 
        }

        return retMap;
        
    }


    //모든 업체의 월 매출
    //주소 127.0.0.1:8282/bubble_bumul/api/washingsales/allmonthsales.bubble
    @GetMapping(value="/allmonthsales.bubble")
    public Map<String,Object> selectMonthSalesGET() {

        Map<String, Object> retMap = new HashMap<>();

        try {

            List<Map<String, Object>> list = wSalesMybatisService.selectAllMonthSales();

            log.info("내놔 => {}", list.toString()); //여기가 나와야 하는 상황
            
            retMap.put("status", 200);
            retMap.put("list", list);

            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 
        }

        return retMap;
        
    }

    
    

    
}
