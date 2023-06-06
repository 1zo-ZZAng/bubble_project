
package com.example.controller.mybatis;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Customer;
import com.example.service.jpa.CustomerService;
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
    final CustomerService cService;

    // 예약 화면
    @GetMapping(value = "/letsgo.bubble")
    public String letsgoGET(Model model, @AuthenticationPrincipal User user) {
        try {
            Customer customer = cService.selectCustomerOne(user.getUsername());

            model.addAttribute("user", user);
            model.addAttribute("citynamelist", cityService.selectCitynameList());

            model.addAttribute("customer", customer);

            return "/reserve/reservemain";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @PostMapping(value = "/letsgo.bubble")
    public String letsgoPOST(Model model, @AuthenticationPrincipal User user,
                             @RequestParam(name = "rvno") String rvno,
                             @RequestParam(name = "wnumber") String wnumber,
                             @RequestParam(name = "machine") String machine,
                             @RequestParam(name = "machineno") Long machineno,
                             @RequestParam(name = "rvdate") String rvdate,
                             @RequestParam(name = "rvtime") String rvtime) {
        try {
            // log.info(rvno);
            // log.info(wnumber);
            // log.info(machine);
            // log.info(machineno.toString());
            // log.info(rvdate);
            // log.info(rvtime);

            // 기기번호(시퀀스) => reservation 테이블의 mno
            Long mno = wmService.selectWashingmachineNo(wnumber, machine, machineno);
            log.info(String.valueOf(mno));

            int ret = rService.insertReserve(rvno, user.getUsername(), mno, rvdate, rvtime);

            if (ret == 1) {
                return "redirect:/reserve/success.bubble";
            }
            else {
                model.addAttribute("msg", "예약에 실패하셨습니다.\n다시 시도해주세요!");
                model.addAttribute("url", "/bubble_bumul/reserve/letsgo.bubble");
        
                return "message";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @GetMapping(value = "/success.bubble")
    public String successGET(Model model, @AuthenticationPrincipal User user) {
        try {
            model.addAttribute("user", user);

            return "/reserve/success";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }
}
