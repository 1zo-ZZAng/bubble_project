package com.example.controller.jpa;



import java.util.Collection;

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

import com.example.entity.Admin;
import com.example.repository.AdminRepository;

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
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

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


//127.0.0.1:8282/bubble_bumul/admin/login.bubble
    @GetMapping(value = "/login.bubble")
    public String loginGET(){
        try {
            return "/admin/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
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
            return "redirect:/admin/home.bubble";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/login.bubble";
        }
    }

        //임포트 shift + alt + o
     //127.0.0.1:8282/bubble_bumul/admin/home.bubble
     @GetMapping(value = "/home.bubble")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {
        try {
            model.addAttribute("user", user);
            return "/admin/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.bubble";
        }
    }




}
