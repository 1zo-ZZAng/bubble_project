
package com.example.controller.mybatis;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.mybatis.CityMybatisService;
import com.example.service.mybatis.ReserveMybatisService;
import com.example.service.mybatis.WashingMachineMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReserveController {
    final CityMybatisService cityService;
    final ReserveMybatisService rService;
    final WashingMachineMybatisService wmService;

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

    // @PostMapping(value = "/letsgo.bubble")
    // public String letsgoPOST(@AuthenticationPrincipal User user,
    //                          @RequestParam(name = "wnumber") String wnumber,
    //                          @RequestParam(name = "machine") String machine,
    //                          @RequestParam(name = "machineno") Long machineno,
    //                          @RequestParam(name = "rvdate") String rvdate,
    //                          @RequestParam(name = "rvtime") String rvtime) {
    //     try {
    //         log.info(wnumber);
    //         log.info(machine);
    //         log.info(machineno.toString());
    //         log.info(rvdate);
    //         log.info(rvtime);

    //         return "redirect:/reserve/reservecomplete.bubble";
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/home.bubble";
    //     }
    // }


    // 기기번호(시퀀스) => reservation 테이블의 mno
    // Long mno = wmService.selectWashingmachineNo(wnumber, machine, machineno);
    // log.info(String.valueOf(mno));

    // 예약
    // int ret = rService.insertReserve(user.getUsername(), mno, rvdate, rvtime);
    // log.info(String.valueOf(ret));
}
