package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
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

    @Column(name = "monthi_id")
    private Long monthiId;

    @Column(name = "capdo_id")
    private Long capdoId;

    @ManyToOne
    @JoinColumn(name = "monthi_id", insertable = false, updatable = false)
    private MonThi monthi;

    @ManyToOne
    @JoinColumn(name = "capdo_id", insertable = false, updatable = false)
    private CapDo capdo;

    @OneToMany(mappedBy = "cauHoi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDeThi> chitietbaithi = new ArrayList<>(); // Khởi tạo danh sách
    // Phương thức để thêm ChiTietDeThi
    public void addChiTietDeThi(ChiTietDeThi chiTiet) {
        chitietbaithi.add(chiTiet);
        chiTiet.setCauHoi(this); // Thiết lập tham chiếu ngược
    }
}//còn loi chinh sua