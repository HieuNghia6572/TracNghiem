package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CaThi;
import com.example.TracNghiem.repository.ICaThiRepository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Transactional
@Service
public class CaThiService {
    private final ICaThiRepository caThiRepository;
    // Retrieve all cauhoi from the database
    public List<CaThi> getAllCaThi() {
        return caThiRepository.findAll();
    }
    // Retrieve a cauhoi by its id
    public Optional<CaThi> getCaThiById(Long id) {
        return caThiRepository.findById(id);
    }
    // Add a new cauhoi to the database
    public CaThi addCaThi(CaThi caThi) {
        return caThiRepository.save(caThi);
    }

    // Delete a cauhoi by its id
    public void deleteCaThi(Long id) {
        if (!caThiRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        caThiRepository.deleteById(id);
    }
    public CaThi updateCaThi(@NotNull CaThi caThi) {
        CaThi existingCathi = caThiRepository.findById(caThi.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        caThi.getId() + " does not exist."));
        existingCathi.setTencathi(caThi.getTencathi());
        existingCathi.setTenmonhoc(caThi.getTenmonhoc());
        existingCathi.setTgbd(caThi.getTgbd());
        existingCathi.setTgkt(caThi.getTgkt());

        return caThiRepository.save(existingCathi);
    }

}
