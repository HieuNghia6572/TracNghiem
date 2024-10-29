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
@Table (name="chitietdethi")
public class ChiTietDeThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 //
    @ManyToOne
    @JoinColumn(name= "dethi_id")
    private DeThi dethi;

    @ManyToOne
    @JoinColumn(name= "cauhoi_id")
    private CauHoi cauhoi;
}
