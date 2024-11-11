package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.ThongBao;
import com.example.TracNghiem.services.ThongBaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thongbaos")
public class ThongBaoController {

    @Autowired
    private ThongBaoService thongBaoService;

    @GetMapping
    public String showthongBaolist(Model model) {
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        return "/thongbaos/thongbaos-list";
    }
    @GetMapping("/add")
    public String showAddThongBao(Model model) {
        model.addAttribute("thongbao", new ThongBao());

        return "/thongbaos/add-thongbao";
    }
    @PostMapping("/add")
    public String addThongbao(@Valid ThongBao thongbao, BindingResult result, Model model) {
        // Nếu có lỗi xác thực hoặc đã tồn tại một thông báo
        if (result.hasErrors() || thongBaoService.hasThongBao()) {
            if (thongBaoService.hasThongBao()) {
                model.addAttribute("error", "Chỉ có thể thêm một thông báo. Đã tồn tại thông báo trong hệ thống.");
            }
            return "/thongbaos/add-thongbao";
        }
        thongBaoService.addThongBao(thongbao);
        return "redirect:/thongbaos";

    }
    @GetMapping("/edit/{id}")
    public String EditThongbao (@PathVariable Long id, Model model) {
        ThongBao thongBao = thongBaoService.getThongBaoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cauhoi Id:" + id));
        model.addAttribute("thongbao", thongBao);

//        model.addAttribute("theloais", theLoaiService.getAllTheloai()); //Load theloais
        return "/thongbaos/update-thongbao";
    }
    @PostMapping("/update/{id}")
    public String UpdateThongbao(@Valid ThongBao thongBao ,BindingResult result, @PathVariable Long id, Model model) {
        if(result.hasErrors()){
            return"/thongbaos/update-thongbao";
        }
        thongBaoService.updateThongBao(thongBao);
        return "redirect:/thongbaos";

    }
    @GetMapping("/delete/{id}")
    public String DeleteThongbao (@PathVariable Long id) {
        thongBaoService.deleteThongBao(id);
        return "redirect:/thongbaos";
    }
}