package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString
@Table(name="monthi")
public class MonThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name= "tenmonthi")
    private String  tenmonthi;

    @Column(name= "mamonthi")
    private  String  mamonthi;

    @OneToMany(mappedBy = "monthi", cascade = CascadeType.ALL)
    private List<CaThi> Cathi;
}
