package com.example.dto;

import java.math.BigInteger;


import lombok.Data;


@Data
public class AdminChkList {
    private String id;
    private String name;
    private String wnumber;
    private String address;
    private String phone;
    private String ceo;
    private BigInteger chkno;
    private String chk; 
}
