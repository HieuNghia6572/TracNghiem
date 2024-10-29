package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.services.DeThiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dethis")
public class DeThiController {
    @Autowired
    private DeThiService deThiService;

    @GetMapping
    public String showDethiList(Model model) {
        model.addAttribute("dethis", deThiService.getAllDeThi());
        return "/dethis/dethis-list";
    }
    @GetMapping("/add")
    public String showAddDethi(Model model) {
        model.addAttribute("dethi", new DeThi());
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
    @GetMapping("/edit/{id}")
    public String EditDeThi (@PathVariable Long id, Model model) {
        DeThi deThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));
        model.addAttribute("dethi", deThi);
        return "/dethis/update-deThi";
    }
    @PostMapping("/update/{id}")
    public String UpdateDethi(@Valid DeThi  deThi , BindingResult result, @PathVariable Long id, Model model) {
        if(result.hasErrors()){
            return"/dethis/update-dethi";
        }
        deThiService.updateDeThi(deThi);
        return "redirect:/dethis";

    }
    @GetMapping("/delete/{id}")
    public String DeleteDethi (@PathVariable Long id, Model model) {
        deThiService.deleteDeThi(id);
        return "redirect:/dethis";
    }
}
