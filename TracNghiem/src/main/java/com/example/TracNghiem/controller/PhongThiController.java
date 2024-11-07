package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.PhongThi;
import com.example.TracNghiem.services.CaThiService;
import com.example.TracNghiem.services.MonThiService;
import com.example.TracNghiem.services.PhongThiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/phongthis")
public class PhongThiController {
    @Autowired
    private  PhongThiService phongThiService;
    private  CaThiService caThiService;
    private  MonThiService monThiService;

    @GetMapping
    public String showPhongthiList(Model model) {
        model.addAttribute("phongthis", phongThiService.getAllPhongThi());
        return "phongthis/phongthis-list"; // Không cần dấu /
    }

    @GetMapping("/add")
    public String showAddPhongthi(Model model) {
        model.addAttribute("phongthi", new PhongThi());
        model.addAttribute("cathis", caThiService.getAllCaThi());
        return "phongthis/add-phongthi"; // Không cần dấu /
    }

    @PostMapping("/add")
    public String addPhongthi(@Valid @ModelAttribute PhongThi phongthi, BindingResult result) {
        if (result.hasErrors()) {
            return "phongthis/add-phongthi"; // Không cần dấu /
        }
        phongThiService.addPhongThi(phongthi);
        return "redirect:/phongthis";
    }

    @GetMapping("/edit/{id}")
    public String editPhongthi(@PathVariable Long id, Model model) {
        PhongThi phongThi = phongThiService.getPhongThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid phongthi Id: " + id));
        model.addAttribute("phongthi", phongThi);
        model.addAttribute("cathis", caThiService.getAllCaThi());
        return "phongthis/update-phongthi"; // Không cần dấu /
    }

    @PostMapping("/update/{id}")
    public String updatePhongthi(@Valid @ModelAttribute PhongThi phongthi, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            return "phongthis/update-phongthi"; // Không cần dấu /
        }
        phongThiService.updatePhongThi(phongthi);
        return "redirect:/phongthis";
    }

    @GetMapping("/delete/{id}")
    public String deletePhongthi(@PathVariable Long id) {
        phongThiService.deletePhongThi(id);
        return "redirect:/phongthis";
    }
    
}