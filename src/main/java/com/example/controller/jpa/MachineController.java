package com.example.controller.jpa;

import javax.servlet.http.HttpSession;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/machine")
@RequiredArgsConstructor
@Slf4j
public class MachineController {

     //기기
    final MachineRepository mRepository;

    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


/* ============================================================================================== */

    //기기조회




/* ------------------------------------------------------------ */


    //기기 등록
    @GetMapping(value="/insert.bubble")
    public String machineinsertGET(@ModelAttribute Machine machine) {
        try {
            return "/machine/insert";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/machine/insert.bubble";

        }
    }

    @PostMapping(value="/insert.bubble")
    public String machineinsertPOST(@ModelAttribute Machine machine) {
        try {


            log.info("기기등록 => {}", machine.toString() );

            mRepository.save(machine);

            return "redirect:/machine/select.bubble";

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
            
            return "redirect:/washing/machineupdate.bubble"; //아마 그 기기의 번호가 필요할듯 

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/machineupdate.bubble";
        }
    }
    
}
