package com.example.controller.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Board;
import com.example.dto.BoardType;
import com.example.dto.Washing;
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
    //검색창

    //페이지네이션



    
/* =========================================================================================================== */

    //글작성
    @GetMapping(value = "/write.bubble")
    public String writeGET(@AuthenticationPrincipal User user, Model model, @ModelAttribute Washing washing){
        try {

            List<BoardType> list1 = bService.selectlistBTypeCodeName();
            List<BoardType> list2 = bService.selectlistBTypeCodeDetail();

            log.info("게시판 종류=>{}",list1.toString());
            log.info("말머리 종류=>{}",list2.toString());


            model.addAttribute("CodeName", list1);
            model.addAttribute("CodeDetail", list2);

            model.addAttribute("washing", washing);
            model.addAttribute("user", user);

            return "/wboard/write";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }

    @PostMapping(value = "/write.bubble")
    public String writePOST(@RequestParam(name = "title") String title, 
                            @RequestParam(name = "content", required = false) String content, 
                            @RequestParam(name = "writer") String writer, 
                            @RequestParam(name = "code") long code, @AuthenticationPrincipal User user){
        try {

            log.info(content);
            
            Board obj = new Board();
            obj.setCode(code);
            obj.setTitle(title);
            obj.setContent(content);
            obj.setWriter(writer);
            
            log.info("글 작성 내용 => {}", obj.toString());

            int ret = bService.writeBoard(obj);

            if(ret == 1){
                return "redirect:/wboard/selectlist.bubble";
            }else{
                return "redirect:/wboard/write.bubble";
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    /* ------------------------------------------------------------- */

    //전체 조회
    @GetMapping(value="/selectlist.bubble")
    public String selectlistGET(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {
        try {

            model.addAttribute("user", user);



            // List<Board> list = new ArrayList<>();

            if(menu == 1){ //전체 게시판 조회

                List<Board> list = bService.selectlistBoard();

                log.info("조회 => {}", list.toString());

                model.addAttribute("list", list);
                // return "/wboard/menu/menu1";
            } 

            // } else if(menu == 2) { //공지사항 전체 조회

            //     list = bService.selectlistBoardTypeNotice();
            //     model.addAttribute("list", list);
            //     // return "/wboard/menu/menu2";

            // } else if(menu == 3) { //분실물 전체 조회

            //     list = bService.selectlistBoardTypeLost();
            //     model.addAttribute("list", list);
            //     // return "/wboard/menu/menu3";

            // } else if(menu == 4) { //유실물 전체 조회

            //     list = bService.selectlistBoardTypeGet();
            //     model.addAttribute("list", list);
            //     // return "/wboard/menu/menu4";

            // }

            return "/wboard/selectlist";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }

    }

    // @PostMapping(value="/selectlist.bubble")
    // public String selectlistPOST(@RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {
    //     try {
            
    //         if(menu == 0){

    //             return "redirect:/wboard/selectlist?menu=1";

    //         }

    //         return "redirect:/wboard/seleclist?menu"+menu;


    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/washing/home.bubble";
    //     }
    // }
    

    /* ------------------------------------------------------------- */
    
    //조회수 증가

    

    /* ------------------------------------------------------------- */

    //수정


    /* ------------------------------------------------------------- */

    //삭제

    /* ------------------------------------------------------------- */


    
    
    
}
