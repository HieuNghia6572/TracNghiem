package com.example.TracNghiem.services;

import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.entity.UserDeThi;
import com.example.TracNghiem.repository.IUserDeThiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDeThiService {

    @Autowired
    private IUserDeThiRepository userDeThiRepository;

    @Autowired
    private DeThiService deThiService;

    // Lưu thông tin UserDeThi
    public UserDeThi save(UserDeThi userDeThi) {
        return userDeThiRepository.save(userDeThi);
    }

    // Tìm UserDeThi theo người dùng và đề thi
    public Optional<UserDeThi> findByUserAndDeThi(User user, Long deThiId) {
        // Lấy DeThi từ ID
        DeThi deThi = deThiService.getDeThiById(deThiId)
                .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));

        // Gọi phương thức repository với đối tượng DeThi
        return userDeThiRepository.findByUserAndDeThi(user, deThi);
    }

    // Lấy tất cả UserDeThi
    public List<UserDeThi> findAll() {
        return userDeThiRepository.findAll();
    }

    // Xóa UserDeThi theo ID
    public void deleteById(Long id) {
        userDeThiRepository.deleteById(id);
    }


    public List<UserDeThi> findAllDeThiByUser(User user){
        return userDeThiRepository.findAll().stream().filter(p->p.getUser().getId().equals(user.getId())).toList();
    }

}