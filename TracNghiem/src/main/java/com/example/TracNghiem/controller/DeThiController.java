package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.*;
import com.example.TracNghiem.repository.ICapDoRepository;
import com.example.TracNghiem.repository.ICauHoiRepository;
import com.example.TracNghiem.repository.IDeThiRepository;
import com.example.TracNghiem.repository.IMonThiRepository;
import com.example.TracNghiem.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dethis")
public class DeThiController {
    private final DeThiService deThiService;
    private final IDeThiRepository deThiRepository;
    private final ICapDoRepository capDoRepository;
    private final CapDoService capDoService;
    private final MonThiService monThiService;
    private final IMonThiRepository monThiRepository;
    private  final CauHoiService cauHoiService;
    private final ChiTietDeThiService chiTietDeThiService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String showDethiList(Model model) {
        model.addAttribute("dethis", deThiService.getAllDeThi());
        List<MonThi> monThiList = monThiRepository.findAll();
        model.addAttribute("monThiList", monThiList);
        return "/dethis/dethis-list";
    }
    @GetMapping("/add")
    public String showAddDethi(Model model) {
        model.addAttribute("dethi", new DeThi());
        model.addAttribute("capdos", capDoService.getAllCapDo());
        List<MonThi> monthis = monThiService.getAllMonThi();
        model.addAttribute("monthis", monthis);
        return "/dethis/add-dethi";
    }
    @PostMapping("/add")
    public String addDethi(@Valid DeThi  dethi , BindingResult result) {
        if(result.hasErrors()){
            return"/dethis/add-dethi";
        }
        deThiService.addDeThi(dethi);
        return "redirect:/dethis";

    }
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute @Valid DeThi dethi,
                             BindingResult bindingResult,
                             @RequestParam Long monthiId,

                             Model model) { // Thêm Model vào đây
        if (bindingResult.hasErrors()) {
            List<MonThi> monthis = monThiService.getAllMonThi();
            model.addAttribute("monthis", monthis); // Thêm danh sách môn thi vào mô hình nếu có lỗi
            return "dethis/add-dethi"; // Trả về form nếu có lỗi
        }
        // Gán giá trị cho các trường
//        cauhoi.setDapandung(dapandung);
//        dethi.setCapDo(capDo);
        dethi.setMonthiId(monthiId);
        deThiRepository.save(dethi);
        return "redirect:/dethis"; // Chuyển hướng về danh sách câu hỏi
    }

    @GetMapping("/edit/{id}")
    public String EditDeThi (@PathVariable Long id, Model model) {
        DeThi deThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));
        model.addAttribute("dethi", deThi);
        List<MonThi> monthis = monThiService.getAllMonThi();
        model.addAttribute("monthis", monthis);
        return "/dethis/update-deThi";
    }

    @PostMapping("/update/{id}")
    public String UpdateDethi(@PathVariable Long id, // Thêm ID vào đây
                              @ModelAttribute @Valid DeThi deThi,
                              BindingResult bindingResult,
                              @RequestParam Long monthiId) {
        if (bindingResult.hasErrors()) {
            return "dethis/update-dethi"; // Trả về form nếu có lỗi
        }

        // Tìm đối tượng DeThi từ cơ sở dữ liệu
        DeThi existingDeThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));

        // Cập nhật các thuộc tính// Giả sử bạn có thuộc tính "tenDeThi"
        existingDeThi.setMonthiId(monthiId);
        existingDeThi.setTendethi(deThi.getTendethi());
        existingDeThi.setSlcauhoide(deThi.getSlcauhoide());
        existingDeThi.setSlcauhoitb(deThi.getSlcauhoitb());
        existingDeThi.setSlcauhoikho(deThi.getSlcauhoikho());

        // Lưu đối tượng cập nhật
        deThiService.updateDeThi(existingDeThi);
        chiTietDeThiService.deleteBaiThiCu(deThi);

        return "redirect:/dethis"; // Chuyển hướng về danh sách câu hỏi
    }


    @GetMapping("/delete/{id}")
    public String xoaCauHoi(@PathVariable Long id, Model model) {
        DeThi deThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));
        deThiService.deleteDeThi(deThi.getId());
        chiTietDeThiService.deleteBaiThiCu(deThi);
        return "redirect:/dethis";
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

        return "dethis/hienthidethi";
    }









}
