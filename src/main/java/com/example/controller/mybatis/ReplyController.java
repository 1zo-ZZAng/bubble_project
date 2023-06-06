package com.example.controller.mybatis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Reply;
import com.example.service.mybatis.ReplyMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "/reply")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {

    final ReplyMybatisService rMybatisService;

    //댓글 작성
    @PostMapping(value="/insert.bubble")
    public String insertPOST(@ModelAttribute Reply reply) {
        try {

            log.info("댓글 작성 => {}",reply.toString());

            rMybatisService.writeReply(reply);

            return "redirect:/wboard/selectone.bubble?no=" + reply.getBno();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "rediret:/wboard/selectone.bubble?no=" + reply.getBno();
        }
    }
    
    
}
