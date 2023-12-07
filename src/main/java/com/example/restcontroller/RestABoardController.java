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

    @GetMapping(value = "/selectoption.bubble")
    public Map<String, Object> selectoptionGET(@RequestParam(name = "code") int code) {
        Map<String, Object> retMap = new HashMap<>();

        if(code == 1){
            try {
            retMap.put("status", 200);
            retMap.put("codedetail", bService.selectlistBTypeCodeDetailTest("공지사항"));

            log.info(retMap.get("codedetail").toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                retMap.put("status", -1);
                retMap.put("error", e.getMessage());
            }
            return retMap;
        }
        if(code == 4){
            try {
                retMap.put("status", 200);
                retMap.put("codedetail", bService.selectlistBTypeCodeDetailTest("분실물/습득물"));

                log.info(retMap.get("codedetail").toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                retMap.put("status", -1);
                retMap.put("error", e.getMessage());
            }
            return retMap;
        }
        else{
            try {
                retMap.put("status", 200);
                retMap.put("codedetail", bService.selectlistBTypeCodeDetailTest("자유게시판"));

                log.info(retMap.get("codedetail").toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                retMap.put("status", -1);
                retMap.put("error", e.getMessage());
            }
            return retMap;
        }
        
    }

    //code 추출
    @GetMapping(value = "/findcode.bubble")
    public Map<String, Object> findcodeGET(@RequestParam(name = "codedetail") String codedetail) {
        Map<String, Object> retMap = new HashMap<>();

    
            try {
                retMap.put("status", 200);
                retMap.put("code", bService.selectlistBTypeFindCodeDetail(codedetail));

                log.info(retMap.get("code").toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                retMap.put("status", -1);
                retMap.put("error", e.getMessage());
            }
            return retMap;

        
    }


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
