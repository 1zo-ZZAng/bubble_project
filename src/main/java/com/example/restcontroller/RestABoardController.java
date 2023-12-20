package com.example.restcontroller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    // @GetMapping(value = "/findcode.bubble")
    // public Map<String, Object> findcodeGET(@RequestParam(name = "codedetail") String codedetail) {
    //     Map<String, Object> retMap = new HashMap<>();
    //         log.info("이거=>{}",codedetail);

    //         // String codedetailurlencode =  URLEncoder.encode(codedetail, StandardCharsets.UTF_8) ;
    //         // log.info("냐냥=>{}",codedetailurlencode);
    //         try {
    //             retMap.put("status", 200);
    //             retMap.put("code", codedetail);

    //             // log.info(retMap.get("code").toString()); //확인
    //         }
    //         catch (Exception e) {
    //             e.printStackTrace();
    //             retMap.put("status", -1);
    //             retMap.put("error", e.getMessage());
    //         }
    //         return retMap;

        
    // }


    @GetMapping(value = "/findcode.bubble")
    public Map<String, Object> findcodeGET(@RequestParam(name = "codedetail") String codedetail) {
    Map<String, Object> retMap = new HashMap<>();

    try {
        // 클라이언트에서 받은 codedetail을 URL로 인코딩
        String encodedCodedetail = URLEncoder.encode(codedetail, StandardCharsets.UTF_8.toString());

        retMap.put("status", 200);
        retMap.put("encodedCode", encodedCodedetail);
    } catch (UnsupportedEncodingException e) {
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
