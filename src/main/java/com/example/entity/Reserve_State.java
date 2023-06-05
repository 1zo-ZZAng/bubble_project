package com.example.entity;

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

@Entity
@Data
@Table(name = "RESERVE_STATE")
public class Reserve_State {
    @Id
    @Column(name = "STATE")
    private String state;

    @ToString.Exclude
    @OneToMany(mappedBy = "reservestate", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Reservation> reservation = new ArrayList<>();
}
