package com.example.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.SendMail;
import com.example.service.MailService;

@RestController
@RequestMapping("/mail")
public class RestMailController {
    @Autowired
    private MailService mailService;

    @GetMapping("/send")
    public void sendTestMail(String email) {
        SendMail mail = new SendMail();

        mail.setAddress(email);
        mail.setTitle("버블버물");
        mail.setMessage("버블버물입니다.");

        mailService.sendMail(mail);

        // return mail;
    }
}
