package com.example.service.mybatis;

import org.springframework.stereotype.Service;

import com.example.dto.Washing;
import com.example.mapper.WashingMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WashingMybatisServiceImpl implements WashingMybatisService {
    
    final WashingMapper wMapper;

    //업체 탈퇴
    @Override
    public int deleteWashingOne(Washing obj) {
        try {

            int ret = wMapper.deleteWashingOne(obj);

            if(ret == 1){
                return 1;
            }else{
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
