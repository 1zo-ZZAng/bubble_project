package com.example.controller.mybatis;

import java.math.BigInteger;
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

import com.example.dto.BoardAdmin;
import com.example.dto.BoardGetLost;
import com.example.dto.Board;
import com.example.dto.BoardType;
import com.example.dto.BoardView;
import com.example.dto.BoardWashing;
import com.example.dto.Reply;
import com.example.dto.Washing;
import com.example.service.mybatis.BoardMybatisService;
import com.example.service.mybatis.BoardViewMybatisService;
import com.example.service.mybatis.BoardWashingMybatisService;
import com.example.service.mybatis.ReplyMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Controller
@RequestMapping(value = "/wboard")
@RequiredArgsConstructor
@Slf4j
public class WBoardController {

    final BoardMybatisService bService; //게시판

    final ReplyMybatisService rService; // 댓글

    final BoardViewMybatisService bvService; //게시판 + 카테고리 view

    final BoardWashingMybatisService bwService; // boardview + washing view

    
/* =========================================================================================================== */

    //글작성
    @GetMapping(value = "/write.bubble")
    public String writeGET(@AuthenticationPrincipal User user, Model model, 
                            @RequestParam(name = "type", required = false, defaultValue = "notice") String type,
                            @RequestParam(name = "menu", required = false, defaultValue = "admin") String menu,
                            @ModelAttribute Washing washing){
        try {
            List<String> list1 = bService.selectCodeNameDistinct(); // 게시판 종류

            List<String> list2 = new ArrayList<>();
            if (type.equals("notice")) {
                list2 = bService.selectCodeDetail("공지사항");
            }
            else if (type.equals("getlost")) {
                list2 = bService.selectCodeDetail("분실물/습득물");
            }
            else if (type.equals("community")) {
                list2 = bService.selectCodeDetail("자유게시판");
            }

            // log.info("게시판 종류=>{}",list1.toString());
            // log.info("말머리 종류=>{}",list2.toString());

            model.addAttribute("CodeNameList", list1);
            model.addAttribute("CodeDetailList", list2);

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

    //전체 조회
    @GetMapping(value="/selectlist.bubble")
    public String selectlistGET(Model model, @AuthenticationPrincipal User user, 
                                @RequestParam(name = "type", required = false, defaultValue = "notice") String type,
                                @RequestParam(name = "menu", required = false, defaultValue = "admin") String menu,
                                @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        try {

            model.addAttribute("user", user);

            // 총 게시글 개수
            int listcount = 0;

            List<BoardAdmin> alist = new ArrayList<>();
            List<BoardWashing> wlist = new ArrayList<>();
            List<BoardGetLost> gllist = new ArrayList<>();

            if (page == 0) {
                return "redirect:/wboard/selectlist.bubble?type=notice&menu=admin&page=1";
            }

            if(type.equals("notice")){ // 공지사항 조회
                if (menu.equals("admin")) { // 관리자 공지사항
                    alist = bwService.selectBoardAdminNotice(10*page-9, 10*page);
                    listcount = bwService.selectBoardAdminNoticeCount();

                    model.addAttribute("list", alist);
                }
                else { // 세탁업체 공지사항
                    wlist = bwService.selectBoardWashingNotice(10*page-9, 10*page);
                    listcount = bwService.selectBoardWashingNoticeCount();

                    model.addAttribute("list", wlist);
                }
            } 
            else if (type.equals("getlost")) { // 분실물/습득물
                if (menu.equals("get")) { // 분실물
                    gllist = bwService.selectBoardGetLost(4, 10*page-9, 10*page);

                    listcount = bwService.selectBoardGetLostCount(4);

                    model.addAttribute("list", gllist);
                }
                else { // 습득물
                    gllist = bwService.selectBoardGetLost(5, 10*page-9, 10*page);
                    listcount = bwService.selectBoardGetLostCount(5);

                    model.addAttribute("list", gllist);
                }
            } 
            else if (type.equals("community")) { // 자유게시판

                
            }
            else { //type값 없을 때 type=notice, menu=admin으로 자동이동
                return "redirect:/wboard/selectlist.bubble?type=notice&menu=admin&page=1";
            }

            // 페이지네이션 정보 계산
            int perPage = 10; // 페이지당 표시할 항목 수
            int totalPageCount = (int) Math.ceil((double) listcount / perPage); // 총 페이지 개수  

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPageCount", totalPageCount);
            
            // 페이지네이션 인덱스
            int startPageIndex = ((page-1)/10)*10 + 1; // 페이지네이션 그룹 시작
            int endPageIndex = (((page-1)/10)+1)*10; // 페이지네이션 그룹 끝
            model.addAttribute("startPageIndex", startPageIndex); 
            if (totalPageCount < endPageIndex) {
                model.addAttribute("endPageIndex", totalPageCount);
            }
            else {
                model.addAttribute("endPageIndex", endPageIndex); 
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
    public String selectOne(Model model, @AuthenticationPrincipal User user,
                            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu,
                            @RequestParam(name = "no") long no) {
        try {

            Board board = bService.selectOneBoard(no);

            List<BoardType> boardType = bService.selectlistBType();

            List<Reply> list = rService.selectlistReply(no); //해당 게시글의 댓글 전체 조회
        
            // log.info("글 1개 조회 => {}", board.toString());


            long next = bService.nextBoardOne(no, board.getCode());
            long pre = bService.preBoardOne(no, board.getCode());

            log.info("이전페이지 번호 => {}", pre);
            log.info("다음페이지 번호 => {}", next);



            model.addAttribute("board", board); //게시글 1개 조회 view로 넘기기
            model.addAttribute("boardType", boardType); //카테고리
            model.addAttribute("next", next);   //다음 페이지
            model.addAttribute("pre", pre); // 이전 페이지
            model.addAttribute("user", user); //로그인 관련
            model.addAttribute("list", list); //해당게시글의 댓글 전체 조회


            return "/wboard/selectone";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/wboard/selectlist.bubble?menu"+menu;
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

    //삭제
    @PostMapping(value="/delete.bubble")
    public String deletePOST( @RequestParam(name = "menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "no") long no,
                                @AuthenticationPrincipal User user, Model model) {
        try {
            
            log.info("삭제할 게시글 번호 => {}", no);

            if(no == 0) { //no값이 0일경우 목록으로 이동
                return "redirect:/wboard/selectlist.bubble?menu="+menu;
            }

            //삭제
            int ret = bService.deleteBoard(no);

            log.info("삭제되면 1 실패면 -1 => {}", ret);

            if(ret == 1) { //성공시

                rService.deleteReply(no); //게시글 삭제시 게시글에 있는 댓글도 같이 삭제

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
