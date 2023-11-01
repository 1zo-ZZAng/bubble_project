package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.mybatis.BoardMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/api/aboard")
@RequiredArgsConstructor
@Slf4j
public class RestABoardController {

    final BoardMybatisService bService;

    //조회수 증가
    //127.0.0.1:8282/bubble_bumul/api/aboard/updatehit.bubble?no=
    @GetMapping(value="/updatehit.bubble")
    public Map<String, Integer> updatehitGET( @RequestParam(name = "no") long no) {

        Map<String, Integer> retMap = new HashMap<>();

        try {

            int ret = bService.updateHit(no);

            retMap.put("status", 200);
            retMap.put("result", ret);

            log.info("값은? => {}", ret);

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retMap;

    }

    
}
