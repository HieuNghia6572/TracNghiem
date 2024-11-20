package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString
@Table(name = "dethi")
public class DeThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "madethi")
    private String madethi;

    @Column(name = "tendethi")
    private String tendethi;

    @Column(name = "slcauhoide")
    private String slcauhoide;

    @Column(name = "slcauhoitb")
    private String slcauhoitb;

    @Column(name = "slcauhoikho")
    private String slcauhoikho;

    @OneToMany(mappedBy = "deThi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDeThi> chitietbaithi;

    @Column(name = "monthi_id")
    private Long monthiId;//khong can thiet

    @ManyToOne
    @JoinColumn(name = "monthi_id", insertable = false, updatable = false)
    private MonThi monthi;

    @OneToMany(mappedBy = "dethi", cascade = CascadeType.ALL)
    private List<PhongThi> phongThi;
}