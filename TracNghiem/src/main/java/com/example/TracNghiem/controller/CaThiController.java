package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CaThi;
import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.services.CaThiService;
import com.example.TracNghiem.services.CauHoiService;
import com.example.TracNghiem.services.MonThiService;
import com.example.TracNghiem.services.PhongThiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cathis")
public class CaThiController {
    @Autowired
    private CaThiService caThiService;

    @Autowired
    private MonThiService monThiService;

    /*@Autowired
    private PhongThiService phongThiService;*/
//    @Autowired
//    private TheLoaiService theLoaiService;
    // Đảm bảo bạn đã injectCategoryService
    // Display a list of all cauhoi

    @GetMapping
    public String showCathiList(Model model) {
        model.addAttribute("cathis", caThiService.getAllCaThi());
        return "/cathis/cathis-list";
    }
    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cathi", new CaThi());
        model.addAttribute("monthis", monThiService.getAllMonThi());
       // model.addAttribute("phongthis",phongThiService.getAllPhongThi() );
//        model.addAttribute("theloais", theLoaiService.getAllTheloai()); //Load sanphams
      //  model.addAttribute("cathi", caThiService.getAllCaThi()); //Load sanphams
        return "/cathis/add-cathi";
    }


    @GetMapping("/countdown/{id}")
    public String countdown(@PathVariable Long id, Model model) {
        CaThi caThi = caThiService.getCaThiById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CaThi với ID: " + id));
        int thoiLuong = caThiService.calculateThoiLuong(caThi.getTgbd(), caThi.getTgkt());

        // Tính thời gian còn lại theo phút
        long minutesLeft = java.time.Duration.between(LocalDateTime.now(), caThi.getTgkt()).toMinutes();

        model.addAttribute("caThi", caThi);
        model.addAttribute("minutesLeft", minutesLeft);
        model.addAttribute("thoiLuong", thoiLuong);
        return "/cathis/countdown";  // Tên file HTML để hiển thị đếm ngược
    }







    // Process the form for adding a new product
    @PostMapping("/add")
    public String  addCathi(@Valid CaThi caThi, BindingResult result) {
        if (result.hasErrors()) {
            return "/cathis/add-cathi";
        }
       /* if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImage(imageFile);
                sanPham.setImgUrl("/images/" +imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        caThiService.addCaThi(caThi);
        return "redirect:/cathis";
    }








    //    private String saveImage(MultipartFile image) throws IOException {
//        File saveFile = new ClassPathResource("static/images").getFile();
//        String fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
//        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
//        Files.copy(image.getInputStream(), path);
//        return fileName;
//    }
    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CaThi caThi = caThiService.getCaThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cathi Id:" + id));
        model.addAttribute("cathis", caThi);
        model.addAttribute("monthis", monThiService.getAllMonThi());
        /*model.addAttribute("phongthis",phongThiService.getAllPhongThi() );*/
//        model.addAttribute("theloais", theLoaiService.getAllTheloai()); //Load theloais
        return "/cathis/update-cathi";
    }
    // Process the form for updating a product
    @PostMapping("/update/{id}")
    public String updateCaThi(@PathVariable Long id, @Valid CaThi caThi,
                                BindingResult result ) {
        if (result.hasErrors()) {
            caThi.setId(id); // set id to keep it in the form in case of errors
            return "/cathis/update-cathi";
        }
       /* if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImage(imageFile);
                sanPham.setImgUrl("/images/" +imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        caThiService.updateCaThi(caThi);
        return "redirect:/cathis";
    }





    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteCauHoi(@PathVariable Long id) {
        caThiService.deleteCaThi(id);
        return "redirect:/cathis";
    }

    //TD Detail
  /*  @GetMapping("/details/{id}")
    public String Detail(@PathVariable Long id, Model model)
    {
        CauHoi cauHoi = cauHoiService.getCauHoiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("cauhoi", cauHoi);
        return "cauhois/cauhoi-details";
    }*/
}
