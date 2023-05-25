package com.example.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CITY")
@SequenceGenerator(name = "SEQ_CITY_CODE", sequenceName = "SEQ_CITY_CODE", initialValue = 1, allocationSize = 1)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CITY_CODE")
    private BigInteger code;

    private String cityname;

    private String townname;
}
