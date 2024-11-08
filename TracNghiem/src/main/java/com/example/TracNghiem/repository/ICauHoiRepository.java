package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.CauHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICauHoiRepository extends JpaRepository<CauHoi, Long> {
    // Repository


//    List<CauHoi> findByMonthiIdAndCapDo(Long monthiId, String capDo);
//    @Query(value = "SELECT * FROM cau_hoi WHERE cap_do_id = :capdoId ORDER BY RAND() LIMIT :limit", nativeQuery = true)
//    List<CauHoi> findRandomCauHoiByCapDo(@Param("capdoId") Long capdoId, @Param("limit") int limit);

    @Query(value = "SELECT * FROM cauhoi where capdo_id = :capdoId AND monthi_id =:monthiId ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<CauHoi> findRandomCauHoiByCapDoAndMonThi(@Param("capdoId") Long capdoId, @Param("monthiId") Long monthiId,  @Param("limit") int limit);


}

