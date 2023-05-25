package com.example.controller.mybatis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.jpa.CityService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/reserve")
@RequiredArgsConstructor
public class ReserveController {
    final CityService cityService;

    // 예약 화면
    @GetMapping(value = "/letsgo.bubble")
    public String letsgoGET(Model model) {
        try {
            model.addAttribute("citynamelist", cityService.selectCitynameList());

            return "/reserve/reservemain";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }
}
