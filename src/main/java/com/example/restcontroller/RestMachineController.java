package com.example.restcontroller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.MachineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/machine")
@RequiredArgsConstructor
@Slf4j
public class RestMachineController {
    
    final MachineRepository mRepository;

    
}
