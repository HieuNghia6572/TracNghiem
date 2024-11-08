package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CapDo;
import com.example.TracNghiem.repository.ICapDoRepository; // Giả sử bạn có repository cho CapDo
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CapDoService {

    private final ICapDoRepository capDoRepository;

    @PostConstruct
    public void initData() {
        // Thêm dữ liệu vào bảng
        if (capDoRepository.count() == 0) {
            CapDo de = new CapDo();
            de.setId(Long.parseLong("1"));
            de.setTencapdo("Dễ");

            CapDo tb = new CapDo();
            tb.setId(Long.parseLong("2"));
            tb.setTencapdo("Trung Bình");

            CapDo kho = new CapDo();
            kho.setId(Long.parseLong("3"));
            kho.setTencapdo("Khó");

            capDoRepository.save(de);
            capDoRepository.save(tb);
            capDoRepository.save(kho);

        }
    }


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
