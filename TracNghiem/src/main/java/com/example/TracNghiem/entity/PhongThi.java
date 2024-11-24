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

    @ManyToOne
    @JoinColumn(name= "dethi_id")
    private DeThi dethi;
    @ManyToMany
    @JoinTable(
            name = "user_phongthi",
            joinColumns = @JoinColumn(name = "phongthi_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> user; // Danh sách người dùng tham gia phòng thi



}

