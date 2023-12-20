package com.example.controller.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Board;
import com.example.dto.BoardAdmin;
import com.example.dto.BoardGetLost;
import com.example.dto.BoardType;
import com.example.dto.BoardView;
import com.example.dto.BoardWashing;
import com.example.dto.Reply;
import com.example.dto.Washing;
import com.example.entity.Admin;
import com.example.service.mybatis.BoardMybatisService;
import com.example.service.mybatis.BoardViewMybatisService;
import com.example.service.mybatis.BoardWashingMybatisService;
import com.example.service.mybatis.ReplyMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Controller
@RequestMapping(value = "/aboard")
@RequiredArgsConstructor
@Slf4j
public class ABoardController {

    final BoardMybatisService bService; //게시판

    final ReplyMybatisService rService; // 댓글

    final BoardViewMybatisService bvService; //게시판 + 카테고리 view

    final BoardWashingMybatisService bwService; // boardview + washing view
    
/* =========================================================================================================== */

    //글작성
    // @GetMapping(value = "/write.bubble")
    // public String writeGET(@AuthenticationPrincipal User user, Model model, @ModelAttribute Admin admin ){
    //     try {

    //         //해야할 일 : 말머리에 맞는 codedetail 나오게 하기 (code 활용해보기)
    //         // BoardType bType = bService.selectlistBTypeAdmin(); 
    //         List<BoardType> list1 = bService.selectlistBType();// 게시판 종류

    //         model.addAttribute("CodeName", list1);            

    //         model.addAttribute("user", user);

    //         log.info("게시판 종류=>{}",list1.toString());

    //         return "/aboard/write";
            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/washing/home.bubble";
    //     }
    // }

    // @PostMapping(value = "/write.bubble")
    // public String writePOST(@AuthenticationPrincipal User user, 
    //                         @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, 
    //                         @RequestBody Map<String, String> requestData,
    //                         @ModelAttribute Board board){

    //                             //레스트컨으롤러에서 받은 코드값 넣기
    //     try {
    //         log.info("menu=>{}",menu);
    //         String codedetail = requestData.get("codedetail");
    //         log.info("coddde=>{}",codedetail.toString());
    //         board.setRole(user.getAuthorities().toString());
    //         board.setCode(bService.selectlistBTypeFindCodeDetail(codedetail));
    //         log.info("내용만 => {}", board.getContent());
    //         log.info("작성한 내용 => {}", board.toString());
            
    //         int ret = bService.writeBoard(board);

    //         if(ret == 1){

    //             return "redirect:/aboard/selectlist.bubble?menu="+menu;
                
    //         }else{
    //             return "redirect:/aboard/write.bubble?id="+user.getUsername();
    //         }
            

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/aboard/write.bubble?id="+user.getUsername();
    //     }
        
    // }


    @GetMapping(value = "/write.bubble")
    public String writeGET(@AuthenticationPrincipal User user, Model model){
    try {
        List<BoardType> list1 = bService.selectlistBType();
        model.addAttribute("CodeName", list1);
        model.addAttribute("user", user);
        log.info("게시판 종류=>{}", list1.toString());
        return "/aboard/write";
    } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/washing/home.bubble";
        }
    }

    @PostMapping(value = "/write.bubble")
    public String writePOST(@AuthenticationPrincipal User user, 
                        @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, 
                        @RequestParam(name = "codedetail") String codedetail,
                        @ModelAttribute Board board){
    try {
        log.info("menu=>{}", menu);
        log.info("coddde=>{}", codedetail);
        board.setRole(user.getAuthorities().toString());
        board.setCode(bService.selectlistBTypeFindCodeDetail(codedetail));
        log.info("내용만 => {}", board.getContent());
        log.info("작성한 내용 => {}", board.toString());
        
        int ret = bService.writeBoard(board);

        if (ret == 1) {
            return "redirect:/aboard/selectlist.bubble?menu=" + menu;
        } else {
            return "redirect:/aboard/write.bubble?id=" + user.getUsername();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/aboard/write.bubble?id=" + user.getUsername();
        }
    }

    /* ------------------------------------------------------------- */

    //전체 조회
    @GetMapping(value="/selectlist.bubble")
    public String selectlistGET(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "type", required = false, defaultValue = "notice") String type,
                                             @RequestParam(name = "menu", required = false, defaultValue = "null") String menu,
                                             @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        try {

            model.addAttribute("user", user);

            
            List<BoardAdmin> list = new ArrayList<>();            
            List<BoardView> list2 = new ArrayList<>();            
            List<BoardGetLost> gllist = new ArrayList<>();


            //총 게시글 개수
            int totalPageCount= 0;

            //공지사항 조회할 때 관리자입장에서는
            //1.관리자 전체 페이지 2.관리자 중요 페이지 가 필요할거같음
            if (page == 0) {
                return "redirect:/wboard/selectlist.bubble?type=notice&page=1";
            }
            if(type.equals("notice")){ //공지사항 조회(관리자)

                list2 = bvService.selectBoardView(page*10-9, page*10);
                totalPageCount = bwService.selectBoardAllNoticeCount();


                // log.info("카테고리 전체 조회 => {}", list1.toString());

                model.addAttribute("list", list2);

            } else if(type.equals("getlost")) { //분실물 / 습득물 전체 조회

                list2 = bvService.selectBoardViewGetLost();
                
                model.addAttribute("list", list);


            } else if(type.equals("getlost") && menu.equals("lost")) { //분실물 전체 조회

                list2 = bvService.selectBoardViewLost();
                
                model.addAttribute("list", list);


            } else if(type.equals("getlost") && menu.equals("get")) { //습득물 전체 조회

                list2 = bvService.selectBoardViewGet();
                
                model.addAttribute("list", list);


            } else if(type.equals("community")) { //자유게시판

                list2 = bvService.selectBoardViewGeneral();
                
                model.addAttribute("list", list);


            }
            
    
            return "/aboard/selectlist";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.bubble";
        }


    }

    /* ------------------------------------------------------------- */

    //1개 조회
    @GetMapping(value="/selectone.bubble")
    public String selectOne(Model model, @AuthenticationPrincipal User user,
                            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu,
                            @RequestParam(name = "no") long no) {
        try {

            Board board = bService.selectOneBoard(no);

            List<BoardType> boardType = bService.selectlistBType();

            List<Reply> list = rService.selectlistReply(no); //해당 게시글의 댓글 전체 조회
        
            // log.info("글 1개 조회 => {}", board.toString());


            long next = bService.nextBoardOne(no);
            long pre = bService.preBoardOne(no);

            log.info("이전페이지 번호 => {}", pre);
            log.info("다음페이지 번호 => {}", next);



            model.addAttribute("board", board); //게시글 1개 조회 view로 넘기기
            model.addAttribute("boardType", boardType); //카테고리
            model.addAttribute("next", next);   //다음 페이지
            model.addAttribute("pre", pre); // 이전 페이지
            model.addAttribute("user", user); //로그인 관련
            model.addAttribute("list", list); //해당게시글의 댓글 전체 조회


            return "/aboard/selectone";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/aboard/selectlist.bubble?menu"+menu;
        }
    }
    

    /* ------------------------------------------------------------- */

    //수정
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

            return "/aboard/update";

            

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/aboard/selectone.bubble?no="+no;
        }
    }

    @PostMapping(value="/update.bubble")
    public String updatePOST(@ModelAttribute Board board, @RequestParam(name = "no") long no ) {
        try {

            Board obj = bService.selectOneBoard(no);

            log.info("수정 완료 => {}",board.toString());

            int ret = bService.updateBoard(board);

            if(ret == 1){
                return "redirect:/aboard/selectone.bubble?no="+obj.getNo();
            }
        
            return "redirect:/aboard/update.bubble?no="+obj.getNo();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/aboard/selectone.bubble?no="+no;
        }
    }
    


    


    /* ------------------------------------------------------------- */

    //삭제
    @PostMapping(value="/delete.bubble")
    public String deletePOST( @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "no") long no,
                                @AuthenticationPrincipal User user, Model model) {
        try {
            
            log.info("삭제할 게시글 번호 => {}", no);

            if(no == 0) { //no값이 0일경우 목록으로 이동
                return "redirect:/aboard/selectlist.bubble?menu="+menu;
            }

            //삭제
            int ret = bService.deleteBoard(no);

            log.info("삭제되면 1 실패면 -1 => {}", ret);

            if(ret == 1) { //성공시

                rService.deleteReply(no); //게시글 삭제시 게시글에 있는 댓글도 같이 삭제

                model.addAttribute("msg", "삭제되었습니다");
                model.addAttribute("url","/bubble_bumul/aboard/selectlist.bubble?menu=" + menu);

                return "message";


                // return "/aboard/selectlist.bubble?menu=" + menu;
            }

            model.addAttribute("msg", "삭제 실패");
            model.addAttribute("url","/bubble_bumul/aboard/selectlist.bubble?menu=" + menu);

            return "message";

            // return "/aboard/selectlist.bubble?menu=" + menu;

        

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/aboard/selectone.bubble?no="+no;
        }
    }
    


    /* ------------------------------------------------------------- */


    
    
    
}
