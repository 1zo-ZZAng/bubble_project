package com.example.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.mybatis.BoardMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/wboard")
@RequiredArgsConstructor
@Slf4j
public class RestWBoardController {

    final BoardMybatisService bService;

    
}
