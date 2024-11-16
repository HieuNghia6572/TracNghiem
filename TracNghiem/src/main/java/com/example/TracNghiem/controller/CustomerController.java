package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.*;
import com.example.TracNghiem.repository.ICaThiRepository;
import com.example.TracNghiem.repository.IMonThiRepository;
import com.example.TracNghiem.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        User currentUser = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));

        // Gọi phương thức với cả DeThi và User
        model.addAttribute("thongtinde", deThi);
        model.addAttribute("dethis", deThiService.getAllCauHoiByDeThi(deThi, currentUser));

        return "/dethis/hienthidethi";
    }
}