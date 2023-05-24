package com.example.service.mybatis;

import org.springframework.stereotype.Service;

import com.example.dto.Washing;

@Service
public interface WashingMybatisService {

    //업체탈퇴
    public int deleteWashingOne(Washing obj);



    
    
}
