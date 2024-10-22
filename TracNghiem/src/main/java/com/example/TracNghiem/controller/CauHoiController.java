package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.repository.ICauHoiRepository;
import com.example.TracNghiem.repository.IMonThiRepository;
import com.example.TracNghiem.services.CauHoiService;
import com.example.TracNghiem.services.MonThiService;
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
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cauhois")
public class CauHoiController {
    @Autowired
    private CauHoiService cauHoiService;

    @Autowired
    private ICauHoiRepository cauHoiRepository;

    @Autowired
    private MonThiService monThiService;
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
        model.addAttribute("cauhoi", new CauHoi()); // Tạo đối tượng CauHoi mới
        List<MonThi> monthis = monThiService.getAllMonThi(); // Lấy danh sách môn thi
        model.addAttribute("monthis", monthis); // Thêm danh sách môn thi vào mô hình
        return "cauhois/add-cauhoi"; // Đảm bảo đường dẫn đúng
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("cauhoi", new CauHoi());
        return "form";
    }

    @PostMapping("/submit")
//    public String submitForm(@ModelAttribute @Valid CauHoi cauhoi,
//                             BindingResult bindingResult,
//                             @RequestParam String correctOption, // Đổi từ correctOption thành dapandung
//                             @RequestParam String capDo,
//                             @RequestParam Long monthiId) {
//        if (bindingResult.hasErrors()) {
//            List<MonThi> monthis = monThiService.getAllMonThi();
//            bindingResult.rejectValue("monthiId", "error.cauhoi", "Vui lòng chọn môn thi.");
//            return "/cauhois/add-cauhoi"; // Trả về form nếu có lỗi
//        }
//        cauhoi.setDapandung(correctOption); // Gán đáp án đúng
//        cauhoi.setCapDo(capDo);
//        cauhoi.setMonthiId(monthiId);
//        cauHoiRepository.save(cauhoi);
//        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
//    }
    public String submitForm(@ModelAttribute CauHoi cauhoi, @RequestParam String dapandung,@RequestParam String capDo) {
        cauhoi.setDapandung(dapandung); // Gán đáp án đúng
        cauhoi.setCapDo(capDo); // Gán cấp độ
        cauHoiRepository.save(cauhoi);
        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CauHoi cauHoi = cauHoiService.getCauHoiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Câu hỏi với ID " + id + " không tồn tại."));

        model.addAttribute("cauhoi", cauHoi);
        List<MonThi> monthis = monThiService.getAllMonThi();
        model.addAttribute("monthis", monthis); // Thêm danh sách môn thi vào mô hình

        // Debug thông tin môn thi
        System.out.println("Danh sách môn thi: " + monthis);

        return "cauhois/update-cauhoi"; // Đường dẫn đến view
    }
    @PostMapping("/update")
    public String updateCauHoi(@ModelAttribute @Valid CauHoi cauHoi,
                               @RequestParam String dapandung,
                               @RequestParam Long monthiId,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cauhois/update-cauhoi"; // Trả về form nếu có lỗi
        }

        // Gán giá trị cho các trường
        cauHoi.setMonthiId(monthiId);
        cauHoi.setDapandung(dapandung);

        // Debug thông tin
        System.out.println("Updating CauHoi with ID: " + cauHoi.getId());
        System.out.println("New monthiId: " + monthiId);

        // Gọi service để cập nhật
        cauHoiService.updateCauHoi(cauHoi, dapandung);

        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
    }

    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteCauHoi(@PathVariable Long id) {
        cauHoiService.deleteCauHoi(id);
        return "redirect:/cauhois";
    }
}/////tới đậy
