package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.MonThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMonThiRepository  extends JpaRepository<MonThi, Long>{
    List<MonThi> findByTenmonthiContainingIgnoreCase(String tenmonthi);
}
