package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.entity.PhongThi;
import com.example.TracNghiem.services.MonThiService;
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
@RequestMapping("/monthis")
public class MonThiController {
    @Autowired
    private MonThiService monThiService;

    @GetMapping
    public String showMonThiList(Model model) {
        model.addAttribute("monthis", monThiService.getAllMonThi());
        return "/monthis/monthis-list";
    }
    @GetMapping("/add")
    public String showAddMonthi(Model model) {
        model.addAttribute("monthi", new MonThi());
        return "/monthis/add-monthi";
    }
    @PostMapping("/add")
    public String addMonthi(@Valid MonThi monThi , BindingResult result) {
        if(result.hasErrors()){
            return"/monthis/add-monthi";
        }
        monThiService.addMonThi(monThi);
        return "redirect:/monthis";

    }
    @GetMapping("/edit/{id}")
    public String EditMonthi (@PathVariable Long id, Model model) {
        MonThi monThi = monThiService.getMonThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mon thi Id:" + id));
        model.addAttribute("monthi", monThi);
        return "/monthis/update-monThi";
    }
    @PostMapping("/update/{id}")
    public String UpdateMonthi(@Valid MonThi  monThi , BindingResult result, @PathVariable Long id, Model model) {
        if(result.hasErrors()){
            return"/monthis/update-monthi";
        }
        monThiService.updateMonThi(monThi);
        return "redirect:/monthis";

    }
    @GetMapping("/delete/{id}")
    public String DeleteMonthi (@PathVariable Long id, Model model) {
        monThiService.deleteMonThi(id);
        return "redirect:/monthis";
    }
}
