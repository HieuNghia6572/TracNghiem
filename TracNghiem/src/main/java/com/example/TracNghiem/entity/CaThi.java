package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
   /* @NotEmpty(message = "Tên không được để trống")*/
    private String tencathi;

    /*@Column(name = "tenmonhoc")
    @NotEmpty(message = "Đáp án không được để trống")
    private String tenmonhoc;*/
    // thời gian bắt đầu thi
    @Column(name = "thoigianbatdauthi")
    @NotNull(message = "Đáp án không được để trống")
    private LocalDateTime tgbd;
    //thời gian kết thúc thi
    @Column(name = "thoigianketthucthi")
    @NotNull(message = "Đáp án không được để trống")
    private LocalDateTime tgkt;

    private int thoiLuong; // Thời lượng thi tính bằng phút

    private boolean daBatDau; // Trạng thái bài thi

    @ManyToOne
    @JoinColumn(name= "monthi_id")
    private MonThi monthi;
    @OneToMany(mappedBy = "cathi", cascade = CascadeType.ALL)
    private List<PhongThi> Phongthi;

    // Tính thời lượng thi
    @PrePersist
    @PreUpdate
    private void calculateThoiLuong() {
        this.thoiLuong = (int) java.time.Duration.between(tgbd, tgkt).toMinutes();
    }
}
