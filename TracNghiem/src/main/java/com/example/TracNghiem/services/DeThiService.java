package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.repository.ICauHoiRepository;
import com.example.TracNghiem.repository.IChiTietDeThiRepository;
import com.example.TracNghiem.repository.IDeThiRepository;
import com.example.TracNghiem.repository.IMonThiRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeThiService {
    private final IDeThiRepository deThiRepository;
    private final IChiTietDeThiRepository chiTietDeThiRepository;


    public List<DeThi> getAllDeThi() {
        return deThiRepository.findAll();
    }

    public Optional<DeThi> getDeThiById(Long id) {
        return deThiRepository.findById(id);
    }

    public DeThi addDeThi(DeThi deThi) {
        deThi.setMadethi(deThi.getMadethi());
        return deThiRepository.save(deThi);
    }

    public void deleteDeThi(Long id) {
        if (!deThiRepository.existsById(id)) {
            throw new IllegalStateException("De Thi voi id " + id + " khong hop le");
        }
        deThiRepository.deleteById(id);
    }

    public DeThi updateDeThi(@NotNull DeThi deThi) {
        DeThi existingDeThi = deThiRepository.findById(deThi.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        deThi.getId() + " does not exist."));
        existingDeThi.setMadethi(deThi.getMadethi());


        return deThiRepository.save(existingDeThi);
    }

    public List<ChiTietDeThi> getBaiThi(String made){
        return chiTietDeThiRepository.findAll().stream().filter(p->p.getDethi().getMadethi().equals(made)).toList();
    }



}
