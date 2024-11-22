package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IChiTietDeThiRepository extends JpaRepository<ChiTietDeThi, Long> {
    List<ChiTietDeThi> findByDeThiMadethi(String madethi);
    List<ChiTietDeThi> findByDeThiId(Long deThiId);
    List<ChiTietDeThi> findByUserAndCauHoi(User user, CauHoi cauHoi);
    Optional<ChiTietDeThi> findByUserIdAndId(Long userId, Long id);
    List<ChiTietDeThi> findByDeThiIdAndUserId(Long deThiId, Long userId);
    Optional<ChiTietDeThi> findByCauHoiIdAndUserId(Long cauHoiId, Long userId);

}
