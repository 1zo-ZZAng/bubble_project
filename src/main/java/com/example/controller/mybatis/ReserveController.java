package com.example.controller.mybatis;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.mybatis.CityMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReserveController {
    final CityMybatisService cityService;

    // 예약 화면
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


}
