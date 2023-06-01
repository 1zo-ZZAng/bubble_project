package com.example.dto;

import java.math.BigInteger;
import java.util.List;

import com.example.entity.WashingCheck;

import lombok.Data;

@Data
public class Category {

    
    List<WashingCheck> list = null;

    private BigInteger no = BigInteger.valueOf(0);

    private String chk = null;

}
