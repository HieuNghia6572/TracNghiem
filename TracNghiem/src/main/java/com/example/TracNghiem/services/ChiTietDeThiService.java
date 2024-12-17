package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.repository.IChiTietDeThiRepository;
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
    private final UserService userService;

    // Lấy tất cả các chi tiết đề thi
    public List<ChiTietDeThi> getAllChiTietDeThi() {
        return chiTietDeThiRepository.findAll();
    }

    // Lấy chi tiết đề thi theo ID
    public Optional<ChiTietDeThi> getChiTietDeThiById(Long id) {
        return chiTietDeThiRepository.findById(id);
    }

    // Thêm chi tiết đề thi mới
    public ChiTietDeThi addChiTietDeThi(@NotNull ChiTietDeThi chiTietDeThi) {
        return chiTietDeThiRepository.save(chiTietDeThi);
    }

    // Xóa chi tiết đề thi theo ID
    public void deleteChiTietDeThi(Long id) {
        if (!chiTietDeThiRepository.existsById(id)) {
            throw new IllegalStateException("Chi tiết đề thi với ID " + id + " không hợp lệ.");
        }
        chiTietDeThiRepository.deleteById(id);
    }

    // Cập nhật chi tiết đề thi
    public ChiTietDeThi updateChiTietDeThi(@NotNull ChiTietDeThi chiTietDeThi) {
        ChiTietDeThi existingChiTietDeThi = chiTietDeThiRepository.findById(chiTietDeThi.getId())
                .orElseThrow(() -> new IllegalStateException("Chi tiết đề thi với ID " +
                        chiTietDeThi.getId() + " không tồn tại."));
        existingChiTietDeThi.setDeThi(chiTietDeThi.getDeThi());
        existingChiTietDeThi.setCauHoi(chiTietDeThi.getCauHoi());

        return chiTietDeThiRepository.save(existingChiTietDeThi);
    }

    // Lưu chi tiết đề thi từ danh sách câu hỏi
    public void saveDeThi(List<CauHoi> listCauHoi, DeThi deThi, User user) {
        for (CauHoi cauHoi : listCauHoi) {
            ChiTietDeThi chiTietDeThi = new ChiTietDeThi();
            chiTietDeThi.setCauHoi(cauHoi);
            chiTietDeThi.setDeThi(deThi);
            chiTietDeThi.setUser(user);
            chiTietDeThiRepository.save(chiTietDeThi);
        }
    }

    // Lấy tất cả chi tiết đề thi theo mã đề
    public List<ChiTietDeThi> getDeThi(String made) {
        return chiTietDeThiRepository.findAll().stream()
                .filter(p -> p.getDeThi() != null && p.getDeThi().getMadethi().equals(made))
                .toList();
    }

    // Xóa tất cả câu hỏi của một đề thi cụ thể
    public void deleteBaiThiCu(DeThi deThi) {
        List<ChiTietDeThi> deleteList = chiTietDeThiRepository.findAll().stream()
                .filter(p -> p.getDeThi() != null && p.getDeThi().getMadethi().equals(deThi.getMadethi()))
                .toList();
        chiTietDeThiRepository.deleteAll(deleteList);
    }
    public List<ChiTietDeThi> findAll() {//them dong nay
        return chiTietDeThiRepository.findAll();
    }
    public ChiTietDeThi save(ChiTietDeThi chiTietDeThi) {//them dong nay
        return chiTietDeThiRepository.save(chiTietDeThi);
    }
    public List<ChiTietDeThi> findByDeThiId(Long deThiId) {
        return chiTietDeThiRepository.findByDeThiId(deThiId);
    }

//    public Optional<ChiTietDeThi> findByUserAndCauHoi(User user, CauHoi cauHoi) {
//        return chiTietDeThiRepository.findByUserAndCauHoi(user, cauHoi);
//    }
    public Optional<ChiTietDeThi> findById(Long id) {
        return chiTietDeThiRepository.findById(id);
    }
    public List<ChiTietDeThi> findByDeThiIdAndUserId(Long deThiId, Long userId) {
        return chiTietDeThiRepository.findByDeThiIdAndUserId(deThiId, userId);
    }
    // Phương thức tìm tất cả ChiTietDeThi theo ID đề thi
    public List<ChiTietDeThi> findByDeThi(Long deThiId) {
        return chiTietDeThiRepository.findByDeThiId(deThiId);
    }
    public ChiTietDeThi saveChiTietDeThi(ChiTietDeThi chiTietDeThi) {
        return chiTietDeThiRepository.save(chiTietDeThi);
    }

    public ChiTietDeThi updateDapanchon(Long id, String dapanchon) {
        ChiTietDeThi chiTietDeThi = chiTietDeThiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết đề thi không tồn tại"));
        chiTietDeThi.setDapanchon(dapanchon);
        return chiTietDeThiRepository.save(chiTietDeThi);
    }
    public Optional<ChiTietDeThi> findByCauHoiIdAndUserIdAndCaThiId(Long cauHoiId, Long userId, Long caThiId) {
        return chiTietDeThiRepository.findByCauHoiIdAndUserIdAndCaThiId(cauHoiId, userId, caThiId);
    }

    public List<ChiTietDeThi> getAllChiTietDeThiByUserAndIdDe(Long id){
        User user = userService.getUserLogin();
        return chiTietDeThiRepository.findAll().stream().filter(p-> p.getDeThi().getId().equals(id) && p.getUser().getId().equals(user.getId())).toList();
    }

}