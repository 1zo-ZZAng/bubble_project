package com.example.controller.jpa;

import javax.servlet.http.HttpSession;

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

import com.example.entity.Machine;
import com.example.entity.Washing;
import com.example.repository.MachineRepository;
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

    final MachineRepository mRepository;

    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    /* ---------------------------------------------- */

    //홈화면
    @GetMapping(value="/home.bubble")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {

        try {
            model.addAttribute("user", user);
            return "/washing/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
        
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
    public String loginGET(@RequestParam(value = "error", required = false)String error, 
                            @RequestParam(value = "exception", required = false)String exception,  //exception에 메시지를 담아서 사용자에게 전달하기 위해 model객체를 이용해서 사용자에게 전달
                            Model model){

        try {

            model.addAttribute("error", error);
            model.addAttribute("exception", exception); 

            return "/washing/login";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }

    }


    /* ---------------------------------------------- */

    //정보수정 (마이페이지)
    @GetMapping(value="/mypage.bubble")
    public String updateGET(@RequestParam(name = "id") String id, Model model, @ModelAttribute Washing washing) {
        try {

            Washing obj = wRepository.findById(id).orElse(null);

            model.addAttribute("washing", obj);

            return "/washing/mypage";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }

    @PostMapping(value="/mypage.bubble")
    public String updatePOST( @AuthenticationPrincipal User user, @ModelAttribute Washing washing) {
        
        try {
            
            //기존 데이터 읽어오기
            Washing obj = wRepository.findById(user.getUsername()).orElse(null);
            
            obj.setPhone(washing.getPhone());
            obj.setCeo(washing.getCeo()); //대표자명
            obj.setEmail(washing.getEmail());
            obj.setName(washing.getName()); //업체명
            obj.setAddress(washing.getAddress());

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
            }

            model.addAttribute("msg", "비밀번호가 변경되었습니다");
            model.addAttribute("url","/bubble_bumul/washing/pwupdate.bubble?id="+washing.getId());


            // return "redirect:/washing/pwupdate.bubble?id="+washing.getId();
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
    
    

    
    


    /* ---------------------------------------------- */

    //기기 추가
    @GetMapping(value="/machineinsert.bubble")
    public String machineinsertGET() {
        try {
            return "/washingmachine/machineinsert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washin/home.bubble";
        }
    }

    @PostMapping(value="/machineinsert.bubble")
    public String machineinsertPOST(@ModelAttribute Machine machine) {
        try {

            mRepository.save(machine);

            return "redirect:/washing/machineinsert.bubble";
        } catch (Exception e) {
            return "redirect:/washing/machineinsert.bubble";
        }
    }

    /* ---------------------------------------------- */

    //기기수정
    @GetMapping(value="/machineupdate.bubble")
    public String machineupdateGET(Model model, @RequestParam(name = "no") int no) {
        try {
            
            return "redirect:/washing/machineupdate.bubble"; //아마 그 기기의 번호가 필요할듯 

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/machineupdate.bubble";
        }
    }
    
    
    
    
    
    

    


    
}
