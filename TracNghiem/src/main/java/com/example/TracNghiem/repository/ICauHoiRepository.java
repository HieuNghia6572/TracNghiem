package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.CauHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICauHoiRepository extends JpaRepository<CauHoi, Long> {

}
