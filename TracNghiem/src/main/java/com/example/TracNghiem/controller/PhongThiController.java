package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.PhongThi;
import com.example.TracNghiem.services.CaThiService;
import com.example.TracNghiem.services.PhongThiService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/phongthis")
public class PhongThiController {
    @Autowired
    private PhongThiService phongThiService;
    @Autowired
    private CaThiService caThiService;
    @GetMapping
    public String showPhongthiList(Model model) {
        model.addAttribute("phongthis", phongThiService.getAllPhongThi());
        return "/phongthis/phongthis-list";
    }
    @GetMapping("/add")
    public String showAddPhongthi(Model model) {
        model.addAttribute("phongthi", new PhongThi());
        model.addAttribute("cathis",caThiService.getAllCaThi() );
        return "/phongthis/add-phongthi";
    }
    @PostMapping("/add")
    public String addPhongthi(@Valid PhongThi phongthi , BindingResult result) {
        if(result.hasErrors()){
            return"/phongthis/add-phongthi";
        }
        phongThiService.addPhongThi(phongthi);
        return "redirect:/phongthis";

    }
    @GetMapping("/edit/{id}")
    public String EditPhongthi (@PathVariable Long id, Model model) {
        PhongThi phongThi = phongThiService.getPhongThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cauhoi Id:" + id));
        model.addAttribute("phongthi", phongThi);
        model.addAttribute("cathis",caThiService.getAllCaThi());
//        model.addAttribute("theloais", theLoaiService.getAllTheloai()); //Load theloais
        return "/phongthis/update-phongthi";
    }
    @PostMapping("/update/{id}")
    public String UpdatePhongthi(@Valid PhongThi phongthi ,BindingResult result, @PathVariable Long id, Model model) {
        if(result.hasErrors()){
            return"/phongthis/update-phongthi";
        }
        phongThiService.updatePhongThi(phongthi);
        return "redirect:/phongthis";

    }
    @GetMapping("/delete/{id}")
    public String DeletePhongthi (@PathVariable Long id) {
        phongThiService.deletePhongThi(id);
        return "redirect:/phongthis";
    }
}


