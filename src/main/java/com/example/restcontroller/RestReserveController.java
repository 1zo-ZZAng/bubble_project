package com.example.restcontroller;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.jpa.CustomerService;
import com.example.service.mybatis.CityMybatisService;
import com.example.service.mybatis.ReserveMybatisService;
import com.example.service.mybatis.WashingMachineMybatisService;
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
    final CustomerService cService;
    final WashingMachineMybatisService wmService;
    final ReserveMybatisService rService;

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

            // log.info(wService.selectWashingList(city, town).toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    @GetMapping(value = "/myaddress.json")
    public Map<String, Object> myaddressGET(@AuthenticationPrincipal User user){
        Map<String, Object> retMap = new HashMap<>();

        String str = cService.selectCustomerOne(user.getUsername()).getAddress();
        List<String> addressList = Arrays.asList(str.split(" "));
        List<String> townList = cityService.selectCityTown(addressList.get(0));

        try {
            if (str != null & townList != null) {
                // log.info(addressList.get(0));
                // log.info(addressList.get(1));

                retMap.put("status", 200);
                retMap.put("cityname", addressList.get(0));
                retMap.put("townlist", townList);
                retMap.put("townname", addressList.get(1));
            }
            else {
                retMap.put("cityname", null);
                retMap.put("townlist", null);
                retMap.put("townname", null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    @GetMapping(value = "/selectmachine.json")
    public Map<String, Object> selectmachineGET(@RequestParam(name = "wnumber") String wnumber,
                                                @RequestParam(name = "machine") String machine,
                                                @AuthenticationPrincipal User user) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            retMap.put("status", 200);
            retMap.put("typeno",  wmService.selectmachineno(wnumber, machine));
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    @GetMapping(value = "/selecteddate.json")
    public Map<String, Object> selecteddateGET(@AuthenticationPrincipal User user,
                                               @RequestParam(name = "wnumber") String wnumber,
                                               @RequestParam(name = "machine") String machine,
                                               @RequestParam(name = "machineno") BigInteger machineno,
                                               @RequestParam(name = "rvdate") String rvdate) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            retMap.put("status", 200);
            retMap.put("useable", rService.selectUseableTime(wnumber, machine, machineno, rvdate));
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}
