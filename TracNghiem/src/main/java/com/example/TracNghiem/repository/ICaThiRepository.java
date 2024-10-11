package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.CaThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICaThiRepository extends JpaRepository<CaThi, Long> {
}
