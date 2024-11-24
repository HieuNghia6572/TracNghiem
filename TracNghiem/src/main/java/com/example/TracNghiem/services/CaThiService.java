package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CaThi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.repository.ICaThiRepository;

import com.example.TracNghiem.repository.IMonThiRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CaThiService {

    private final ICaThiRepository caThiRepository;





    public List<CaThi> getAllCaThi() {
        return caThiRepository.findAll();
    }


    public Optional<CaThi> getCaThiById(Long id) {
        return caThiRepository.findById(id);
    }


    public CaThi addCaThi(CaThi caThi) {
        return caThiRepository.save(caThi);
    }

    public CaThi luuCaThi(CaThi caThi) {
        return caThiRepository.save(caThi);
    }

    private final IMonThiRepository monThiRepository;
    public List<MonThi> getAllMoThi() {
        return monThiRepository.findAll();
    }

    /*private final IPhongThiRepository phongThiRepository;
    public List<PhongThi> getAllPhongThi() {
        return phongThiRepository.findAll();
    }*/


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
        /*existingCathi.setTenmonhoc(caThi.getTenmonhoc());*/
        existingCathi.setTgbd(caThi.getTgbd());
        existingCathi.setTgkt(caThi.getTgkt());

        return caThiRepository.save(existingCathi);
    }

}
