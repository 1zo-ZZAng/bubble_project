package com.example.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "BOARDTYPE")
@SequenceGenerator(name = "SEQ_BTYPE_CODE", sequenceName = "SEQ_BTYPE_CODE", initialValue = 1, allocationSize = 1)
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BTYPE_CODE")
    private BigInteger code; //  분류코드 (시퀀스) 

    private String codename; // 분류코드명 (공지사항, 분실물, 커뮤니티, 제휴등록)

    private String codedetail; // 말머리 (분실물 - 찾아주세요, 찾아가세요)

    // 외래키 (boardtype : board = 1 : N)
    @ToString.Exclude
    @OneToMany(mappedBy = "boardType", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Board> board = new ArrayList<>();
}
