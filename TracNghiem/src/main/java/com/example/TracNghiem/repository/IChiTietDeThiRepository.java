package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IChiTietDeThiRepository extends JpaRepository<ChiTietDeThi, Long> {
    List<ChiTietDeThi> findByDeThiMadethi(String madethi);
    List<ChiTietDeThi> findByDeThiId(Long deThiId);
    List<ChiTietDeThi> findByUserAndCauHoi(User user, CauHoi cauHoi);
    Optional<ChiTietDeThi> findByUserIdAndId(Long userId, Long id);
    List<ChiTietDeThi> findByDeThiIdAndUserId(Long deThiId, Long userId);
    Optional<ChiTietDeThi> findByCauHoiIdAndUserId(Long cauHoiId, Long userId);
    Optional<ChiTietDeThi> findByCauHoiIdAndUserIdAndDeThiId(Long cauHoiId, Long userId, Long deThiId);
    Optional<ChiTietDeThi> findByCauHoiIdAndUserIdAndCaThiId(Long cauHoiId, Long userId, Long caThiId);
    @Query("SELECT c FROM ChiTietDeThi c WHERE c.id = :id AND c.user.id = :userId AND c.deThi.id = :deThiId")
    Optional<ChiTietDeThi> findByIdAndUserIdAndDeThiId(@Param("id") Long id,
                                                       @Param("userId") Long userId,
                                                       @Param("deThiId") Long deThiId);
}
