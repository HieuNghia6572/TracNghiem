package com.example.TracNghiem.controller;

import com.example.TracNghiem.dto.Report;
import com.example.TracNghiem.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping
    public String getReport(Model model) {
        List<Report> reports = reportService.generateReport();
        model.addAttribute("reports", reports);
        return "report"; // Tên file HTML
    }
}
