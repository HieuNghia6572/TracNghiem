package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.repository.IChiTietDeThiRepository;
import com.example.TracNghiem.repository.IDeThiRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class ChiTietDeThiService {

    private final IChiTietDeThiRepository chiTietDeThiRepository;

    public List<ChiTietDeThi> getAllChiTietDeThi() { return chiTietDeThiRepository.findAll();
    }



    public Optional<ChiTietDeThi> getChiTietDeThiById(Long id) {
         return chiTietDeThiRepository.findById(id);
    }

    public ChiTietDeThi addChiTietDeThi(ChiTietDeThi chiTietDeThi) {
//        chiTietDeThi.setDethi(chiTietDeThi.getDethi());
//        chiTietDeThi.setCauhoi(chiTietDeThi.getCauhoi());
        return chiTietDeThiRepository.save(chiTietDeThi);
    }

    public void deleteChiTietDeThi(Long id) {
        if (!chiTietDeThiRepository.existsById(id)) {
            throw new IllegalStateException("De Thi voi id " + id + " khong hop le");
        }
        chiTietDeThiRepository.deleteById(id);
    }

    public ChiTietDeThi updateChiTietDeThi(@NotNull ChiTietDeThi chiTietDeThi) {
        ChiTietDeThi existingChiTietDeThi = chiTietDeThiRepository.findById(chiTietDeThi.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        chiTietDeThi.getId() + " does not exist."));
        existingChiTietDeThi.setDethi(chiTietDeThi.getDethi());
        existingChiTietDeThi.setCauhoi(chiTietDeThi.getCauhoi());


        return chiTietDeThiRepository.save(existingChiTietDeThi);
    }
}
