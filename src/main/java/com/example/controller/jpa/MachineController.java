package com.example.controller.jpa;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
import com.example.repository.MachineRepository;
import com.example.repository.WashingRepository;
import com.example.service.jpa.MachineService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "/machine")
@RequiredArgsConstructor
@Slf4j
public class MachineController {


    //업체
    final WashingRepository wRepository;

     //기기
    final MachineRepository mRepository;
    final MachineService mService;

    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


/* ============================================================================================== */

    //기기조회

    @GetMapping(value="/selectlist.bubble")
    public String selectlistGET(Model model, @ModelAttribute Machine machine, @RequestParam(name = "wid") String wid, @AuthenticationPrincipal User user) {
        try {

            List<Machine> list = mRepository.findByWashing_idOrderByNoDesc(wid);

            model.addAttribute("wid", user.getUsername()); 
            model.addAttribute("list", list);

            return "/machine/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/homde.bubble";
        }
    }
    



/* ------------------------------------------------------------ */


    //기기 등록
    @GetMapping(value="/insert.bubble")
    public String machineinsertGET( Model model, @AuthenticationPrincipal User user, @ModelAttribute Machine machine) {

        try {
            
            model.addAttribute("wid", user.getUsername()); 

            model.addAttribute("machine", machine);



            return "/machine/insert";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/machine/insert.bubble";

        }
    }

    @PostMapping(value="/insert.bubble")
    public String machineinsertPOST(@ModelAttribute Machine machine) {
        try {


            mRepository.save(machine);

            return "redirect:/machine/selectlist.bubble?wid=" + machine.getWashing().getId();

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/machine/insert.bubble";

        }
    }

    /* ---------------------------------------------- */

    //기기수정
    @SuppressWarnings("unchecked")
    @GetMapping(value="/update.bubble")
    public String updateGET(Model model, @AuthenticationPrincipal User user) {
        try {
            
            
            List<BigInteger> chk = (List<BigInteger>) httpSession.getAttribute("chk[]");
            List<Machine> list = mRepository.findAllById(chk);

            log.info( "수정하려는 기기 정보들 => {}", list.toString());

            model.addAttribute("wid", user.getUsername()); 
            model.addAttribute("list", list);

            return "/machine/update";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }


    @PostMapping(value = "/update.bubble")
    public String updatePOST(@RequestParam(name = "chk[]") List<BigInteger> chk){
        try {
            
            log.info("수정하려는 세탁기 번호 => {}", chk.toString());

            httpSession.setAttribute("chk[]", chk);

            return "redirect:/machine/update.bubble";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }

    //여기서 진짜로 수정
    @PostMapping(value = "/updateaction.bubble")
    public String updateactionPOST(@RequestParam(name = "no[]") BigInteger[] no, 
                                    @RequestParam(name = "type[]") String[] type,
                                    @RequestParam(name = "typeno[]") BigInteger[] typeno,
                                    @RequestParam(name = "time[]") BigInteger[] time,
                                    @RequestParam(name = "price[]") BigInteger[] price,
                                    @AuthenticationPrincipal User user
                                    ){

        try {

            List<Machine> list = new ArrayList<>();

            

            for(int i = 0; i < no.length; i++){
                
                //no를 이용해서 기존 정보 가져오기
                Machine obj = mRepository.findById(no[i]).orElse(null);

                obj.setType(type[i]);
                obj.setTypeno(typeno[i]);
                obj.setTime(time[i]);
                obj.setPrice(price[i]);

                log.info("수정은 이렇게 할 거임 => {}", obj.toString());
                
                list.add(obj);

            
            }

            //저장
            mRepository.saveAll(list); 

            //주소문제로 판결 => 왜 안됨? => user로 받았네 주소를 그니깐 안됐음 힝구
            // return "redirect:/washing/home.bubble";
            return "redirect:/machine/selectlist.bubble?wid=" + user.getUsername();

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/machine/selectlist.bubble?wid="+ user.getUsername();


        }
    }
    


    /*-------------------------------------------- */

    //기기 일괄 삭제
    //127.0.0.1:8282/bubble_bumul/machine/selectlist.wid=wid;
    @PostMapping(value="/delete.bubble")
    public String deletePOST(@RequestParam(name = "chk[]") List<Machine> chk, @AuthenticationPrincipal User user) { //
        try {

            

            log.info("삭제하려는 세탁기 번호 => {}", chk.toString());

            //로그는 찍히는 상황
            //삭제가 안되는 상황 어떻게 해야할지 고민 중..

            mRepository.deleteAll();
            
            
            return "redirect:/machine/selectlist.bubble?wid=" + user.getUsername();

            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/machine/selectlist.bubble?wid=" + user.getUsername();
        }
    }
    
    
}
