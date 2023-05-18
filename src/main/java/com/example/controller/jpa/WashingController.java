package com.example.controller.jpa;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Washing;
import com.example.repository.WashingRepository;
import com.example.service.jpa.WashingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Controller
@RequestMapping(value = "/washing")
@RequiredArgsConstructor
@Slf4j
public class WashingController {

    final WashingRepository wRepository;
    final WashingService wService;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    /* ---------------------------------------------- */

    //홈화면
    @GetMapping(value="/home.bubble")
    public String homeGET() {
        return "/washing/home";
    }
    

    /* ---------------------------------------------- */

    //회원가입
    @GetMapping(value="/join.bubble")
    public String joinGET() {
        try {
            return "/washing/join";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @PostMapping(value="/join.bubble")
    public String joinPOST(@ModelAttribute Washing washing) {
        try {

            //비밀번호 암호화
            washing.setPassword(bcpe.encode(washing.getPassword()));

            //확인용
            log.info("회원가입 => {}", washing.toString());

            //회원가입
            wRepository.save(washing);

            //완료 후 이동
            return "redirect:/washing/login.bubble";
            
        } catch (Exception e) { //실패시
            e.printStackTrace();
            return "redirect:/washing/join.bubble";
        }
    }

    

    /* ---------------------------------------------- */

    //로그인
    @GetMapping(value = "/login.bubble")
    public String loginGET(){

        try {
            return "washing/login";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }

    }

    @PostMapping(value = "/login.bubble")
    public String loginPOST(@ModelAttribute Washing washing){
        try {

            Washing obj = wRepository.findById(washing.getId()).orElse(null);

            if(obj != null){

                if(bcpe.matches(washing.getPassword(), obj.getPassword())){
                    
    
                    
    
                }

            } 
            
            

            return "redirect:/washing/home.bubble";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/login.bubble";
        }
    }

    /* ---------------------------------------------- */

    //정보 수정
    @GetMapping(value="/update.bubble")
    public String updateGET(@ModelAttribute Washing washing) {
        try {
            
            

            return "/washing/update";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }
    

    


    
}
