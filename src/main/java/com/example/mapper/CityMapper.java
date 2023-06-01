package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CityMapper {
    // 시/도 distinct 조회
    @Select({"SELECT DISTINCT(cityname) FROM city"})
    public List<String> selectCitynameList();

    // 시/도 & 구/군 1개 조회
    @Select({"SELECT townname FROM city WHERE cityname = #{cityname}"})
    public List<String> selectCityOne(@Param("cityname") String cityname);
}
