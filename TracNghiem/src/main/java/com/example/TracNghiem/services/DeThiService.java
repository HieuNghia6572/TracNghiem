package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.*;
import com.example.TracNghiem.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class DeThiService {
    private final IDeThiRepository deThiRepository;
    private final IChiTietDeThiRepository chiTietDeThiRepository;
    private final ICauHoiRepository cauHoiRepository;
    private final ICapDoRepository capDoRepository;
    private final ChiTietDeThiService chiTietDeThiService;
    private final IUserRepository userRepository;

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

    public List<ChiTietDeThi> getAllCauHoiByDeThi(DeThi deThi, User user) {

        if (chiTietDeThiService.getDeThi(deThi.getMadethi()).isEmpty()) {
            String str1 = deThi.getSlcauhoide();
            String str2 = deThi.getSlcauhoitb();
            String str3 = deThi.getSlcauhoikho();
            if (str1.isEmpty()) {
                str1 = "0";
            }
            if (str2.isEmpty()) {
                str2 = "0";
            }
            if (str3.isEmpty()) {
                str3 = "0";
            }


            Long idMonThi = Long.parseLong(deThi.getMonthiId().toString());

            List<CauHoi> listCauHoiDe = cauHoiRepository.findRandomCauHoiByCapDoAndMonThi(Long.parseLong("1"), idMonThi, Integer.parseInt(str1));
            List<CauHoi> listCauHoiTB = cauHoiRepository.findRandomCauHoiByCapDoAndMonThi(Long.parseLong("2"), idMonThi, Integer.parseInt(str2));
            List<CauHoi> listCauHoiKho = cauHoiRepository.findRandomCauHoiByCapDoAndMonThi(Long.parseLong("3"), idMonThi, Integer.parseInt(str3));

            List<CauHoi> allCauHoi = Stream.of(listCauHoiDe, listCauHoiTB, listCauHoiKho)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            // Xáo trộn từng danh sách câu hỏi N
            Collections.shuffle(listCauHoiDe);
            Collections.shuffle(listCauHoiTB);
            Collections.shuffle(listCauHoiKho);
            // N
            chiTietDeThiService.saveDeThi(allCauHoi, deThi,user);

        }
        return chiTietDeThiService.getDeThi(deThi.getMadethi());


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
        existingDeThi.setTendethi(deThi.getTendethi());


        return deThiRepository.save(existingDeThi);
    }

    public List<ChiTietDeThi> getBaiThi(String made) {
        return chiTietDeThiRepository.findAll().stream()
                .filter(p -> p.getDeThi() != null && p.getDeThi().getMadethi().equals(made)).toList();
    }

}
