package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.*;
import com.example.TracNghiem.repository.*;
import com.example.TracNghiem.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private MonThiService monThiService;
    @Autowired
    private CauHoiService cauHoiService;
    @Autowired
    private ThongBaoService thongBaoService;
    @Autowired
    private CaThiService caThiService;
    @Autowired
    private PhongThiService phongThiService;
    @Autowired
    private IMonThiRepository monThiRepository;
    @Autowired
    private ICaThiRepository caThiRepository;
    @Autowired
    private DeThiService deThiService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChiTietDeThiService chiTietDeThiService;
    @Autowired
    private IChiTietDeThiRepository chiTietDeThiRepository;
    @Autowired
    private IDeThiRepository deThiRepository;
    @Autowired
    private IUserDeThiRepository userDeThiRepository;

    @GetMapping("/home")
    public String showCauhoiList(Model model) {
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        List<PhongThi> phongThiList = phongThiService.getAllPhongThi();
        List<MonThi> monThis = monThiService.getAllMonThi();
        List<CauHoi> cauHois = cauHoiService.getAllCauHoi();
        model.addAttribute("phongThiList", phongThiList);
        model.addAttribute("monthis", monThis);
        model.addAttribute("cauhois", cauHois);
        return "/userlayout"; // Đường dẫn tới template
    }
    @GetMapping("/giaodiencathi")
    public String showGiaoDienCaThi (Model model) {
        model.addAttribute("cathis", caThiService.getAllCaThi());
        return "/cathis/giaodiencathi";
    }
    @GetMapping("/userphongthi")
    public String showGiaoDienPhongThi (Model model) {
        model.addAttribute("phongthis", phongThiService.getAllPhongThi());
        model.addAttribute("cathis", caThiService.getAllCaThi());
        model.addAttribute("monthis", monThiService.getAllMonThi());

        return "/phongthis/userphongthi";
    }
    @GetMapping("/hienthidethi/{id}")
    public String hienThiDeThi(@PathVariable Long id, Model model) {
        // Lấy đề thi theo ID
        DeThi deThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đề thi với ID: " + id));

        // Lấy tên người dùng từ Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Tên người dùng đã đăng nhập

        // Lấy thông tin người dùng
        Optional<User> currentUserOptional = userService.findByUsername(username);
        User currentUser = currentUserOptional
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));

        // Lấy danh sách câu hỏi cho đề thi
        List<ChiTietDeThi> chiTietDeThiList = chiTietDeThiService.findByDeThiId(id);

        // Thêm thông tin vào model
        model.addAttribute("thongtinde", deThi);
        model.addAttribute("dethis", deThiService.getAllCauHoiByDeThi(deThi, currentUser));
        model.addAttribute("chiTietDeThiList", chiTietDeThiList); // Hiển thị tất cả câu hỏi

        return "/dethis/hienthidethi"; // Đường dẫn tới template
    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Long deThiId,
                             @RequestParam Map<String, String> allParams,
                             Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            DeThi deThi = deThiService.getDeThiById(deThiId)
                    .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));

            UserDeThi userDeThi = userDeThiRepository.findByUserAndDeThi(user, deThi)
                    .orElse(new UserDeThi(user, deThi));

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("question_")) {
                    Long chiTietDeThiId = Long.parseLong(key.substring(9));
                    // Gọi phương thức saveChiTietDeThi
                    saveChiTietDeThi(deThiId, user, chiTietDeThiId, value);
                }
            }

            // Tính điểm và lưu
            int score = calculateScore(userDeThi);
            userDeThi.setDiem(score);
            userDeThiRepository.save(userDeThi);
        } else {
            return "redirect:/error";
        }

        return "redirect:/hienthidethi/" + deThiId;
    }

private void saveChiTietDeThi(Long deThiId, User user, Long id, String selectedAnswer) {
    // Tìm ChiTietDeThi từ cơ sở dữ liệu dựa trên id
    ChiTietDeThi chiTietDeThi = chiTietDeThiRepository.findById(id)
            .orElseGet(() -> {
                // Nếu không tìm thấy, tạo mới một ChiTietDeThi
                ChiTietDeThi newChiTietDeThi = new ChiTietDeThi();
                newChiTietDeThi.setId(id);

                // Lấy DeThi từ cơ sở dữ liệu
                DeThi deThi = deThiRepository.findById(deThiId)
                        .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));

                // Lấy CauHoi từ ChiTietDeThi
                CauHoi cauHoi = chiTietDeThiService.findById(id)
                        .map(ChiTietDeThi::getCauHoi)
                        .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));

                newChiTietDeThi.setDeThi(deThi);
                newChiTietDeThi.setCauHoi(cauHoi);
                newChiTietDeThi.setUser(user);
                return newChiTietDeThi;
            });

    // Cập nhật giá trị "dapanchon"
    if (selectedAnswer != null && !selectedAnswer.trim().isEmpty()) {
        chiTietDeThi.setDapanchon(selectedAnswer);
        System.out.println("Đã cập nhật dapanchon: " + chiTietDeThi.getDapanchon());
    } else {
        chiTietDeThi.setDapanchon(null);
        System.out.println("Đã cập nhật dapanchon: null");
    }

    // Lưu ChiTietDeThi vào cơ sở dữ liệu
    chiTietDeThiRepository.save(chiTietDeThi);
}
    // Phương thức tính điểm

    private int calculateScore(UserDeThi userDeThi) {
        int score = 0;

        List<ChiTietDeThi> chiTietDeThiList = chiTietDeThiService.findByDeThi(userDeThi.getDeThi().getId());

        for (ChiTietDeThi chiTiet : chiTietDeThiList) {
            // Kiểm tra xem dapanchon và dapandung có khác null hay không trước khi so sánh
            String dapanchon = chiTiet.getDapanchon();
            String dapandung = chiTiet.getCauHoi().getDapandung();

            if (dapanchon != null && dapanchon.equals(dapandung)) {
                score += 1;
            }
        }

        return score;
    }

}