
package com.example.controller.mybatis;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.service.mybatis.CityMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReserveController {
    final CityMybatisService cityService;

    // 예약 화면 1
    @GetMapping(value = "/letsgo.bubble")
    public String letsgoGET(Model model, @AuthenticationPrincipal User user) {
        try {
            model.addAttribute("user", user);
            model.addAttribute("citynamelist", cityService.selectCitynameList());

            return "/reserve/reservemain";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }
    
    // 예약화면 2
    // @GetMapping(value = "/selectmachine.bubble")
    // public String selectmachineGET(Model model, 
    //                                @AuthenticationPrincipal User user,
    //                                @RequestParam(name = "wnumber") String wnumber) {
    //     try {
    //         if (wnumber != null){
    //             log.info(wnumber);
    //             model.addAttribute("user", user);
    //             return "/reserve/reservemain2";
    //         }

    //         return "redirect:/home.bubble";
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/home.bubble";
    //     }
    // }
}
