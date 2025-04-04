package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.repository.ICauHoiRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CauHoiService {

    private final ICauHoiRepository cauHoiRepository;
    //private final ICauHoiRepository cauHoiRepository;
    // Retrieve all cauhoi from the database




    public List<CauHoi> getAllCauHoi() {
        return cauHoiRepository.findAll();
    }
    // Retrieve a cauhoi by its id

    public Optional<CauHoi> getCauHoiById(Long id) {
        return cauHoiRepository.findById(id);
    }
    public CauHoi getCauHoiById1(Long id) {
        return cauHoiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
//    public List<CauHoi> getRandomCauHoiByCapDo(Long capdoId, int limit) {
//        return cauHoiRepository.findRandomCauHoiByCapDo(capdoId, limit);
//    }
    // Add a new cauhoi to the database

    public CauHoi addCauHoi(CauHoi cauHoi) {
        return cauHoiRepository.save(cauHoi);
    }


    // Delete a cauhoi by its id
    public void deleteCauHoi(Long id) {
        if (!cauHoiRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        cauHoiRepository.deleteById(id);
    }
    public CauHoi updateCauHoi(@NotNull CauHoi cauHoi, String dapandung) {
        // Tìm thực thể CauHoi hiện tại dựa trên ID
        CauHoi existingCauhoi = cauHoiRepository.findById(cauHoi.getId())
                .orElseThrow(() -> new IllegalStateException("Câu hỏi với ID " +
                        cauHoi.getId() + " không tồn tại."));

        // Cập nhật thông tin
        existingCauhoi.setTen(cauHoi.getTen());
        existingCauhoi.setImgUrl(cauHoi.getImgUrl());
        existingCauhoi.setDapanA(cauHoi.getDapanA());
        existingCauhoi.setDapanB(cauHoi.getDapanB());
        existingCauhoi.setDapanC(cauHoi.getDapanC());
        existingCauhoi.setDapanD(cauHoi.getDapanD());
        existingCauhoi.setDapandung(dapandung); // Gán đáp án đúng

        // Lưu câu hỏi đã cập nhật
        return cauHoiRepository.save(existingCauhoi);
    }

    public Optional<CauHoi> findById(Long id) {
        return cauHoiRepository.findById(id);
    }
}
