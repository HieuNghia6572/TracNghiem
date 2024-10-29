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


    @OneToMany(mappedBy = "dethi", cascade = CascadeType.ALL)
    private List<ChiTietDeThi> chitietbaithi;


}




