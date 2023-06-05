package com.example.service.mybatis;

import java.util.List;

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

    // 예약 페이지에서 지역에 맞는 세탁소 리스트 조회
    @Override
    public List<Washing> selectWashingList(String cityname, String townname) {
        try {
            return wMapper.selectWashingList(cityname, townname);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //1명조회 (업체명으로 조회한 거)
    @Override
    public Washing selectWashingnameOne(String name) {
        try {

            return wMapper.selectWashingnameOne(name);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    
}
