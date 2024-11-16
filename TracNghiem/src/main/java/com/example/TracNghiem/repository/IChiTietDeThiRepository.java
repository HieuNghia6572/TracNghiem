package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.ChiTietDeThi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChiTietDeThiRepository extends JpaRepository<ChiTietDeThi, Long> {
    List<ChiTietDeThi> findByDeThiMadethi(String madethi);
    
}
