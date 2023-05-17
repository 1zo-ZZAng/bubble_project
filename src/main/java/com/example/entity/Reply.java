package com.example.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "REPLY")
@SequenceGenerator(name = "SEQ_REPLY_NO", sequenceName = "SEQ_REPLY_NO", initialValue = 1, allocationSize = 1)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPLY_NO")
    private BigInteger no; // 댓글 번호

    @Lob
    private String content; // 댓글 내용

    private String writer; // 작성자

    // 작성일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", insertable = true, updatable = true)
    private Date regdate;

    // 외래키 (board : reply = 1 : N)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno", referencedColumnName = "no")
    private Board board;
}
