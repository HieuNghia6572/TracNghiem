package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CapDo;
import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.repository.ICapDoRepository;
import com.example.TracNghiem.repository.ICauHoiRepository;
import com.example.TracNghiem.repository.IDeThiRepository;
import com.example.TracNghiem.repository.IMonThiRepository;
import com.example.TracNghiem.services.CapDoService;
import com.example.TracNghiem.services.CauHoiService;
import com.example.TracNghiem.services.DeThiService;
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
@RequestMapping("/dethis")
public class DeThiController {
    private final DeThiService deThiService;
    private final IDeThiRepository deThiRepository;
    private final ICapDoRepository capDoRepository;
    private final CapDoService capDoService;
    private final MonThiService monThiService;
    private final IMonThiRepository monThiRepository;
    private  final CauHoiService cauHoiService;


    @GetMapping
    public String showDethiList(Model model) {
        model.addAttribute("dethis", deThiService.getAllDeThi());
        List<MonThi> monThiList = monThiRepository.findAll();
        model.addAttribute("monThiList", monThiList);
        return "/dethis/dethis-list";
    }
    @GetMapping("/add")
    public String showAddDethi(Model model) {
        model.addAttribute("dethi", new DeThi());
        model.addAttribute("capdos", capDoService.getAllCapDo());
        List<MonThi> monthis = monThiService.getAllMonThi();
        model.addAttribute("monthis", monthis);
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
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute @Valid DeThi dethi,
                             BindingResult bindingResult,
                             @RequestParam Long monthiId,

                             Model model) { // Thêm Model vào đây
        if (bindingResult.hasErrors()) {
            List<MonThi> monthis = monThiService.getAllMonThi();
            model.addAttribute("monthis", monthis); // Thêm danh sách môn thi vào mô hình nếu có lỗi
            return "dethis/add-dethi"; // Trả về form nếu có lỗi
        }
        // Gán giá trị cho các trường
//        cauhoi.setDapandung(dapandung);
//        dethi.setCapDo(capDo);
        dethi.setMonthiId(monthiId);
        deThiRepository.save(dethi);
        return "redirect:/dethis"; // Chuyển hướng về danh sách câu hỏi
    }

    @GetMapping("/edit/{id}")
    public String EditDeThi (@PathVariable Long id, Model model) {
        DeThi deThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));
        model.addAttribute("dethi", deThi);
        List<MonThi> monthis = monThiService.getAllMonThi();
        model.addAttribute("monthis", monthis);
        return "/dethis/update-deThi";
    }

    @PostMapping("/update/{id}")
    public String UpdateDethi(@PathVariable Long id, // Thêm ID vào đây
                              @ModelAttribute @Valid DeThi deThi,
                              BindingResult bindingResult,
                              @RequestParam Long monthiId) {
        if (bindingResult.hasErrors()) {
            return "dethis/update-dethi"; // Trả về form nếu có lỗi
        }

        // Tìm đối tượng DeThi từ cơ sở dữ liệu
        DeThi existingDeThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));

        // Cập nhật các thuộc tính// Giả sử bạn có thuộc tính "tenDeThi"
        existingDeThi.setMonthiId(monthiId);
        existingDeThi.setSlcauhoide(deThi.getSlcauhoide());
        existingDeThi.setSlcauhoitb(deThi.getSlcauhoitb());
        existingDeThi.setSlcauhoikho(deThi.getSlcauhoikho());

        // Lưu đối tượng cập nhật
        deThiService.updateDeThi(existingDeThi);
        return "redirect:/dethis"; // Chuyển hướng về danh sách câu hỏi
    }


    @GetMapping("/delete/{id}")
    public String themCauHoi(@PathVariable Long id, Model model) {
        DeThi deThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid de thi Id:" + id));

        // Số lượng câu hỏi theo cấp độ (giả sử bạn có các thuộc tính này trong `DeThi`)

        String slcauhoide = deThi.getSlcauhoide();
        String  slcauhoitb = deThi.getSlcauhoitb();
        String  slcauhoikho = deThi.getSlcauhoikho();

//        // Lấy câu hỏi ngẫu nhiên theo cấp độ và số lượng
//        CapDo capDoDe = capDoRepository.findByName("dễ");
//        CapDo capDoTB = capDoRepository.findByName("trung bình");
//        CapDo capDoKho = capDoRepository.findByName("khó");
//
//        if (capDoDe == null || capDoTB == null || capDoKho == null) {
//            throw new IllegalArgumentException("Không tìm thấy cấp độ yêu cầu: dễ, trung bình hoặc khó.");
//        }
//
//        Long capDoDeId = capDoDe.getId();
//        Long capDoTBId = capDoTB.getId();
//        Long capDoKhoId = capDoKho.getId();
//
//        List<CauHoi> cauHoiDe = cauHoiService.getRandomCauHoiByCapDo(capDoDeId, slcauhoide);
//        List<CauHoi> cauHoiTrungBinh = cauHoiService.getRandomCauHoiByCapDo(capDoTBId, slcauhoitb);
//        List<CauHoi> cauHoiKho = cauHoiService.getRandomCauHoiByCapDo(capDoKhoId, slcauhoikho);
//
//        // Đưa vào model để hiển thị
//        model.addAttribute("dethi", deThi);
//        model.addAttribute("cauHoiDe", cauHoiDe);
//        model.addAttribute("cauHoiTrungBinh", cauHoiTrungBinh);
//        model.addAttribute("cauHoiKho", cauHoiKho);

        return "/dethis/hienthidethi";
    }



    @GetMapping("hienthidethi/{id}")
    public String hienThiDeThi(@PathVariable Long id, Model model){

        DeThi deThi = deThiService.getDeThiById(id).orElseThrow(null);
        model.addAttribute("thongtinde", deThi);
        model.addAttribute("dethis", deThiService.getAllCauHoiByDeThi(deThi));
        return "/dethis/hienthidethi";
    }





}
