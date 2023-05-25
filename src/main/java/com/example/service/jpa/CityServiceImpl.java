package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.City;
import com.example.mapper.CityMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    final CityMapper cMapper;

    // 시/도 distinct 조회
    @Override
    public List<City> selectCitynameList() {
        try {
            return cMapper.selectCitynameList();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 시/도 & 구/군 1개 조회
    @Override
    public City selectTownOne(String cityname, String townname) {
        try {
            return cMapper.selectCityOne(cityname, townname);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
