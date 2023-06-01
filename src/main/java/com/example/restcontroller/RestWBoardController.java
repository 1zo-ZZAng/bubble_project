package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Board;
import com.example.service.mybatis.BoardMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/api/wboard")
@RequiredArgsConstructor
@Slf4j
public class RestWBoardController {

    final BoardMybatisService bService;

    //조회수 증가
    //127.0.0.1:8282/bubble_bumul/api/wboard/updatehit.bubble?no=
    @PutMapping(value="/updatehit.bubble")
    public Map<String, Integer> updatehitPUT( @RequestParam(name = "no") long no) {

        Map<String, Integer> retMap = new HashMap<>();

        try {

            int ret = bService.updateHit(no);

            retMap.put("status", 200);
            retMap.put("result", ret);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    
}
