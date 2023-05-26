package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.City;
import com.example.mapper.CityMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityMybatisServiceImpl implements CityMybatisService {
    final CityMapper cMapper;

    // 시/도 distinct 조회
    @Override
    public List<String> selectCitynameList() {
        try {
            return cMapper.selectCitynameList();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 시/도에 맞는 구/군 조회
    @Override
    public List<String> selectCityTown(String cityname) {
        try {
            return cMapper.selectCityOne(cityname);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
