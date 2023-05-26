package com.example.controller.mybatis;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Board;
import com.example.dto.BoardType;
import com.example.service.mybatis.BoardMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequestMapping(value = "/wboard")
@RequiredArgsConstructor
@Slf4j
public class WBoardController {

    final BoardMybatisService bService;


/* =========================================================================================================== */

    //개피곤해




    
/* =========================================================================================================== */

    //글작성
    @GetMapping(value = "/write.bubble")
    public String writeGET(@AuthenticationPrincipal User user, Model model){
        try {

            List<BoardType> list1 = bService.selectlistBTypeCodeName();
            List<BoardType> list2 = bService.selectlistBTypeCodeDetail();

            log.info("게시판 종류=>{}",list1.toString());
            log.info("말머리 종류=>{}",list2.toString());


            model.addAttribute("CodeName", list1);  
            model.addAttribute("CodeDetail", list2);  

            model.addAttribute("user", user);

            return "/wboard/write";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }

    @PostMapping(value = "/write.bubble")
    public String writePOST(@ModelAttribute Board board){
        try {
            
            log.info("글 작성 내용 => {}", board.toString());

            bService.writeBoard(board);

            return "redirect:/wboard/selectlist.bubble";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/write.bubble";
        }
    }

    /* ------------------------------------------------------------- */

    //전체 조회
    @GetMapping(value="/selectlist.bubble")
    public String selectlistGET(Model model, @ModelAttribute Board board, @AuthenticationPrincipal User user) {
        try {

            model.addAttribute("user", user);
            model.addAttribute("board", board);

            return "/wboard/selectlist";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }
    
    
}
