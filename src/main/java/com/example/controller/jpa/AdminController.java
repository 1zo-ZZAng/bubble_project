package com.example.controller.jpa;




import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.MachineCount;
import com.example.dto.Washing;
import com.example.entity.Admin;
import com.example.entity.Customer;
import com.example.entity.WashingCheck;
import com.example.mapper.AdminMapper;
import com.example.repository.AdminRepository;
import com.example.repository.WashingMachineRepository;
import com.example.service.jpa.AdminService;
import com.example.service.jpa.CustomerService;

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
    final AdminService aService;
    final CustomerService cService;
    final WashingMachineRepository wmRepository;
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


    //업체별 보유기기목록 조회
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
    public String confirmGET(Model model,
                            @RequestParam(name="type", defaultValue = "") String type,
                            @ModelAttribute WashingCheck wChecked ){
            try {
                Washing washing = new Washing();
                washing.setChk(type);
                List<Washing> list = aService.selectWList();
                log.info("{}", type);
                log.info("{}", list.toString());
                if(type.equals("all")){
                    list = aService.selectWList();
                }
                else if(type.equals("승인 대기")){
                    list = aService.selectWlistUnchecked(type);
                    log.info("{}", list.toString());
                }
                else if(type.equals("승인 완료")){
                    list = aService.selectWlistUnchecked(type);
                }
                model.addAttribute("chklist", aService.selctChkList());
                model.addAttribute("list", list); 
                return "/admin/confirm";
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/admin/home.bubble";
            }                       
        // try {
        //     List<Washing> list = aService.selectWList();
        //     // log.info("{}", list);
        //     List<Washing> list1 = new ArrayList<>();
        //     list1 = aService.selectWlistUnchecked(type);
        //     //all일경우
        //     if(type.equals("all")){
        //         list = aService.selectWList();
        //         // log.info("{}",list);

        //     //승인 대기일 경우
        //     if(type.equals("승인 대기")){
        //         list1 = aService.selectWlistUnchecked(type);
        //         log.info("{}",list1);
        //     }
        //     //승인 완료일 경우
        //     else if(type.equals("승인 완료")){
        //         list1 = aService.selectWlistUnchecked(type);
        //         log.info("{}",list1);
        //     }
        //     model.addAttribute("list1", list1);
        //     log.info("{}",list1);
        //     }
        //     model.addAttribute("list", list);
        //     log.info("{}",list);
        //     return "admin/confirm";
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     return "redirect:/admin/home.bubble";
        // }




    }

    // --------------------------------------------------------------------------------------


    //제휴 승인
    @PostMapping(value = "/updateconfirm.bubble")
    public String updateConfirmPOST(@RequestParam(name = "chk[]") String[] chk){
        try {
            // 1. chk로 오는 사업자번호에 맞는 업체 하나 받아서 객체 생성
            // 2. 1번 객체 set~~ chk 값 변경
            // 3. 1번 객체 save
            

            return "redirect:/admin/confirm.bubble?type=unchecked";
            // List<Integer> chk = (List<Integer>) httpSession.getAttribute("chk[]");
            // List<Washing> list = aMapper.selectWlistUnchecked(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/confirm?type=unchecked";
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
