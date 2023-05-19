package com.example.controller.jpa;

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

    // 아이디 찾기

    // 비밀번호 찾기

    // 마이 페이지
    @GetMapping(value = "/mypage.bubble")
    public String mypageGET(Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {
        try {
            if (menu == 1) { // 예약 내역 조회
                
            }
            else if (menu == 2) { // 회원정보 수정
                Customer customer = cRepository.findById(user.getUsername()).orElse(null);
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

        return "redirect:/home.bubble";
    }

    // 비밀번호 변경
    @GetMapping(value = "/updatepw.bubble")
    public String updatepwGET() {
        return "/customer/updatepw";
    }
    
    @PostMapping(value = "/updatepw.bubble")
    public String updatepwPOST(@AuthenticationPrincipal User user,
                               @ModelAttribute Customer obj,
                               @RequestParam(name = "newpassword") String newpassword) {
        // 1. 기존 데이터를 읽음
        Customer customer = cRepository.findById(user.getUsername()).orElse(null);

        // 2. 조회된 정보의 암호와 사용자가 입력한 암호를 matches로 비교
        // 비밀번호 확인 => matches(바꾸기 전 비번, 해시된 비번)
        if (bcpe.matches(obj.getPassword(), customer.getPassword())) {
            // 3. 비밀번호 변경 
            customer.setPassword(bcpe.encode(newpassword));

            // 4. 다시 저장
            cRepository.save(customer);

            return "redirect:/customer/mypage.bubble?menu=2";
        }

        return "redirect:/home.bubble";
    }

    // 탈퇴
}
