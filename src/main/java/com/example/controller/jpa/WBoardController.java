package com.example.controller.jpa;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Board;
import com.example.entity.Washing;
import com.example.repository.WBoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequestMapping(value = "/wboard")
@RequiredArgsConstructor
@Slf4j
public class WBoardController {

    final WBoardRepository wbRepository;



    @GetMapping(value = "/write.bubble")
    public String writeGET(@AuthenticationPrincipal User user, Model model, @ModelAttribute Washing washing){
        try {

            model.addAttribute("user", user);
            model.addAttribute("washing", washing);

            

            return "/wboard/write";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }


    // @PostMapping(value="/write.bubble")
    // public String writePOST() {
    //     try {
            
    //     } catch (Exception e) {
            
    //     }
    // }
    


    @GetMapping(value="/selectlist.bubble")
    public String selectlistGET(Model model) {
        try {
            
            

            return "/wboard/selectlist";

        } catch (Exception e) {
            return "redirect:/washing/home.bubble";
        }
    }
    
    
}
