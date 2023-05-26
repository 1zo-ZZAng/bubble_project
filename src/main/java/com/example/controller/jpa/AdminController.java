package com.example.controller.jpa;




import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.MachineCount;
import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Admin;

import com.example.mapper.AdminMapper;
import com.example.repository.AdminRepository;
import com.example.repository.WashingMachineRepository;


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
    final AdminMapper aMapper;
    final WashingMachineRepository wmRepository;
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
    @GetMapping(value = "/login.bubble")
    public String loginGET(){
        try {
            return "/admin/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.bubble";
        }
    }

    @PostMapping(value = "/loginaction.bubble")
    public String loginActionPOST(@ModelAttribute Admin admin){
        try {
            log.info("loginAction => {}", admin.toString());

            Admin obj = aRepository.findById(admin.getId()).orElse(null);

            //암호비교
            if(bcpe.matches(obj.getPassword(), admin.getPassword())){
               aRepository.save(obj);
               log.info(format, obj);
            }
            return "redirect:/admin/main.bubble";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/login.bubble";
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
    @GetMapping(value = "/wlist.bubble")
    public String wlistGET(@ModelAttribute Washing washing,Model model){
        try {            
                List<Washing> list = aMapper.selectWList();
                // log.info("{}", list.toString());
                model.addAttribute("list", list);               
       
            return "/admin/wlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.bubble";
        }
    }


    //업체별 보유기기목록 조회
    @GetMapping(value = "/wmlist.bubble")
    public String wmlistGET(Model model, @RequestParam(name = "wnumber") String wnumber){
        try {
            List<MachineCount> list = aMapper.selectMCount(wnumber);

            log.info("{}",list.toString());
            model.addAttribute("list", list);
            model.addAttribute("name", aMapper.selectWashingNameOne(wnumber));
            return "/admin/wmlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/wlist.bubble";
        }
    }

}
