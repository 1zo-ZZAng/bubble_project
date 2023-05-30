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

    //1개 조회
    @GetMapping(value="/selectone.bubble")
    public String selectOne(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "no") long no) {
        try {

            
            Board board = bService.selectOneBoard(no);

            // model.addAttribute(null, user)
            model.addAttribute("board", board);
            model.addAttribute("user", user);

            return "/wboard/selectone";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectlist.bubble?menu"+menu;
        }
    }
    


    /* ------------------------------------------------------------- */
    
    //조회수 증가

    

    /* ------------------------------------------------------------- */

    //수정
    @GetMapping(value="/update.bubble")
    public String updateGET(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "no") long no ) {
        try {
            
            Board board = bService.selectOneBoard(no);

            model.addAttribute("board", board);
            model.addAttribute("user", user);

            return "/wboard/update";

            

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectone.bubble?no="+no;
        }
    }

    // int ret = bService.updateBoard(board);

    //         if(ret == 1){
    //             return "redirect:/wboard/selectlist.bubble?menu="+menu;
    //         }

    //         return "redirect:/wboard/selectone.bubble?no="+no;
    


    /* ------------------------------------------------------------- */

    //삭제
    @PostMapping(value="/delete.bubble")
    public String deletePOST( @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "no") long no, @AuthenticationPrincipal User user) {
        try {
            
            log.info("삭제할 게시글 번호 => {}", no);

            if(no == 0) { //no값이 0일경우 목록으로 이동
                return "redirect:/wboard/selectlist.bubble?menu="+menu;
            }

            //삭제
            int ret = bService.deleteBoard(no);

            log.info("삭제되면 1 아니면 0 => {}", ret);

            if(ret == 1) { //성공시
                return "redirect:/wboard/selectlist.bubble?menu="+menu;
            }

            return "redirect:/wboard/selectlist.bubble?menu="+menu;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectone.bubble?no="+no;
        }
    }
    


    /* ------------------------------------------------------------- */


    
    
    
}
