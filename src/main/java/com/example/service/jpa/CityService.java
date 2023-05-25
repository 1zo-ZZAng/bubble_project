package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.City;

@Service
public interface CityService {
    // 시/도 distinct 조회
    public List<City> selectCitynameList();

    // 시/도 & 구/군 1개 조회
    public City selectTownOne(String cityname, String townname);
}
