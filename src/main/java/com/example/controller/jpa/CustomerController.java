package com.example.controller.jpa;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerController {
    final CustomerRepository cRepository;

    // 암호화
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 회원가입
    @GetMapping(value = "/join.bubble")
    public String joinGet() {
        return "/customer/join";
    }

    @PostMapping(value = "/join.bubble")
    public String joinPOST(@ModelAttribute Customer customer) {
        customer.setPassword(bcpe.encode(customer.getPassword()));
        // log.info("Customer join => {}", customer.toString());
        cRepository.save(customer);

        return "redirect:/home.bubble";
    }
}
