package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.WashingRepository;
import com.example.service.jpa.WashingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/api/washing")
@RequiredArgsConstructor
@Slf4j
public class RestWashingController {

    final WashingRepository wRepository;
    final WashingService wService;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); 


    //아이디 중복 확인
    //127.0.0.1:8282/bubble_bumul/api/washing/idcheck.json?id=아이디
    @GetMapping(value="/idcheck.json")
    public Map<String, Object> idcheckGET(@RequestParam(name = "id") String id) {

        Map<String, Object> retMap = new HashMap<>();

        try {

            log.info("아이디 => {}", id );

            retMap.put("status", 200);
            retMap.put("washing", wService.washingIDCheck(id) ); //html에서 .washing으로 확인할 수 있음
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    /* ------------------------------------------------------------- */
    



    
}
