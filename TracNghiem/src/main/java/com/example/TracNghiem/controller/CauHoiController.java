    package com.example.TracNghiem.controller;

    import com.example.TracNghiem.entity.CapDo;
    import com.example.TracNghiem.entity.CauHoi;
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

        // Xử lý form thêm câu hỏi
        /*@PostMapping("/submit")
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
        }*/

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


        // Xử lý cập nhật câu hỏi
        /*@PostMapping("/update")
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
        }*/

        // Xử lý yêu cầu xóa câu hỏi
        @GetMapping("/delete/{id}")
        public String deleteCauHoi(@PathVariable Long id) {
            cauHoiService.deleteCauHoi(id);
            return "redirect:/cauhois"; // Chuyển hướng về danh sách câu hỏi
        }
        @PostMapping("/update/{id}")
        public String updateProduct(@PathVariable Long id, @Valid CauHoi cauHoi,@RequestParam String dapandung,
                                    BindingResult result,@RequestParam("image") MultipartFile imageFile
        ){
            if (result.hasErrors()) {
                cauHoi.setId(id); // set id to keep it in the form in case of errors
                return "/cauhois/update-cauhoi";
            }
            if (!imageFile.isEmpty()) {
                try {
                    String imageName = saveImage(imageFile);
                    cauHoi.setImgUrl("/img/" +imageName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            cauHoiService.updateCauHoi(cauHoi, dapandung);
            return "redirect:/cauhois";
        }
    }