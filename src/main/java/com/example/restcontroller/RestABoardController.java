package com.example.restcontroller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @CrossOrigin
    @GetMapping(value = "/findcode.bubble")
    public Map<String, Object> findcodeGET(@RequestParam(name = "codedetail") String codedetail) {
    Map<String, Object> retMap = new HashMap<>();

    try {
        // 클라이언트에서 받은 codedetail을 URL로 인코딩
        // String encodedCodedetail = URLEncoder.encode(codedetail, StandardCharsets.UTF_8.toString());

        retMap.put("status", 200);
        retMap.put("codedetail",codedetail);
        // retMap.put("encodedCode", encodedCodedetail);
    } catch (Exception e) {
        e.printStackTrace();
        retMap.put("status", -1);
        retMap.put("error", e.getMessage());
    }

    return retMap;
}



    //조회수 증가
    //127.0.0.1:8282/bubble_bumul/api/aboard/updatehit.bubble?no=
    @PostMapping(value="/updatehit.bubble")
    public Map<String, Integer> updatehitPOST( @RequestParam(name = "no") long no) {

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
