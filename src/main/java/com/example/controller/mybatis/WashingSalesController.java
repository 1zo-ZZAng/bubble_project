package com.example.controller.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Reserve;
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
    public String monthGET(@AuthenticationPrincipal User user, Model model) { //@RequestParam(name = "wid") String wid,

        try {

            List<Map<String, Object>> list1 = wSalesMybatisService.selectMonthSales(user.getUsername());

            log.info("매출 1 => {} ", list1.toString());

            for(Map<String, Object> list : list1) {
            log.info("매출 for문 => {} ", list.toString());

            }


            model.addAttribute("user", user);
            model.addAttribute("list1", list1);

            return "/washing/monthsales";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
        
    }

    
    /* --------------------------------------------------------------- */
    
    //일매출
    @GetMapping(value="/daysales.bubble")
    public String dayGET(@AuthenticationPrincipal User user, Model model) { 

        try {

            List<Map<String, Object>> list1 = wSalesMybatisService.selectDaySales(user.getUsername());

            model.addAttribute("list1", list1);
            model.addAttribute("user", user);

            return "/washing/daysales";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        } 
        
    }

    /* ============================================================================== */

    //예약내역 조회
    @GetMapping(value="/reserve.bubble")
    public String reserveGET(@AuthenticationPrincipal User user,  Model model, @ModelAttribute Reserve reserve, @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {  //
        try {

            model.addAttribute("user", user);
            log.info("로그인한 아이디 => {}", user.getUsername());

            List<Reserve> list = new ArrayList<>();

            if(menu == 1){ //전체 예약 내역 조회

                list = wSalesMybatisService.selectReserveAllList(user.getUsername());

            }else if(menu == 2) { // 이용 완료 내역 조회

                list = wSalesMybatisService.selectReserveStateUseComplete(user.getUsername());

            }else if(menu == 3) { // 예약 완료 내역 조회

                list = wSalesMybatisService.selectReserveStateRevComplete(user.getUsername());

            }else if(menu == 4){ // 예약 취소 내역 조회

                list = wSalesMybatisService.selectReserveStateRevCancle(user.getUsername());

            }else { //menu값 없을 때 menu=1로 자동이동
                return "redirect:/washingsales/reserve.bubble?menu=1";
            }
            
            

            // log.info("예약내역 조회 => {}", list.toString());

            model.addAttribute("list", list);


            return "/washing/reserve";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }




    

    
}
