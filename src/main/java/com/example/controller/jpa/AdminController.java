package com.example.controller.jpa;




import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.MachineCount;
import com.example.dto.Washing;
import com.example.dto.Category;
import com.example.entity.Admin;
import com.example.entity.Customer;
import com.example.entity.WashingCheck;
import com.example.mapper.AdminMapper;
import com.example.repository.AdminRepository;
import com.example.repository.WashingCheckRepository;
import com.example.repository.WashingMachineRepository;
import com.example.repository.WashingRepository;
import com.example.service.jpa.AdminService;
import com.example.service.jpa.CustomerService;
import com.example.service.mybatis.AdminListMybatisService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    
    final String format = "AdminController => {}";
    final AdminRepository aRepository;

    final AdminService aService;
    final AdminListMybatisService aService2;
    final CustomerService cService;
    final WashingCheckRepository wcRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    // --------------------------------------------------------------------------------------
    //관리자 회원가입
    //127.0.0.1:8282/bubble_bumul/admin/join.bubble
    @GetMapping(value = "/join.bubble")
    public String joinGET() {
        return "/admin/join"; // templates/admin/join.html
    }


    @PostMapping(value = "/join.bubble")
    public String joinPOST(@ModelAttribute Admin admin) {
        try{
            admin.setPassword(bcpe.encode(admin.getPassword()));
            admin.setRole("ADMIN");
            log.info("{}", admin.toString());
            aRepository.save(admin);
            return "redirect:/admin/join.bubble";
        }
        catch( Exception e){
            e.printStackTrace();
            return "redirect:/admin/join.bubble";
        }


    }

    // --------------------------------------------------------------------------------------
    //관리자 로그인
    //127.0.0.1:8282/bubble_bumul/admin/login.bubble
    // loginaction.bubble post는 만들지 않음. security에서 자동으로 처리함
    @GetMapping(value = "/login.bubble")
    public String loginGET(){
        try {
            return "/admin/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.bubble";
        }
    }


    // --------------------------------------------------------------------------------------

    //관리자 홈
    //127.0.0.1:8282/bubble_bumul/admin/home.bubble
     @GetMapping(value = "/home.bubble")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {
        try {
            model.addAttribute("user", user);
            log.info("{}", model);
            return "/admin/adhome";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/login.bubble";
        }
    }


    // --------------------------------------------------------------------------------------

    //업체목록 전체 조회
    @GetMapping(value = "/washinglist.bubble")
    public String wlistGET(@ModelAttribute Washing washing,Model model){
        try {            
                List<Washing> list = aService.selectWList();
                // log.info("{}", list.toString());
                model.addAttribute("list", list);               
       
            return "/admin/wlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.bubble";
        }
    }


    //업체별 보유기기목록 조회 // 나중에 모달로 되면 좋겟당
    @GetMapping(value = "/wmlist.bubble")
    public String wmlistGET(Model model, @RequestParam(name = "wnumber") String wnumber){
        try {
            List<MachineCount> list = aService.selectMCount(wnumber);

            log.info("{}",list.toString());
            model.addAttribute("list", list);
            model.addAttribute("name", aService.selectWashingNameOne(wnumber));
            return "/admin/wmlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/wlist.bubble";
        }
    }


    // --------------------------------------------------------------------------------------

    //업체 목록
    @GetMapping(value = "/confirm.bubble")
    public String confirmGET(Model model ){
            try {

                model.addAttribute("category", aService2.selectBoxList());      
                return "/admin/confirm";
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/admin/home.bubble";
            }                       
   


    }

    // --------------------------------------------------------------------------------------


    // //제휴 승인
    @PostMapping(value = "/updatechk.bubble")
    public String updatechkPOST(@RequestParam(name = "chk") String[] chk){
        try {
           for (int i=0; i<chk.length; i++) {
            log.info(chk[i]);
           }
            // log.info(chk);

            return "redirect:/admin/confirm.bubble";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/confirm.bubble";
        }
    }


    // --------------------------------------------------------------------------------------
    
    //회원목록 
    @GetMapping(value = "/customerlist.bubble")
    public String selectCustomerListGET(Model model){
        try {
            List<Customer> list = cService.findAllByOrderByNameAsc();
            log.info("{}",list.toString());
            model.addAttribute("list", list);

            return "/admin/customerlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.bubble";
        }
    }


}
