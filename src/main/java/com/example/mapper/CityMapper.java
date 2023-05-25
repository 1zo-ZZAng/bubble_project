package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.City;

@Mapper
public interface CityMapper {
    // 시/도 distinct 조회
    @Select({"SELECT DISTINCT(cityname) FROM city"})
    public List<City> selectCitynameList();

    // 시/도 & 구/군 1개 조회
    @Select({"SELECT code, cityname, townname FROM city WHERE cityname = #{cityname} AND townname = #{townname}"})
    public City selectCityOne(@RequestParam("cityname") String cityname, @RequestParam("townname") String townname);
}
