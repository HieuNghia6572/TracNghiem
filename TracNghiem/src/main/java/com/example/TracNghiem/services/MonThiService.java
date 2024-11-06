package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.entity.PhongThi;
import com.example.TracNghiem.repository.IMonThiRepository;
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
public class MonThiService {
    private final IMonThiRepository monThiRepository;
    // Retrieve all monthi from the database
    public List<MonThi> getAllMonThi() {
        return monThiRepository.findAll();
    }
    // Retrieve a monthi by its id
    public Optional<MonThi> getMonThiById(Long id) {
        return monThiRepository.findById(id);
    }
    // Add a new monthi to the database
    public MonThi addMonThi(MonThi monThi) {
        monThi.setMamonthi(monThi.getMamonthi());
        return monThiRepository.save(monThi);

    }
    public void deleteMonThi(Long id) {
        if (!monThiRepository.existsById (id)) {
            throw new IllegalStateException("Mon Thi voi id " + id + " khong hop le");
        }
        monThiRepository .deleteById(id);
    }
    public  MonThi updateMonThi(@NotNull MonThi monThi){
        MonThi existingMonThi = monThiRepository.findById(monThi.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        monThi.getId() + " does not exist."));
        existingMonThi.setTenmonthi(monThi.getTenmonthi());
        existingMonThi.setImgUrl(monThi.getImgUrl());
        existingMonThi.setMamonthi(monThi.getMamonthi());
        return monThiRepository.save(existingMonThi);
    }}
