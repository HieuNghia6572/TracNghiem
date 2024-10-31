package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CapDo;
import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.repository.ICapDoRepository; // Giả sử bạn có repository cho CapDo
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CapDoService {

    private final ICapDoRepository capDoRepository;

    public List<CapDo> getAllCapDo() {
        return capDoRepository.findAll();
    }

    public Optional<CapDo> getCapDoById(Long id) {
        return capDoRepository.findById(id);
    }

    public CapDo addCapDo(CapDo capDo) {
        capDo.setTencapdo(capDo.getTencapdo());
        return capDoRepository.save(capDo);
    }

    public void deleteCapDo(Long id) {
        if (!capDoRepository.existsById(id)) {
            throw new IllegalStateException(" Cap do  voi id " + id + " khong hop le");
        }
        capDoRepository.deleteById(id);
    }

    public CapDo updateCapDo(@NotNull CapDo capDo) {
        CapDo existingCapDo = capDoRepository.findById(capDo.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        capDo.getId() + " does not exist."));
        existingCapDo.setTencapdo(capDo.getTencapdo());


        return capDoRepository.save(existingCapDo);
    }

//    public List<ChiTietDeThi> getBaiThi(String made){
//        return chiTietDeThiRepository.findAll().stream().filter(p->p.getDethi().getMadethi().equals(made)).toList();
//    }

}
