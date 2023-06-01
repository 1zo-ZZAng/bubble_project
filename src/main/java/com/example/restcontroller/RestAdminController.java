package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.entity.Washing;
import com.example.service.jpa.AdminService;
import com.example.service.mybatis.AdminListMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
@Slf4j
public class RestAdminController {
    final AdminListMybatisService alService;
    final AdminService aService;

    @GetMapping(value = "/confirm.json")
    public Map<String, Object> confirmGET(@RequestParam(name = "type") String type) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            retMap.put("status", 200);
            retMap.put("washinglist", alService.selectWlistUnchecked(type));

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());

        }
        return retMap;
    }


    @PostMapping(value = "/updatechk.json")
    public Map<String, Object> updatechkPOST(@RequestBody List<Map<String,String>> bubble){
        Map<String, Object> retMap = new HashMap<>();
        try {
            for(Map<String,String> map: bubble ){
                retMap.put("status",200);
                retMap.put("bubble",alService.updateChk(map.get("bubble")));
            }   
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }



}
