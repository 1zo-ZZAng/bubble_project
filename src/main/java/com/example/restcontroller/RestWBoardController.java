package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.mybatis.BoardMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/wboard")
@RequiredArgsConstructor
@Slf4j
public class RestWBoardController {

    final BoardMybatisService bService;

    // 조회수 증가
    // 127.0.0.1:8282/bubble_bumul/api/wboard/updatehit.bubble?no=
    @GetMapping(value = "/updatehit.bubble")
    public Map<String, Integer> updatehitGET(@RequestParam(name = "no") long no) {

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

    // 글쓰기 -> codename 변경시 codedetail 목록 변경&조회
    @GetMapping(value = "/writecodechange.bubble")
    public Map<String, Object> followerlistGET(@RequestParam(name = "type") String type) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            List<String> codeDetailList = new ArrayList<>();
            if (type.equals("notice")) {
                codeDetailList = bService.selectCodeDetail("공지사항");    
            } 
            else if (type.equals("getlost")) {
                codeDetailList = bService.selectCodeDetail("분실물/습득물");    
            }
            else if (type.equals("community")) {
                codeDetailList = bService.selectCodeDetail("자유게시판");
            }

            retMap.put("status", 200);
            retMap.put("codeDetailList", codeDetailList);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }

        return retMap;
    }

}
