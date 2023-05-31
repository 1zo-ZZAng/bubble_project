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

            List<BoardType> list1 = bService.selectlistBType(); // 게시판 종류
            List<BoardType> list2 = bService.selectlistBTypeCodeDetail(); //말머리

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
    public String writePOST(@AuthenticationPrincipal User user, 
                            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu,
                            @ModelAttribute Board board){

        try {

            log.info("내용만 => {}", board.getContent());
            log.info("작성한 내용 => {}", board.toString());
            
            int ret = bService.writeBoard(board);

            if(ret == 1){
                return "redirect:/wboard/selectlist.bubble?menu="+menu;
            }else{
                return "redirect:/wboard/write.bubble?id="+user.getUsername();
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/write.bubble?id="+user.getUsername();
        }
        
    }

    /* ------------------------------------------------------------- */

    //전체 조회 - 성공
    @GetMapping(value="/selectlist.bubble")
    public String selectlistGET(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {
        try {

            model.addAttribute("user", user);



            List<Board> list = new ArrayList<>();

            if(menu == 1){ //전체 게시판 조회

                list = bService.selectlistBoard();

                // log.info("조회 => {}", list.toString());

                model.addAttribute("list", list);


            } else if(menu == 2) { //공지사항 전체 조회

                list = bService.selectlistBoardTypeNotice();
                model.addAttribute("list", list);


            } else if(menu == 3) { //분실물 전체 조회

                list = bService.selectlistBoardTypeLost();
                model.addAttribute("list", list);

            } else if(menu == 4) { //유실물 전체 조회

                list = bService.selectlistBoardTypeGet();
                model.addAttribute("list", list);


            }else { //menu값 없을 때 menu=1로 자동이동
                return "redirect:/wboard/selectlist.bubble?menu=1";
            }

            return "/wboard/selectlist";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }

    }    

    /* ------------------------------------------------------------- */

    //1개 조회 - 성공
    @GetMapping(value="/selectone.bubble")
    public String selectOne(Model model, @AuthenticationPrincipal User user, 
                            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, 
                            @RequestParam(name = "no") long no) {
        try {

            
            Board board = bService.selectOneBoard(no);

            log.info("글 1개 조회 => {}", board.toString());

            // long next = bService.nextBoardOne(no);
            // long pre = bService.preBoardOne(no);


            model.addAttribute("board", board);
            // model.addAttribute("next", next);   //페이지
            // model.addAttribute("pre", pre); //페이지
            model.addAttribute("user", user); //로그인 관련

            return "/wboard/selectone";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectlist.bubble?menu"+menu;
        }
    }
    

    /* ------------------------------------------------------------- */

    //수정 - 진행중
    @GetMapping(value="/update.bubble")
    public String updateGET(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "no") long no) {
        try {


            Board board = bService.selectOneBoard(no);

            List<BoardType> list1 = bService.selectlistBType();
            List<BoardType> list2 = bService.selectlistBTypeCodeDetail();

            // log.info("게시판 종류=>{}",list1.toString());
            // log.info("말머리 종류=>{}",list2.toString());

            // List<Board> list1 = bService.selectlistBoard();

            model.addAttribute("CodeName", list1);
            model.addAttribute("CodeDetail", list2);

            model.addAttribute("board", board);
            model.addAttribute("user", user);

            return "/wboard/update";

            

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectone.bubble?no="+no;
        }
    }

    @PostMapping(value="/update.bubble")
    public String updatePOST(@ModelAttribute Board board, @RequestParam(name = "no") long no ) {
        try {

            Board obj = bService.selectOneBoard(no);

            log.info("수정 완료 => {}",board.toString());

            int ret = bService.updateBoard(board);

            if(ret == 1){
                return "redirect:/wboard/selectone.bubble?no="+obj.getNo();
            }
        
            return "redirect:/wboard/update.bubble?no="+obj.getNo();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectone.bubble?no="+no;
        }
    }
    


    


    /* ------------------------------------------------------------- */

    //삭제 - 성공
    @PostMapping(value="/delete.bubble")
    public String deletePOST( @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "no") long no, @AuthenticationPrincipal User user, Model model) {
        try {
            
            log.info("삭제할 게시글 번호 => {}", no);

            if(no == 0) { //no값이 0일경우 목록으로 이동
                return "redirect:/wboard/selectlist.bubble?menu="+menu;
            }

            //삭제
            int ret = bService.deleteBoard(no);

            log.info("삭제되면 1 아니면 0 => {}", ret);

            if(ret == 1) { //성공시

                model.addAttribute("msg", "삭제되었습니다");
                model.addAttribute("url","/bubble_bumul/wboard/selectlist.bubble?menu=" + menu);

                return "message";

                // return "/wboard/selectlist.bubble?menu=" + menu;

            }

            model.addAttribute("msg", "삭제 실패");
            model.addAttribute("url","/bubble_bumul/wboard/selectlist.bubble?menu=" + menu);

            return "message";

            // return "/wboard/selectlist.bubble?menu=" + menu;

        

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectone.bubble?no="+no;
        }
    }
    


    /* ------------------------------------------------------------- */


    
    
    
}
