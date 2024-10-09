package com.example.TracNghiem.controller;

import com.example.TracNghiem.services.CauHoiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private CauHoiService cauHoiService;
    @GetMapping("/home")
    public String showCauhoiList(Model model) {
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        return "/userlayout";
    }
}