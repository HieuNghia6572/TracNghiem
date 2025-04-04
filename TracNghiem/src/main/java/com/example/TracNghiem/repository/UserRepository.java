package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}