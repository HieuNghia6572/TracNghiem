package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString
@Table(name="dethi")

public class DeThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "madethi")
    private String madethi;

    @Column(name = "slcauhoide")
    private String slcauhoide;

    @Column(name = "slcauhoitb")
    private String slcauhoitb;

    @Column(name = "slcauhoikho")
    private String slcauhoikho;

    @OneToMany(mappedBy = "dethi", cascade = CascadeType.ALL)
    private List<ChiTietDeThi> chitietbaithi;

    @Column(name = "monthi_id")
    private Long monthiId; // ID của môn thi

    @ManyToOne
    @JoinColumn(name = "monthi_id", insertable = false, updatable = false)
    private MonThi monthi; // Tham chiếu đến môn thi


}




