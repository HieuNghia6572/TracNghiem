package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.services.CauHoiService;
import com.example.TracNghiem.services.MonThiService;
import com.example.TracNghiem.services.ThongBaoService;
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
    @GetMapping("/home")
    public String showCauhoiList(Model model) {
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        List<MonThi> monThis = monThiService.getAllMonThi();
        List<CauHoi> cauHois = cauHoiService.getAllCauHoi();
        model.addAttribute("monthis", monThis);
        model.addAttribute("cauhois", cauHois);
        return "/userlayout"; // Đường dẫn tới template
    }
}