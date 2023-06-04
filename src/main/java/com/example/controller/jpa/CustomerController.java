package com.example.controller.jpa;

import java.io.Console;
import java.math.BigInteger;
import java.util.Date;
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

import com.example.dto.Reserve;
import com.example.dto.SendMail;
import com.example.entity.Customer;
import com.example.service.jpa.CustomerService;
import com.example.service.jpa.MailService;
import com.example.service.mybatis.ReserveMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerController {
    final CustomerService cService;
    final MailService mService; // 비밀번호 찾기 - 이메일 전송
    final ReserveMybatisService rService;

    // 암호화
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    final HttpSession httpSession;

    // --------------------------------------------------------------------------------------

    // 고객 홈 화면
    @GetMapping(value = {"/home.bubble", "/"}) 
    public String homeGET(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "/customer/home";
    }
    
    // --------------------------------------------------------------------------------------

    // 회원가입
    @GetMapping(value = "/join.bubble")
    public String joinGet() {
        try {
            return "/customer/join";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    @PostMapping(value = "/join.bubble")
    public String joinPOST(@ModelAttribute Customer customer) {
        try {
            customer.setPassword(bcpe.encode(customer.getPassword()));
            // log.info("Customer join => {}", customer.toString());
            Customer ret = cService.insertCustomer(customer);

            if (ret != null) {
                return "redirect:/customer/login.bubble";
            }
            else {
                return "redirect:/customer/join.bubble";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    // 소셜 로그인(카카오) - 기존 회원이 아닌 경우(이메일이 없는 경우) 회원가입
    @PostMapping(value = "/kakaojoin.bubble")
    public String kakaojoinPOST(@ModelAttribute Customer customer) {
        try {
            // Customer customer = new Customer();
            customer.setId(customer.getId());
            customer.setEmail(customer.getEmail());
            customer.setName(customer.getName());

            cService.insertCustomer(customer);

            return "redirect:/customer/home.bubble";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
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
            return "redirect:/customer/home.bubble";
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
            return "redirect:/customer/home.bubble";
        }
    }

    @PostMapping(value = "/findid.bubble")
    public String findidPOST(@RequestParam(name = "name") String name,
                             @RequestParam(name = "phone") String phone,
                             @RequestParam(name = "email") String email,
                             Model model) {
        try {
            Customer customer = cService.selectCustomerId(name, phone, email);
                                 
            if (customer != null) {
                httpSession.setAttribute("id", customer.getId());
                httpSession.setAttribute("name", customer.getName());

                return "redirect:/customer/showid.bubble";
            }
            else {
                model.addAttribute("msg", "입력하신 정보와 일치하는 아이디가 존재하지 않습니다.");
                model.addAttribute("url", "/bubble_bumul/customer/findid.bubble");

                return "message";
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }                       
    }

    @GetMapping(value = "/showid.bubble")
    public String showidGET(Model model) {
        try {
            model.addAttribute("id", httpSession.getAttribute("id"));
            model.addAttribute("name", httpSession.getAttribute("name"));

            // log.info("세션 삭제 전 => {}", httpSession.getAttribute("id"));
            // log.info("세션 삭제 전 => {}", httpSession.getAttribute("name"));

            httpSession.invalidate();

            // log.info("세션 삭제 후 => {}", httpSession.getAttribute("id"));
            // log.info("세션 삭제 후 => {}", httpSession.getAttribute("name"));

            return "/customer/showid";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    // --------------------------------------------------------------------------------------

    // 비밀번호 찾기 => 이메일로 임시 비밀번호 전송 후 DB에도 임시 비밀번호로 업데이트
    @GetMapping(value = "/findpw.bubble")
    public String findpwGET() {
        try {
            return "/customer/findpw";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    @PostMapping(value = "/findpw.bubble")
    public String findpwPOST(@ModelAttribute Customer obj, Model model) {
        try {
            // (1) 임시 비밀번호 생성
            char [] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

            String temppw = ""; // 임시 비밀번호를 저장할 변수

            // 문자 배열 길이의 값을 랜덤으로 8개를 뽑아 임시 비밀번호를 생성
            for (int i = 0; i < 8; i++){
                int idx = (int) (charSet.length * Math.random());
                temppw += charSet[idx];
            }

            // (2) 임시 비밀번호로 업데이트
            Customer customer = cService.selectCustomerPw(obj.getId(), obj.getEmail());

            if (customer != null) {
                customer.setPassword(bcpe.encode(temppw));

                cService.insertCustomer(customer);

                // (3) 메일 내용을 생성
                SendMail mail = new SendMail();
                mail.setAddress(obj.getEmail());
                mail.setTitle("Bubble Bumul 임시 비밀번호 안내 이메일입니다.");
                mail.setMessage("안녕하세요. Bubble Bumul 임시 비밀번호 안내 관련 이메일입니다.\n"
                                + customer.getName() + "님의 임시 비밀번호는 " + temppw + "입니다.\n"
                                + "로그인 후에 비밀번호를 변경해주세요.");

                // (4) 메일 전송
                mService.sendMail(mail);

                model.addAttribute("msg", "입력하신 이메일로 임시 비밀번호가 발송되었습니다.");
                model.addAttribute("url", "/bubble_bumul/customer/login.bubble");
        
                return "message";
            }
            else {
                model.addAttribute("msg", "입력하신 정보와 일치하는 계정이 존재하지 않습니다.");
                model.addAttribute("url", "/bubble_bumul/customer/findpw.bubble");
        
                return "message";
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    // --------------------------------------------------------------------------------------

    // 마이 페이지
    @GetMapping(value = "/mypage.bubble")
    public String mypageGET(Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {
        try {
            model.addAttribute("user", user);

            List<Reserve> reserveList = rService.selectReserveList(user.getUsername());
            Customer customer = cService.selectCustomerOne(user.getUsername());
            
            if (customer != null) {
                // 고객 등급
                model.addAttribute("grade", customer.getGrade());

                if (menu == 1) { // 예약 내역 조회
                    model.addAttribute("reserveList", reserveList);
                }
                else if (menu == 2) { // 회원정보 수정
                    model.addAttribute("customer", customer);
                }

                return "/customer/mypage";
            }
            else {
                return "redirect:/customer/login.bubble";
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    @PostMapping(value = "/mypage.bubble")
    public String mypagePOST(@RequestParam(name = "menu") int menu,
                             @AuthenticationPrincipal User user,
                             @ModelAttribute Customer obj, Model model) {
        try {
            if (menu == 1) { // 예약 내역 조회

            }
            else if (menu == 2) { // 회원정보 수정
                // 1. 기존 데이터를 읽음
                Customer customer = cService.selectCustomerOne(user.getUsername());
    
                if (customer != null) {
                    if (bcpe.matches(obj.getPassword(), customer.getPassword())) {
                        // 2. 변경 항목을 바꿈 (이름, 전화번호, 이메일, 주소(주소, 상세주소, 참고항목))
                        customer.setName(obj.getName());
                        customer.setPhone(obj.getPhone());
                        customer.setEmail(obj.getEmail());
                        customer.setAddress(obj.getAddress());
                        customer.setDetailaddress(obj.getDetailaddress());
                        customer.setExtraaddress(obj.getExtraaddress());
            
                        // 3. 다시 저장
                        cService.insertCustomer(customer);

                        model.addAttribute("msg", "정보 수정이 완료되었습니다.");
                        model.addAttribute("url", "/bubble_bumul/customer/mypage.bubble?menu=2");

                        return "message";
                    }
                    model.addAttribute("msg", "비밀번호를 정확하게 입력해주세요.");
                    model.addAttribute("url", "/bubble_bumul/customer/mypage.bubble?menu=2");

                    return "message";
                }
            }
    
            return "redirect:/mypage.bubble";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    // --------------------------------------------------------------------------------------

    // 예약 내역 상세
    @GetMapping(value = "/reservedetail.bubble")
    public String reserveoneGET(Model model, @AuthenticationPrincipal User user,
                                @RequestParam(name = "rvno") BigInteger rvno){
        try {
            model.addAttribute("user", user);

            model.addAttribute("reserveOne", rService.selectReserveOne(rvno));

            return "/customer/reservedetail";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    // 예약 취소
    

    // --------------------------------------------------------------------------------------

    // 비밀번호 변경
    @GetMapping(value = "/updatepw.bubble")
    public String updatepwGET(Model model, @AuthenticationPrincipal User user) {
        try {
            model.addAttribute("user", user);

            return "/customer/updatepw";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    @PostMapping(value = "/updatepw.bubble")
    public String updatepwPOST(@AuthenticationPrincipal User user,
                               @ModelAttribute Customer obj,
                               @RequestParam(name = "newpw") String newpassword,
                               Model model) {
        try {
            String message = "";

            // 1. 기존 데이터를 읽음
            Customer customer = cService.selectCustomerOne(user.getUsername());

            if (customer != null) {
                // 2. 조회된 정보의 암호와 사용자가 입력한 암호를 matches로 비교
                // 비밀번호 확인 => matches(바꾸기 전 비번, 해시된 비번)
                if (bcpe.matches(obj.getPassword(), customer.getPassword())) {
                    // 3. 비밀번호 변경 
                    customer.setPassword(bcpe.encode(newpassword));

                    // 4. 다시 저장
                    cService.insertCustomer(customer);

                    model.addAttribute("msg", "비밀번호 변경이 완료되었습니다.");
                    model.addAttribute("url", "/bubble_bumul/customer/mypage.bubble?menu=2");

                    return "message";
                }

                model.addAttribute("msg", "현재 비밀번호를 다시 입력해주세요.");
                model.addAttribute("url", "/bubble_bumul/customer/updatepw.bubble");

                return "message"; 
            } 
            else {
                return "redirect:/customer/login.bubble";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    // --------------------------------------------------------------------------------------

    // 탈퇴
    @GetMapping(value = "/delete.bubble")
    public String deleteGET(Model model, @AuthenticationPrincipal User user) {
        try {
            model.addAttribute("user", user);
            
            return "/customer/delete";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }

    @PostMapping(value = "/delete.bubble")
    public String deletePOST(@AuthenticationPrincipal User user,
                             @ModelAttribute Customer obj,
                             Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            // 1. 기존 데이터를 읽음
            Customer customer = cService.selectCustomerOne(user.getUsername());
            
            if (customer != null) {
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
                    cService.insertCustomer(customer);
        
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
            else {
                return "redirect:/login.bubble";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.bubble";
        }
    }
}
