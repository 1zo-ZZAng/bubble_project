package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.mybatis.CityMybatisService;
import com.example.service.mybatis.WashingMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/reserve")
@RequiredArgsConstructor
@Slf4j
public class RestReserveController {
    final CityMybatisService cityService;
    final WashingMybatisService wService;

    @GetMapping(value = "/selectcity.json")
    public Map<String, Object> selectcityGET(@RequestParam(name = "city") String city) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            retMap.put("status", 200);
            retMap.put("town", cityService.selectCityTown(city));

            // log.info(retMap.get("town").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    @GetMapping(value = "/washinglist.json")
    public Map<String, Object> washinglistGET(@RequestParam(name = "city") String city,
                                              @RequestParam(name = "town") String town) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            retMap.put("status", 200);
            retMap.put("washinglist", wService.selectWashingList(city, town));

            log.info(wService.selectWashingList(city, town).toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}
