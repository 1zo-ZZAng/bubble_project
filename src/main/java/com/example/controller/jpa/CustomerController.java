package com.example.controller.jpa;

import java.math.BigInteger;

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

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.jpa.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerController {
    final CustomerRepository cRepository;
    final CustomerService cService;

    // 암호화
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    final HttpSession httpSession;

    // --------------------------------------------------------------------------------------

    // 회원가입
    @GetMapping(value = "/join.bubble")
    public String joinGet() {
        try {
            return "/customer/join";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @PostMapping(value = "/join.bubble")
    public String joinPOST(@ModelAttribute Customer customer) {
        try {
            customer.setPassword(bcpe.encode(customer.getPassword()));
            // log.info("Customer join => {}", customer.toString());
            cRepository.save(customer);

            return "redirect:/customer/login.bubble";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    // --------------------------------------------------------------------------------------

    // 로그인
    // loginaction.bubble post는 만들지 않음. security에서 자동으로 처리함
    @GetMapping(value = "/login.bubble")
    public String loginGET() {
        try {
            return "/customer/login";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    // --------------------------------------------------------------------------------------

    // 아이디 찾기
    @GetMapping(value = "/findid.bubble")
    public String findidGET() {
        try {
            return "/customer/findid";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @PostMapping(value = "/findid.bubble")
    public String findidPOST(@RequestParam(name = "name") String name,
                            @RequestParam(name = "phone") String phone,
                            @RequestParam(name = "email") String email,
                            Model model) {
        Customer customer = cService.selectCustomerId(name, phone, email);
        
        if (customer != null) {
            httpSession.setAttribute("id", customer.getId());
            httpSession.setAttribute("name", customer.getName());

            return "redirect:/customer/showid.bubble";
        }

        model.addAttribute("msg", "입력하신 정보와 일치하는 아이디가 존재하지 않습니다.");
        model.addAttribute("url", "/bubble_bumul/customer/findid.bubble");

        return "message";
    }

    @GetMapping(value = "/showid.bubble")
    public String showidGET(Model model) {
        try {
            model.addAttribute("id", httpSession.getAttribute("id"));
            model.addAttribute("name", httpSession.getAttribute("name"));

            return "/customer/showid";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    // --------------------------------------------------------------------------------------

    // 비밀번호 찾기

    // --------------------------------------------------------------------------------------

    // 마이 페이지
    @GetMapping(value = "/mypage.bubble")
    public String mypageGET(Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {
        try {
            // 고객 등급
            Customer customer = cRepository.findById(user.getUsername()).orElse(null);
            model.addAttribute("grade", customer.getGrade());

            if (menu == 1) { // 예약 내역 조회
                
            }
            else if (menu == 2) { // 회원정보 수정
                model.addAttribute("customer", customer);
            }

            return "/customer/mypage";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @PostMapping(value = "/mypage.bubble")
    public String mypagePOST(@RequestParam(name = "menu") int menu,
                             @AuthenticationPrincipal User user,
                             @ModelAttribute Customer obj) {
        if (menu == 1) { // 예약 내역 조회

        }
        else if (menu == 2) { // 회원정보 수정
            // 1. 기존 데이터를 읽음
            Customer customer = cRepository.findById(user.getUsername()).orElse(null);

            // 2. 변경 항목을 바꿈 (이름, 전화번호, 이메일, 주소(주소, 상세주소, 참고항목))
            customer.setName(obj.getName());
            customer.setPhone(obj.getPhone());
            customer.setEmail(obj.getEmail());
            customer.setAddress(obj.getAddress());
            customer.setDetailaddress(obj.getDetailaddress());
            customer.setExtraaddress(obj.getExtraaddress());

            // 3. 다시 저장
            cRepository.save(customer);
            return "redirect:/customer/mypage.bubble?menu=2";
        }

        return "redirect:/mypage.bubble";
    }

    // --------------------------------------------------------------------------------------

    // 비밀번호 변경
    @GetMapping(value = "/updatepw.bubble")
    public String updatepwGET() {
        try {
            return "/customer/updatepw";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @PostMapping(value = "/updatepw.bubble")
    public String updatepwPOST(@AuthenticationPrincipal User user,
                               @ModelAttribute Customer obj,
                               @RequestParam(name = "newpw") String newpassword,
                               Model model) {
        String message = "";

        // 1. 기존 데이터를 읽음
        Customer customer = cRepository.findById(user.getUsername()).orElse(null);

        // 2. 조회된 정보의 암호와 사용자가 입력한 암호를 matches로 비교
        // 비밀번호 확인 => matches(바꾸기 전 비번, 해시된 비번)
        if (bcpe.matches(obj.getPassword(), customer.getPassword())) {
            // 3. 비밀번호 변경 
            customer.setPassword(bcpe.encode(newpassword));

            // 4. 다시 저장
            cRepository.save(customer);

            model.addAttribute("msg", "비밀번호 변경이 완료되었습니다.");
            model.addAttribute("url", "/bubble_bumul/customer/mypage.bubble?menu=2");

            return "message";
        }

        model.addAttribute("msg", "현재 비밀번호를 다시 입력해주세요.");
        model.addAttribute("url", "/bubble_bumul/customer/updatepw.bubble");

        return "message";
    }

    // --------------------------------------------------------------------------------------

    // 탈퇴
    @GetMapping(value = "/delete.bubble")
    public String deleteGET() {
        try {
            return "/customer/delete";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.bubble";
        }
    }

    @PostMapping(value = "/delete.bubble")
    public String deletePOST(@AuthenticationPrincipal User user,
                             @ModelAttribute Customer obj,
                             Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        // 1. 기존 데이터를 읽음
        Customer customer = cRepository.findById(user.getUsername()).orElse(null);

        // 2. 조회된 정보의 암호와 사용자가 입력한 암호를 matches로 비교
        // 비밀번호 확인 => matches(바꾸기 전 비번, 해시된 비번)
        if (bcpe.matches(obj.getPassword(), customer.getPassword())) {
            // 3. 아이디를 제외한 나머지 정보들은 null이나 0으로 처리
            customer.setPassword(null);
            customer.setName(null);
            customer.setPhone(null);
            customer.setEmail(null);
            customer.setAddress(null);
            customer.setDetailaddress(null);
            customer.setExtraaddress(null);
            customer.setBirth(null);
            customer.setRegdate(null);
            customer.setGrade(BigInteger.valueOf(0));
            customer.setRole(null);

            // 4. 다시 저장
            cRepository.save(customer);

            // 5. 로그아웃 -> 세션에서 제거
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }

            model.addAttribute("msg", "탈퇴가 완료되었습니다.");
            model.addAttribute("url", "/bubble_bumul/home.bubble");

            return "message";
        }

        model.addAttribute("msg", "비밀번호를 정확하게 입력해주세요.");
        model.addAttribute("url", "/bubble_bumul/customer/delete.bubble");

        return "message";
    }
}
