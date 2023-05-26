package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.City;

@Service
public interface CityMybatisService {
    // 시/도 distinct 조회
    public List<String> selectCitynameList();

    // 시/도에 맞는 구/군 조회
    public List<String> selectCityTown(String cityname);
}
