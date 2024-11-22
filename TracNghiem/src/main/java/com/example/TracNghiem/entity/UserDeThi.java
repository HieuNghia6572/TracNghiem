package com.example.TracNghiem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp; // Sửa từ java.security.Timestamp sang java.sql.Timestamp

@NoArgsConstructor // Thêm annotation này để tạo constructor không tham số
@Data
@Entity
@Table(name = "UserDeThi")
public class UserDeThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "de_thi_id")
    private DeThi deThi;

    private int diem; // Có thể giữ lại nếu bạn muốn
    private int score; // Nếu không cần, có thể xóa

    private Timestamp thoiGianLam;
    // Constructor với tham số
    public UserDeThi(User user, DeThi deThi) {
        this.user = user;
        this.deThi = deThi;
    }
    public double calculateTotalPoints() {
        int soCau = deThi.getTotalQuestions(); // Lấy số câu từ phương thức mới
        return (soCau > 0) ? (10.0 / soCau) * diem : 0; // Tính tổng điểm
    }

}