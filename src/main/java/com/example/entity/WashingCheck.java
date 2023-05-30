package com.example.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "WASHINGCHECK")
public class WashingCheck {

    @Id
    @Column(name = "no")
    private BigInteger no;

    private String chk;

    @ToString.Exclude
    @OneToMany(mappedBy = "washingcheck", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Washing> washing = new ArrayList<>();

}
