package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.MonThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMonThiRepository  extends JpaRepository<MonThi, Long>{

}
