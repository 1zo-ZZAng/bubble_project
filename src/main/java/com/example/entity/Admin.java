package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ADMIN")
public class Admin {

    @Id
    private String id; // 아이디

    private String password; // 비밀번호

    private String name; //관리자 이름 = 관리자

    private String role; //권한 default = "ADMIN"

    
}
