package com.example.TracNghiem.controller;

import com.example.TracNghiem.services.CauHoiService;
import com.example.TracNghiem.services.ThongBaoService;
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
    @Autowired
    private ThongBaoService thongBaoService;
    @GetMapping("/home")
    public String showCauhoiList(Model model) {
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());



        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        return "/userlayout";
    }
}