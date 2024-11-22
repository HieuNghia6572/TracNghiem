package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.entity.UserDeThi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserDeThiRepository extends JpaRepository<UserDeThi, Long> {
    Optional<UserDeThi> findByUserAndDeThi(User user, DeThi deThi);
    Optional<UserDeThi> findByUserUsernameAndDeThiId(String username, Long deThiId);
    List<UserDeThi> findByUserUsername(String username);
    List<UserDeThi> findByUserId(Long userId); // Đảm bảo phương thức này đã được định nghĩa
    UserDeThi findByUserIdAndDeThiId(Long userId, Long deThiId);

}