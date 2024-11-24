package com.example.TracNghiem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Report {
    private String tenCaThi;
    private String tenMonThi;
    private LocalDateTime tgbd;
    private LocalDateTime tgkt;
    private long soLuongThiSinh; // Số lượng người dùng tham gia
}
