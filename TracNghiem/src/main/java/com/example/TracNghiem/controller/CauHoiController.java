package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CauHoi;
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
    // Process the form for adding a new product
//    @PostMapping("/add")
//    public String  addSanpham(@Valid SanPham sanPham, BindingResult result, @RequestParam("image") MultipartFile imageFile) {
//        if (result.hasErrors()) {
//            return "/sanphams/add-sanpham";
//        }
//        if (!imageFile.isEmpty()) {
//            try {
//                String imageName = saveImage(imageFile);
//                sanPham.setImgUrl("/images/" +imageName);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        sanPhamService.addSanPham(sanPham);
//        return "redirect:/sanphams";
//    }
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
        CauHoi cauHoi = cauHoiService.getCauHoiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cauhoi Id:" + id));
        model.addAttribute("cauhoi", cauHoi);
//        model.addAttribute("theloais", theLoaiService.getAllTheloai()); //Load theloais
        return "/cauhois/update-cauhoi";
    }
    // Process the form for updating a product
//    @PostMapping("/update/{id}")
//    public String updateSanPham(@PathVariable Long id, @Valid SanPham sanPham,
//                                BindingResult result,@RequestParam("image") MultipartFile imageFile ) {
//        if (result.hasErrors()) {
//            sanPham.setId(id); // set id to keep it in the form in case of errors
//            return "/sanphams/update-sanpham";
//        }
//        if (!imageFile.isEmpty()) {
//            try {
//                String imageName = saveImage(imageFile);
//                sanPham.setImgUrl("/images/" +imageName);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        sanPhamService.updateSanPham(sanPham);
//        return "redirect:/sanphams";
//    }
    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteCauHoi(@PathVariable Long id) {
        cauHoiService.deleteCauHoi(id);
        return "redirect:/cauhois";
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
