package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "cauhoi")
public class CauHoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten")
    @NotEmpty(message = "Tên không được để trống")
    private String ten;

    @Column(name = "dapanA")
    @NotEmpty(message = "Đáp án không được để trống")
    private String dapanA;

    @Column(name = "dapanB")
    @NotEmpty(message = "Đáp án không được để trống")
    private String dapanB;

    @Column(name = "dapanC")
    @NotEmpty(message = "Đáp án không được để trống")
    private String dapanC;

    @Column(name = "dapanD")
    @NotEmpty(message = "Đáp án không được để trống")
    private String dapanD;

    @Enumerated(EnumType.STRING)
    private CorrectOption correctOption;

    @Column(name = "dapandung")
    @NotEmpty(message = "Đáp án không được để trống")
    private String dapandung;

    @Column(name = "capdo")
    private String capDo;
}
enum CorrectOption {
    A, B, C, D
}