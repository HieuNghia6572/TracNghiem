package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.ChiTietDeThi;
import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.services.CauHoiService;
import com.example.TracNghiem.services.ChiTietDeThiService;
import com.example.TracNghiem.services.DeThiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chitietdethis")
public class ChiTietDeThiController {
    @Autowired
    private ChiTietDeThiService chiTietDeThiService;
    @Autowired
    private CauHoiService cauHoiService;
    @Autowired
    private DeThiService deThiService;

    @GetMapping
    public String showChitietdethiList(Model model) {
        model.addAttribute("chitietdethis", chiTietDeThiService.getAllChiTietDeThi());

        return "/chitietdethis/chitietdethis-list";
    }
    @GetMapping("/add")
    public String showAddChitietdethi(Model model) {
        model.addAttribute("chitietdethi", new ChiTietDeThi());
        model.addAttribute("cauhois",cauHoiService.getAllCauHoi());
        model.addAttribute("dethis", deThiService.getAllDeThi());
        return "/chitietdethis/add-chitietdethi";
    }
    @PostMapping("/add")
    public String addChitietdethi(@Valid ChiTietDeThi  chiTietDeThi , BindingResult result) {
        if(result.hasErrors()){
            return"/chitietdethis/add-chitietdethi";
        }
       chiTietDeThiService.addChiTietDeThi(chiTietDeThi);
        return "redirect:/chitietdethis";

    }
    @GetMapping("/edit/{id}")
    public String EditChiTietDeThi (@PathVariable Long id, Model model) {
        ChiTietDeThi chiTietDeThi = chiTietDeThiService.getChiTietDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));
        model.addAttribute("chitietdethi", chiTietDeThi);
        model.addAttribute("cauhois",cauHoiService.getAllCauHoi());
        model.addAttribute("dethis", deThiService.getAllDeThi());
        return "/chitietdethis/update-chitietdethi";
    }
    @PostMapping("/update/{id}")
    public String UpdateChitietdethi(@Valid ChiTietDeThi  chiTietDeThi , BindingResult result, @PathVariable Long id, Model model) {
        if(result.hasErrors()){
            return"/chitietdethis/update-chitietdethi";
        }
        chiTietDeThiService.updateChiTietDeThi(chiTietDeThi);
        return "redirect:/chitietdethis";

    }
    @GetMapping("/delete/{id}") public String DeleteChitietdethi (@PathVariable Long id, Model model) {
        chiTietDeThiService.deleteChiTietDeThi(id);
        return "redirect:/chitietdethis";
    }



    @GetMapping("/hienthidethi/{made}") public String hienthiDeThi (@PathVariable String made, Model model) {
        model.addAttribute("baithis", deThiService.getBaiThi(made));
        model.addAttribute("dethi", made);
        return "/chitietdethis/hienthidethi";
    }
}
