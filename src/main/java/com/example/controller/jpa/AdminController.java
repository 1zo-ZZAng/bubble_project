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
import com.example.dto.Reserve;
import com.example.dto.Washing;
import com.example.dto.AdminChkList;
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

        //로그인
        @GetMapping(value = "/login.bubble")
        public String loginGET(@RequestParam(value = "error", required = false)String error, 
                                 Model model){

            try {
    
                model.addAttribute("error", error);
                // model.addAttribute("exception", exception); 
    
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
            
           int wc =  aService.washingCount();
           //월별 총매출
           List<Map<String, Object>> msaleslist = aService.selectMonthAllSales();
           //최근예약추이
           int rvok = aService.MonthRvOkState();
           int rvcancel = aService.MonthRvCancelState();
           int rvuseok = aService.MonthUseOkState();
        //    List<Reserve> rvdatelist = aService.ReserveDateAll();
            
            //예약 날짜 목록 최신순
            List<Reserve> RvdateList = aService.selectRvdateList();
            // //예약등록 날짜 목록 최신순
            // List<Reserve> RdateList = aService.selectRdateList();
            //이번달 예약 건 수
            int mcount = aService.thisMonthRVCount();
            //매출 top1
            String top = aService.Top1Washing();

           
           log.info("msaleslist=>{}",msaleslist.toString());
            model.addAttribute("wc", wc);
            model.addAttribute("top", top);
            model.addAttribute("mcount", mcount);
            model.addAttribute("RvdateList", RvdateList);
            // model.addAttribute("RdateList", RdateList);
            model.addAttribute("msaleslist", msaleslist);


            model.addAttribute("rvok", rvok);
            model.addAttribute("rvcancel", rvcancel);
            model.addAttribute("rvuseok", rvuseok);
            // model.addAttribute("rvdatelist", rvdatelist);

            model.addAttribute("user", user);
            // log.info("{}", model);
            return "/admin/adhome";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/login.bubble";
        }
    }


    // --------------------------------------------------------------------------------------

    //업체목록 전체 조회
    @GetMapping(value = "/washinglist.bubble")
    public String wlistGET(@ModelAttribute Washing washing,Model model,@AuthenticationPrincipal User user){
        try {            
                List<Washing> list = aService.selectWList();
                log.info("{}", list.toString());
                model.addAttribute("list", list);
                // log.info("{}",model.toString());  
                //게시판 글쓰기 때문에 필요함
                model.addAttribute("user", user);
           
       
            return "/admin/wlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.bubble";
        }
    }


    
    //업체별 보유기기목록 조회 // 나중에 모달로 되면 좋겟당
    //업체 차트
    @GetMapping(value = "/wmlist.bubble")
    public String wmlistGET(Model model, @RequestParam(name = "wnumber") String wnumber,@AuthenticationPrincipal User user){
        try {
            //보유기기 표 list
            List<MachineCount> list = aService.selectMCount(wnumber);
            //월별 매출 조회 mlist
            List<Map<String,Object>> mlist = aService.selectMonthWashingChart(wnumber);
            
            //업체별 매출 조회 mdlist
            List<Map<String,Object>> mdlist = aService.selectMonthDateWashingChart(wnumber);
            //가장많이 사용하는 기기
            String topMachine = aService.Top1MachineType();
            //-----------------------차트-------------------------


            model.addAttribute("list", list);
            model.addAttribute("name", aService.selectWashingNameOne(wnumber));
            model.addAttribute("rvcount", aService.todayRVWashingCount(wnumber));
            model.addAttribute("mdlist", mdlist);
            model.addAttribute("mlist", mlist);
            model.addAttribute("topMachine", topMachine);

            //게시판 글쓰기 때문에 필요함
            model.addAttribute("user", user);

            log.info("업체별매출2 => {}",mlist.toString());
            log.info("업체별매출 => {}",mdlist.toString());
            log.info("보유기기 => {}",list.toString());

            return "/admin/wmlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/wlist.bubble";
        }
    }


    // --------------------------------------------------------------------------------------

    //업체 목록
    @GetMapping(value = "/confirm.bubble")
    public String confirmGET(Model model, @AuthenticationPrincipal User user){
            try {

                //게시판 글쓰기 때문에 필요함
                model.addAttribute("user", user);

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
                aService2.updateChk(chk[i]);
           }
            return "redirect:/admin/confirm.bubble";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/confirm.bubble";
        }
    }


    // --------------------------------------------------------------------------------------
    
    //회원목록 
    @GetMapping(value = "/customerlist.bubble")
    public String selectCustomerListGET(Model model,@AuthenticationPrincipal User user){
        try {
            List<Customer> list = aService.selectCustomer();
            log.info("{}",list.toString());
            model.addAttribute("list", list);
            //게시판 글쓰기 때문에 필요함
            model.addAttribute("user", user);

            return "/admin/customerlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.bubble";
        }
    }




    // --------------------------------------------------------------------------------------





}
