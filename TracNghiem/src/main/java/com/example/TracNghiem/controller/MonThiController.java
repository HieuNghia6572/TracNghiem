package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.entity.PhongThi;
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
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/monthis")
public class MonThiController {
    @Autowired
    private MonThiService monThiService;

    @GetMapping
    public String showMonThiList(Model model) {
        model.addAttribute("monthis", monThiService.getAllMonThi());
        return "/monthis/monthis-list";
    }
    @GetMapping("/add")
    public String showAddMonthi(Model model) {
        model.addAttribute("monthi", new MonThi());
        return "/monthis/add-monthi";
    }
    @PostMapping("/add")
    public String addMonthi(@Valid MonThi monThi , BindingResult result, @RequestParam("image") MultipartFile imageFile) {
        if(result.hasErrors()){
            return"/monthis/add-monthi";
        }
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImage(imageFile);
                monThi.setImgUrl("/img/" + imageName);  // Cập nhật đường dẫn hình ảnh
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        monThiService.addMonThi(monThi);
        return "redirect:/monthis";
    }
    private String saveImage(MultipartFile image) throws IOException {

        File saveFile = new ClassPathResource("static/img").getFile();
        String fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }
    @GetMapping("/edit/{id}")
    public String EditMonthi (@PathVariable Long id, Model model) {
        MonThi monThi = monThiService.getMonThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mon thi Id:" + id));
        model.addAttribute("monthi", monThi);
        return "/monthis/update-monThi";
    }
    @PostMapping("/update/{id}")
    public String UpdateMonthi(@Valid MonThi  monThi , BindingResult result, @PathVariable Long id, Model model,@RequestParam("image") MultipartFile imageFile) {
        if(result.hasErrors()){
            return"/monthis/update-monthi";
        }
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImage(imageFile);
                monThi.setImgUrl("/img/" +imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        monThiService.updateMonThi(monThi);
        return "redirect:/monthis";

    }
    @GetMapping("/delete/{id}")
    public String DeleteMonthi (@PathVariable Long id, Model model) {
        monThiService.deleteMonThi(id);
        return "redirect:/monthis";
    }
}
