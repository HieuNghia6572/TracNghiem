package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.entity.PhongThi;
import com.example.TracNghiem.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private MonThiService monThiService;
    @Autowired
    private CauHoiService cauHoiService;
    @Autowired
    private ThongBaoService thongBaoService;
    @Autowired
    private CaThiService caThiService;
    @Autowired
    private PhongThiService phongThiService;
    @GetMapping("/home")
    public String showCauhoiList(Model model) {
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        List<PhongThi> phongThiList = phongThiService.getAllPhongThi();
        List<MonThi> monThis = monThiService.getAllMonThi();
        List<CauHoi> cauHois = cauHoiService.getAllCauHoi();
        model.addAttribute("phongThiList", phongThiList);
        model.addAttribute("monthis", monThis);
        model.addAttribute("cauhois", cauHois);
        return "/userlayout"; // Đường dẫn tới template
    }
    @GetMapping("/giaodiencathi")
    public String showGiaoDienCaThi (Model model) {
        model.addAttribute("cathis", caThiService.getAllCaThi());
        return "/cathis/giaodiencathi";
    }
}