package com.example.TracNghiem.repository;
import com.example.TracNghiem.entity.ThongBao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IThongBaoRepository extends JpaRepository<ThongBao, Long> {
}
