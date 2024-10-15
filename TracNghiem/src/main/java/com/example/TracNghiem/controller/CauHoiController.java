package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.repository.ICauHoiRepository;
import com.example.TracNghiem.services.CauHoiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cauhois")
public class CauHoiController {
    @Autowired
    private CauHoiService cauHoiService;

    @Autowired
    private ICauHoiRepository cauHoiRepository;
//    @Autowired
//    private TheLoaiService theLoaiService;
    // Đảm bảo bạn đã injectCategoryService
    // Display a list of all cauhoi

    @GetMapping
    public String showCauhoiList(Model model) {
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        return "/cauhois/cauhois-list";
    }
    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cauhoi", new CauHoi());
//        model.addAttribute("theloais", theLoaiService.getAllTheloai()); //Load sanphams
        return "/cauhois/add-cauhoi";
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("cauhoi", new CauHoi());
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute CauHoi cauhoi, @RequestParam String correctOption,@RequestParam String capDo) {
        cauhoi.setDapandung(correctOption); // Gán đáp án đúng
        cauhoi.setCapDo(capDo); // Gán cấp độ
        cauHoiRepository.save(cauhoi);
        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CauHoi cauHoi = cauHoiService.getCauHoiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Câu hỏi với ID " + id + " không tồn tại."));
        model.addAttribute("cauhoi", cauHoi);
        return "cauhois/update-cauhoi"; // Đường dẫn đến view
    }
    @PostMapping("/update")
    public String updateCauHoi(@ModelAttribute CauHoi cauHoi,
                               @RequestParam String correctOption) {
        cauHoiService.updateCauHoi(cauHoi, correctOption); // Gọi service để cập nhật
        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
    }

    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteCauHoi(@PathVariable Long id) {
        cauHoiService.deleteCauHoi(id);
        return "redirect:/cauhois";
    }
}
