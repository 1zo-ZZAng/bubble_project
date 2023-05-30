package com.example.controller.jpa;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Reserve;
import com.example.entity.Washing;
import com.example.repository.WashingRepository;
import com.example.service.jpa.ReserveService;
import com.example.service.jpa.WashingService;
import com.example.service.mybatis.WashingMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "/washing")
@RequiredArgsConstructor
@Slf4j
public class WashingController {

    //업체
    final WashingRepository wRepository;
    final WashingService wService;

    final WashingMybatisService wMybatisService;

    final ReserveService rService;

    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    /* ---------------------------------------------- */

    //홈화면
    @GetMapping(value="/home.bubble")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {

        try {

            //하단 표
            List<Reserve> list = rService.selectReserve(user.getUsername());
        
            log.info("예약내역 조회 => {}", list.toString());

            model.addAttribute("list", list);
            //

            model.addAttribute("user", user);

            return "/washing/home";


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
        
    }
    

    /* ---------------------------------------------- */

    //사업자번호 받아오기
    @GetMapping(value="/bncheck.bubble")
    public String insertGET() {
        try {
            return "/washing/bncheck";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    //사업자번호 bno 대표자명 pnm
    @PostMapping(value="/bncheck.bubble")
    public String insertPOST(@RequestParam(name="bno") String bno,
                            @RequestParam(name = "pnm") String pnm){
        try {
            httpSession.setAttribute("bno", bno);
            httpSession.setAttribute("pnm", pnm);  
            
            return "redirect:/washing/join.bubble";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/bncheck.bubble";
        }
    }


    /* ---------------------------------------------- */

    //회원가입
    @GetMapping(value="/join.bubble")
    public String joinGET(Model model) {
        try {
            model.addAttribute("wnumber", httpSession.getAttribute("bno"));
            model.addAttribute("ceo", httpSession.getAttribute("pnm"));
            
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
            wService.joinWashing(washing);

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
    public String loginGET(@RequestParam(value = "error", required = false)String error, 
                            // @RequestParam(value = "exception", required = false)String exception,  //exception에 메시지를 담아서 사용자에게 전달하기 위해 model객체를 이용해서 사용자에게 전달
                            Model model){

        try {

            model.addAttribute("error", error);
            // model.addAttribute("exception", exception); 

            return "/washing/login";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }

    }


    /* ---------------------------------------------- */

    //정보수정 (마이페이지)
    @GetMapping(value="/mypage.bubble")
    public String updateGET(@RequestParam(name = "id") String id, Model model, @ModelAttribute Washing washing, @AuthenticationPrincipal User user) {
        try {

            log.info("아이디 => {}", id.toString());

            Washing obj = wRepository.findById(id).orElse(null);

            model.addAttribute("washing", obj);
            model.addAttribute("user", user);

            return "/washing/mypage";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }

    @PostMapping(value="/mypage.bubble")
    public String updatePOST( @AuthenticationPrincipal User user, @ModelAttribute Washing washing) {
        
        try {

            log.info("정보수정 할 아이디 => {}", user.getUsername().toString());
            
            //기존 데이터 읽어오기
            Washing obj = wRepository.findById(user.getUsername()).orElse(null);

            
            obj.setPhone(washing.getPhone());
            obj.setCeo(washing.getCeo()); //대표자명
            obj.setEmail(washing.getEmail());
            obj.setName(washing.getName()); //업체명
            obj.setAddress(washing.getAddress());

            log.info("수정 정보 => {}", obj.toString());

            //변경항목 저장
            wRepository.save(obj);
            
            return "redirect:/washing/mypage.bubble?id="+user.getUsername(); 

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/mypage.bubble?id="+ user.getUsername(); 
        }
    }


    /* ------------------------------------------- */

    //비밀번호 변경
    @GetMapping(value="/pwupdate.bubble")
    public String pwupdateGET(@RequestParam(name = "id") String id, Model model, @ModelAttribute Washing washing) {
        try {

            Washing obj = wRepository.findById(id).orElse(null);

            model.addAttribute("washing", obj);

            return "/washing/pwupdate";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/mypage.bubble?id="+ washing.getId(); //페이지 수정
        }
    }


    @PostMapping(value="/pwupdate.bubble")
    public String pwupdatePOST(@RequestParam(name = "id") String id, @RequestParam(name = "newpassword") String newpassword, @AuthenticationPrincipal User user, @ModelAttribute Washing washing , Model model) {
        try {


            //기존데이터 읽어오기
            Washing obj = wRepository.findById(user.getUsername()).orElse(null);

            //암호 비교
            if(bcpe.matches(washing.getPassword(), obj.getPassword())){

                //암호화 시켜서 비밀번호 변경
                obj.setPassword(bcpe.encode(newpassword));
                

                //변경사항 저장
                wRepository.save(obj);

                model.addAttribute("msg", "비밀번호가 변경되었습니다");
                model.addAttribute("url","/bubble_bumul/washing/pwupdate.bubble?id=" + obj.getId());

                return "message";

            }

            model.addAttribute("msg", "비밀번호를 다시 확인해주세요");
            model.addAttribute("url","/bubble_bumul/washing/pwupdate.bubble?id=" + obj.getId());

            return "message";
            
            

        } catch (Exception e) {


            e.printStackTrace();
            return "redirect:/washing/pwupdate.bubble?id="+washing.getId();
        }
    }

    /* ---------------------------------------------- */

    //탈퇴
    @GetMapping(value="/delete.bubble")
    public String deleteGET(@ModelAttribute Washing washing) {
        try {
            return "/washing/delete";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }

    @PostMapping(value="/delete.bubble")
    public String deletePOST(@RequestParam(name = "id") String id, 
                            @RequestParam(name = "password") String password, 
                            @AuthenticationPrincipal User user, 
                            @ModelAttribute Washing washing , 
                            Model model, HttpServletRequest request, HttpServletResponse response) {
        try {

            //기존데이터 읽어오기
            Washing obj = wRepository.findById(user.getUsername()).orElse(null);

            //암호 비교
            if(bcpe.matches(washing.getPassword(), obj.getPassword())){

                obj.setPassword(null);
                obj.setWnumber(null);
                obj.setEmail(null);
                obj.setName("탈퇴업체");
                obj.setAddress(null);
                obj.setPhone(null);
                obj.setCeo(null);
                obj.setRole(null);
                obj.setWashingcheck(null);

                //변경항목에 저장
                wRepository.save(obj);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                }

                //탈퇴 후 메시지
                model.addAttribute("msg", "탈퇴가 완료되었습니다");
                model.addAttribute("url","/bubble_bumul/home.bubble" );

                return "message";

            }

            model.addAttribute("msg", "탈퇴 실패!");
            model.addAttribute("url","/bubble_bumul/washing/mypage.bubble?id=" + washing.getId());

            return "message";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }


    /* ---------------------------------------------- */

    //아이디 찾기
    @GetMapping(value="/findid.bubble")
    public String findidGET() {
        try {

            return "/washing/findid";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/login.bubble";
        }
    }

    @PostMapping(value="/findid.bubble")
    public String findidPOST(@RequestParam(name = "ceo") String ceo, @RequestParam(name = "email") String email, Model model) {
        try {

            Washing obj = wService.findId(ceo, email);

            if(obj != null) {

                httpSession.setAttribute("id", obj.getId());

                return "redirect:/washing/showid.bubble";

            }else{
                model.addAttribute("msg", "없는 계정입니다. 다시 확인해주세요");
                model.addAttribute("url", "/bubble_bumul/washing/findid.bubble");

                return "message";
            }

            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/findid.bubble";
        }
    }

    //아이디 찾기 완료했을 때 
    @GetMapping(value="/showid.bubble")
    public String showidGET(Model model) {
        try {

            model.addAttribute("id", httpSession.getAttribute("id"));

            httpSession.invalidate();

            return "/washing/showid";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }
    
    
    
    
    

/* =================================================================================================================================================== */


    //예약내역 조회
    @GetMapping(value="/reserve.bubble")
    public String reserveGET(@AuthenticationPrincipal User user,  Model model) { 
        try {

            model.addAttribute("user", user);
            log.info("로그인한 아이디 => {}", user.getUsername());


            List<Reserve> list = rService.selectReserve(user.getUsername());
            

            log.info("예약내역 조회 => {}", list.toString());

            model.addAttribute("list", list);
            model.addAttribute("user", user);

            return "/washing/reserve";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }
    



    
}
