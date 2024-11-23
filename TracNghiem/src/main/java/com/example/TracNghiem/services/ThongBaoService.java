package com.example.TracNghiem.services;


import com.example.TracNghiem.entity.PhongThi;
import com.example.TracNghiem.entity.ThongBao;
import com.example.TracNghiem.repository.IThongBaoRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ThongBaoService {

    @Autowired
    private IThongBaoRepository thongBaoRepository;
    // Retrieve all phongthi from the database
    public List<ThongBao> getAllThongBao() {return thongBaoRepository.findAll();
    }
    // Retrieve a phongthi by its id
    public Optional<ThongBao> getThongBaoById(Long id) {return thongBaoRepository.findById(id);
    }
    // Add a new phongthi to the database
    public ThongBao addThongBao(ThongBao thongBao) {
        thongBao.setMessage(thongBao.getMessage());
        return thongBaoRepository.save(thongBao);

    }
    public boolean hasThongBao() {
        return thongBaoRepository.count() > 0;
    }
    public ThongBao updateThongBao(@NotNull ThongBao thongBao){
        ThongBao existingThongBao = thongBaoRepository.findById(thongBao.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        thongBao.getId() + " does not exist."));
        existingThongBao.setMessage(thongBao.getMessage());

        return thongBaoRepository.save(existingThongBao);
    }
    public void deleteThongBao(Long id) {
        if (!thongBaoRepository.existsById (id)) {
            throw new IllegalStateException("Thong Bao voi id " + id + " khong hop le");
        }
        thongBaoRepository .deleteById(id);
    }


}

