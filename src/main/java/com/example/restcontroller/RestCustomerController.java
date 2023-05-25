package com.example.restcontroller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Customer;
import com.example.service.jpa.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
@Slf4j
public class RestCustomerController {
    final CustomerService cService;

    // 아이디 중복 확인
    // 127.0.0.1:8282/bubble_bumul/api/customer/idcheck.json?id=아이디
    @GetMapping(value = "/idcheck.bubble")
    public Map<String, Object> idcheckGET(@RequestParam(name = "id") String id) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            retMap.put("status", 200);
            retMap.put("ret", cService.selectCustomerIDCheck(id));
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }

        return retMap;
    }

    // 소셜 로그인 (카카오)
    // view에서 받은 이메일이 기존 테이블에 있으면(ret=1) 회원가입 필요 X
    //                                      없으면(ret=0) 회원가입 필요 O
    @PostMapping(value = "/kakaologin.bubble")
    public Map<String, Object> kakaologinPOST(@RequestBody Map<String, Object> map) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            int emailchk = cService.countCustomerEmailCheck(map.get("email").toString());

            retMap.put("status", 200);

            // (2) 세션에 저장할 객체 생성하기 (저장할 객체, null)
            String[] strRole = {"ROLE_CUSTOMER"};
            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);

            // DetailsService를 사용하지 않고 세션에 저장하기
            if (emailchk == 1) { // 카카오 계정 이메일이 기존 테이블에 있는 경우
                // (1) 기존 자료 읽기
                Customer customer = cService.selectCustomerEmail(map.get("email").toString());

                // (2) 세션에 저장할 객체 생성하기 (저장할 객체, null)
                User user = new User(customer.getId(), "", role);
                UsernamePasswordAuthenticationToken authenticationToken 
                    = new UsernamePasswordAuthenticationToken(user, null, role);

                // (3) 수동으로 세션에 저장(로그인)
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context); 

                retMap.put("ret", emailchk);
            }
            else { // 카카오 계정 이메일이 기존 테이블에 없는 경우
                // (2) 세션에 저장할 객체 생성하기 (저장할 객체, null)
                User user = new User(map.get("id").toString(), "", role);
                UsernamePasswordAuthenticationToken authenticationToken 
                    = new UsernamePasswordAuthenticationToken(user, null, role);

                // (3) 수동으로 세션에 저장(로그인)
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);

                retMap.put("ret", emailchk);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}
