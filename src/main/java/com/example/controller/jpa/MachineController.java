package com.example.controller.jpa;


import java.math.BigInteger;
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
    @GetMapping(value="/machineupdate.bubble")
    public String machineupdateGET(Model model, @RequestParam(name = "no") int no) {
        try {
            
            
            // model.addAttribute("machine", obj);

            return "/machine/update";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/machineupdate.bubble";
        }
    }

    /*-------------------------------------------- */

    //기기삭제
    @PostMapping(value="/delete.bubble")
    public String deletePOST(@ModelAttribute Machine machine, @RequestParam(name = "chk[]") List<BigInteger> chk) {
        try {


            log.info("삭제 => {}", chk.toString());

            // mRepository.deleteAll(mRepository.findAllById(chk));
            mRepository.deleteAllById(chk);

            return "redirect:/machine/selectlist.bubble?wid=" + machine.getWashing().getId();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/machine/selectlist.bubble?wid=" + machine.getWashing().getId();
        }
    }
    
    
}
