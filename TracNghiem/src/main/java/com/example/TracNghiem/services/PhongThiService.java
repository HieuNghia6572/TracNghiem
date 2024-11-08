package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CaThi;
import com.example.TracNghiem.entity.PhongThi;
import com.example.TracNghiem.repository.ICaThiRepository;
import com.example.TracNghiem.repository.IPhongThiRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PhongThiService {
    private final IPhongThiRepository phongThiRepository;
    private final ICaThiRepository caThiRepository;

    // Retrieve all phongthi from the database
    public List<PhongThi> getAllPhongThi() {
        return phongThiRepository.findAll();
    }
    // Retrieve a phongthi by its id
    public Optional<PhongThi> getPhongThiById(Long id) {
        return phongThiRepository.findById(id);
    }
    // Add a new phongthi to the database
    public PhongThi addPhongThi(PhongThi phongThi) {
        phongThi.setMaPhong(phongThi.getMaPhong());
        return phongThiRepository.save(phongThi);
    }
    public List<CaThi> getAllCaThi() {
        return caThiRepository.findAll();
    }

    public void deletePhongThi(Long id) {
        if (!phongThiRepository.existsById (id)) {
            throw new IllegalStateException("Phong thi voi id " + id + " khong hop le");
        }
        phongThiRepository .deleteById(id);
    }
    public  PhongThi updatePhongThi(@NotNull PhongThi phongThi){
        PhongThi existingPhongThi = phongThiRepository.findById(phongThi.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        phongThi.getId() + " does not exist."));
        existingPhongThi.setTenPhong(phongThi.getTenPhong());
        existingPhongThi.setMaPhong(phongThi.getMaPhong());
        existingPhongThi.setCathi(phongThi.getCathi());
        return phongThiRepository.save(existingPhongThi);
    }
}


