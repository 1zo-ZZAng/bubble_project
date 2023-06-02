package com.example.controller.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.mybatis.WashingSalesMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "/washingsales")
@RequiredArgsConstructor
@Slf4j
public class WashingSalesController {


    final WashingSalesMybatisService wSalesMybatisService;


/* ============================================================================== */

    //월 매출
    @GetMapping(value="/monthsales.bubble")
    public String getMethodName(@AuthenticationPrincipal User user, Model model) { //@RequestParam(name = "wid") String wid,

        try {

            List<Map<String, Object>> list1 = wSalesMybatisService.selectMonthSales(user.getUsername());

            log.info("매출 1 => {} ", list1.toString());

            // for(Map<String, Object> list : list1) {
            // log.info("매출 for문 => {} ", list.toString());

            // }


            model.addAttribute("user", user);
            model.addAttribute("list1", list1);

            return "/washing/monthsales";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
        
    }

    
    /* --------------------------------------------------------------- */
    
    //연매출

    

    
}
