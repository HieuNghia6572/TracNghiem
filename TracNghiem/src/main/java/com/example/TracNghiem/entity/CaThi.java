package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "cathi")
public class CaThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tencathi")
    @NotEmpty(message = "Tên không được để trống")
    private String tencathi;

    @Column(name = "tenmonhoc")
    @NotEmpty(message = "Đáp án không được để trống")
    private String tenmonhoc;

    @Column(name = "thoigianbatdauthi")
    @NotEmpty(message = "Đáp án không được để trống")
    private String tgbd;

    @Column(name = "thoigianketthucthi")
    @NotEmpty(message = "Đáp án không được để trống")
    private String tgkt;


}
