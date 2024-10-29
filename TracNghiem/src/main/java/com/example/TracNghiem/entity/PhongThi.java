package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString
@Table(name="phongthi")
public class PhongThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name= "tenPhong")
    private String  tenPhong;

    @Column(name= "maPhong")
    private  String  maPhong;

    @ManyToOne
    @JoinColumn(name= "cathi_id")
    private CaThi cathi;

/*    @OneToMany(mappedBy = "phongthi", cascade = CascadeType.ALL)
    private List<CaThi> Cathi;*/





}

