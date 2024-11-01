package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

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
    private String ImgUrl;
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

    @Column(name = "dapandung")
    @NotEmpty(message = "Đáp án không được để trống")
    private String dapandung;

    @Column(name = "capdo")
    private String capDo;

    @Column(name = "monthi_id")
    private Long monthiId; // ID của môn thi

    @ManyToOne
    @JoinColumn(name = "monthi_id", insertable = false, updatable = false)
    private MonThi monthi; // Tham chiếu đến môn thi

    @OneToMany(mappedBy = "cauhoi", cascade = CascadeType.ALL)
    private List<ChiTietDeThi> chitietbaithi;

   @ManyToOne
   @JoinColumn(name="capdo_id",insertable = false,updatable = false)
    private  CapDo capdo;
}
