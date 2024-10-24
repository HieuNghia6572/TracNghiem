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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cauhois")
public class CauHoiController {

    private final CauHoiService cauHoiService;
    private final ICauHoiRepository cauHoiRepository;
    private final MonThiService monThiService;
    private final IMonThiRepository monThiRepository;

    // Hiển thị danh sách câu hỏi
    @GetMapping
    public String showCauhoiList(Model model) {
        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        List<MonThi> monThiList = monThiRepository.findAll();
        model.addAttribute("monThiList", monThiList);
        return "/cauhois/cauhois-list";
    }

    // Hiển thị form thêm câu hỏi mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cauhoi", new CauHoi());
        List<MonThi> monthis = monThiService.getAllMonThi();
        model.addAttribute("monthis", monthis);
        return "cauhois/add-cauhoi";
    }

    // Xử lý form thêm câu hỏi
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute @Valid CauHoi cauhoi,
                             BindingResult bindingResult,
                             @RequestParam String dapandung,
                             @RequestParam String capDo,
                             @RequestParam Long monthiId,
                             Model model) { // Thêm Model vào đây
        if (bindingResult.hasErrors()) {
            List<MonThi> monthis = monThiService.getAllMonThi();
            model.addAttribute("monthis", monthis); // Thêm danh sách môn thi vào mô hình nếu có lỗi
            return "cauhois/add-cauhoi"; // Trả về form nếu có lỗi
        }
        // Gán giá trị cho các trường
        cauhoi.setDapandung(dapandung);
        cauhoi.setCapDo(capDo);
        cauhoi.setMonthiId(monthiId);
        cauHoiRepository.save(cauhoi);
        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
    }

    // Hiển thị form chỉnh sửa câu hỏi
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CauHoi cauHoi = cauHoiService.getCauHoiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Câu hỏi với ID " + id + " không tồn tại."));
        model.addAttribute("cauhoi", cauHoi);
        List<MonThi> monthis = monThiService.getAllMonThi();
        model.addAttribute("monthis", monthis); // Thêm danh sách môn thi vào mô hình
        return "cauhois/update-cauhoi";
    }

    // Xử lý cập nhật câu hỏi
    @PostMapping("/update")
    public String updateCauHoi(@ModelAttribute @Valid CauHoi cauHoi,
                               BindingResult bindingResult,
                               @RequestParam String dapandung,
                               @RequestParam Long monthiId) {
        if (bindingResult.hasErrors()) {
            return "cauhois/update-cauhoi"; // Trả về form nếu có lỗi
        }

        // Gán giá trị cho các trường
        cauHoi.setMonthiId(monthiId);
        cauHoi.setDapandung(dapandung);
        cauHoiService.updateCauHoi(cauHoi, dapandung);
        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
    }

    // Xử lý yêu cầu xóa câu hỏi
    @GetMapping("/delete/{id}")
    public String deleteCauHoi(@PathVariable Long id) {
        cauHoiService.deleteCauHoi(id);
        return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
    }
}