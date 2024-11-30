package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "chitietdethi")
public class ChiTietDeThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dapanchon")
    private String dapanchon;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cauhoi_id", nullable = false)
    private CauHoi cauHoi;

    @ManyToOne
    @JoinColumn(name = "dethi_id", nullable = false)
    private DeThi deThi;
    /*@ManyToOne
    @JoinColumn(name = "cathi_id", nullable = false)
    private CaThi caThi;*/
    @ManyToOne
    @JoinColumn(name = "cathi_id")
    private CaThi caThi;

}