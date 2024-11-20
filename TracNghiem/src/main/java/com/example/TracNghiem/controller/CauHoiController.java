    package com.example.TracNghiem.controller;

    import com.example.TracNghiem.entity.CapDo;
    import com.example.TracNghiem.entity.CauHoi;
    import com.example.TracNghiem.entity.ChiTietDeThi;
    import com.example.TracNghiem.entity.MonThi;
    import com.example.TracNghiem.repository.ICapDoRepository;
    import com.example.TracNghiem.repository.ICauHoiRepository;
    import com.example.TracNghiem.repository.IMonThiRepository;
    import com.example.TracNghiem.services.CapDoService;
    import com.example.TracNghiem.services.CauHoiService;
    import com.example.TracNghiem.services.MonThiService;
    import jakarta.transaction.Transactional;
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
    import java.nio.file.StandardCopyOption;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.UUID;

    @Controller
    @RequiredArgsConstructor
    @Transactional
    @RequestMapping("/cauhois")
    public class CauHoiController {

        private final   CauHoiService cauHoiService;

        private final   ICauHoiRepository cauHoiRepository;

        private final   MonThiService monThiService;

        private final IMonThiRepository monThiRepository;

        private final CapDoService capDoService;

        // Hiển thị danh sách câu hỏi
        @GetMapping
        public String showCauhoiList(Model model) {
            model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
            List<CauHoi> list = cauHoiService.getAllCauHoi();
            List<MonThi> monThiList = monThiRepository.findAll();
            model.addAttribute("monThiList", monThiList);
            return "/cauhois/cauhois-list";
        }

        // Hiển thị form thêm câu hỏi mới
        @GetMapping("/add")
        public String showAddForm(Model model) {
            model.addAttribute("cauhoi", new CauHoi());
            List<MonThi> monthis = monThiService.getAllMonThi();
            List<CapDo> capdos = capDoService.getAllCapDo();
            model.addAttribute("monthis", monthis);
            model.addAttribute("capdos", capdos );
            return "cauhois/add-cauhoi";

        }
        @PostMapping("/add")
        public String addcauhoi(@Valid CauHoi cauHoi, BindingResult result, @RequestParam("image") MultipartFile imageFile) {
            if (result.hasErrors()) {
                return "cauhois/add-cauhoi";  // Đảm bảo rằng đường dẫn này là chính xác
            }
            if (!imageFile.isEmpty()) {
                try {
                    String imageName = saveImage(imageFile);
                    cauHoi.setImgUrl("/img/" + imageName);  // Cập nhật đường dẫn hình ảnh
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cauHoiService.addCauHoi(cauHoi);
            return "redirect:/cauhois";  // Chuyển hướng lại danh sách sản phẩm
        }
        private String saveImage(MultipartFile image) throws IOException {

            File saveFile = new ClassPathResource("static/img").getFile();
            String fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
            Files.copy(image.getInputStream(), path);
            return fileName;
        }


        // Hiển thị form chỉnh sửa câu hỏi
        @GetMapping("/edit/{id}")
        public String showEditForm(@PathVariable Long id, Model model) {
            CauHoi cauHoi = cauHoiService.getCauHoiById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Câu hỏi với ID " + id + " không tồn tại."));
            model.addAttribute("cauhoi", cauHoi);
            List<MonThi> monthis = monThiService.getAllMonThi();
            model.addAttribute("monthis", monthis); // Thêm danh sách môn thi vào mô hình
            model.addAttribute("capdos", capDoService.getAllCapDo() );
            return "cauhois/update-cauhoi";
        }


        // Xử lý yêu cầu xóa câu hỏi
        @GetMapping("/delete/{id}")
        public String deleteCauHoi(@PathVariable Long id) {
            cauHoiService.deleteCauHoi(id);
            return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
        }
        @PostMapping("/update/{id}")
        public String updateCauHoi(@PathVariable Long id, @Valid CauHoi cauHoi,
                                   BindingResult result, @RequestParam("image") MultipartFile imageFile) {
            if (result.hasErrors()) {
                return "cauhois/update-cauhoi"; // Trả về form nếu có lỗi
            }

            // Lấy thực thể hiện tại từ cơ sở dữ liệu
            CauHoi existingCauHoi = cauHoiService.getCauHoiById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid CauHoi Id:" + id));

            // Cập nhật các thuộc tính của existingCauHoi
            existingCauHoi.setTen(cauHoi.getTen());
            existingCauHoi.setDapanA(cauHoi.getDapanA());
            existingCauHoi.setDapanB(cauHoi.getDapanB());
            existingCauHoi.setDapanC(cauHoi.getDapanC());
            existingCauHoi.setDapanD(cauHoi.getDapanD());

            // Xử lý tệp hình ảnh nếu có
            if (!imageFile.isEmpty()) {
                try {
                    String imageName = saveImage(imageFile);
                    existingCauHoi.setImgUrl("/img/" + imageName); // Cập nhật đường dẫn hình ảnh
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Cập nhật danh sách chitietbaithi nếu cần
            List<ChiTietDeThi> updatedChitietbaithi = cauHoi.getChitietbaithi();
            if (updatedChitietbaithi != null) {
                existingCauHoi.getChitietbaithi().clear();
                for (ChiTietDeThi chiTiet : updatedChitietbaithi) {
                    chiTiet.setCauHoi(existingCauHoi);
                    existingCauHoi.addChiTietDeThi(chiTiet);
                }
            }

            // Lưu cập nhật
            cauHoiService.updateCauHoi(existingCauHoi, cauHoi.getDapandung());
            return "redirect:/cauhois";
        }
        // toi day
    }