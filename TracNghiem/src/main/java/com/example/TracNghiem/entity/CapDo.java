package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CapDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="tencapdo")
    private String tencapdo;

    @OneToMany(mappedBy = "capdo", cascade = CascadeType.ALL)
    private List<CauHoi> cauhoi;

}
