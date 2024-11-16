package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.services.CauHoiService;
import com.example.TracNghiem.services.ChiTietDeThiService;
import com.example.TracNghiem.services.DeThiService;
import com.example.TracNghiem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chitietdethis")
public class ChiTietDeThiController {

    private final ChiTietDeThiService chiTietDeThiService;
    private final CauHoiService cauHoiService;
    private final DeThiService deThiService;
    private final UserService userService; // Tiêm UserService

    @GetMapping
    public String showChitietdethiList(Model model) {
        model.addAttribute("chitietdethis", chiTietDeThiService.getAllChiTietDeThi());
        return "/chitietdethis/chitietdethis-list";
    }

    @GetMapping("/add")
    public String showAddChitietdethi(Model model) {
        model.addAttribute("chitietdethi", new ChiTietDeThi());
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        model.addAttribute("dethis", deThiService.getAllDeThi());
        return "/chitietdethis/add-chitietdethi";
    }

    @PostMapping("/add")
    public String addChitietdethi(@Valid ChiTietDeThi chiTietDeThi, BindingResult result, @RequestParam Long cauHoiId) {
        if (result.hasErrors()) {
            return "/chitietdethis/add-chitietdethi";
        }
        CauHoi cauHoi = cauHoiService.getCauHoiById(cauHoiId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CauHoi Id:" + cauHoiId));
        chiTietDeThi.setCauHoi(cauHoi); // Thiết lập tham chiếu đến CauHoi
        chiTietDeThiService.addChiTietDeThi(chiTietDeThi);
        return "redirect:/chitietdethis";
    }

    @GetMapping("/edit/{id}")
    public String editChiTietDeThi(@PathVariable Long id, Model model) {
        ChiTietDeThi chiTietDeThi = chiTietDeThiService.getChiTietDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ChiTietDeThi Id:" + id));
        model.addAttribute("chitietdethi", chiTietDeThi);
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        model.addAttribute("dethis", deThiService.getAllDeThi());
        return "/chitietdethis/update-chitietdethi";
    }

    @PostMapping("/update/{id}")
    public String updateChitietdethi(@Valid ChiTietDeThi chiTietDeThi, BindingResult result, @PathVariable Long id, @RequestParam Long cauHoiId, Model model) {
        if (result.hasErrors()) {
            return "/chitietdethis/update-chitietdethi";
        }
        CauHoi cauHoi = cauHoiService.getCauHoiById(cauHoiId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CauHoi Id:" + cauHoiId));
        chiTietDeThi.setCauHoi(cauHoi); // Thiết lập tham chiếu đến CauHoi
        chiTietDeThiService.updateChiTietDeThi(chiTietDeThi);
        return "redirect:/chitietdethis";
    }

    @GetMapping("/delete/{id}")
    public String deleteChitietdethi(@PathVariable Long id) {
        chiTietDeThiService.deleteChiTietDeThi(id);
        return "redirect:/chitietdethis";
    }

    @GetMapping("/hienthidethi/{made}")
    public String hienthiDeThi(@PathVariable String made, Model model) {
        model.addAttribute("baithis", deThiService.getBaiThi(made));
        model.addAttribute("dethi", made);
        return "/chitietdethis/hienthidethi";
    }
    //toi day
}