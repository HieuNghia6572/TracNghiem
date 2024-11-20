package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.entity.UserDeThi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserDeThiRepository extends JpaRepository<UserDeThi, Long> {
    Optional<UserDeThi> findByUserAndDeThi(User user, DeThi deThi);
}