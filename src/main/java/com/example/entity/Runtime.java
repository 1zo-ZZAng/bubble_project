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
@Table(name = "RUNTIME")
@SequenceGenerator(name = "SEQ_RUNTIME_NO", sequenceName = "SEQ_RUNTIME_NO", initialValue = 1, allocationSize = 1)
public class Runtime {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RUNTIME_NO")
    private BigInteger no; 

    private String starttime;

    private String type;
}
