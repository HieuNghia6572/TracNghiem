package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.CauHoi;
import com.example.TracNghiem.entity.MonThi;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.services.ChiTietDeThiService;
import com.example.TracNghiem.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller // Đánh dấu lớp này là một Controller trong Spring MVC.
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ChiTietDeThiService chiTietDeThiService;
    @GetMapping("/login")
    public String login() {
        return "users/login";
    }
    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User()); // Thêm một đối tượng User mới vào model
        return "users/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, // Validateđối tượng User
                           @NotNull BindingResult bindingResult, // Kết quả của quá trình validate
                           Model model) {
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "users/register"; // Trả về lại view "register" nếu có lỗi
        }
        userService.save(user); // Lưu người dùng vào cơ sở dữ liệu
        userService.setDefaultRole(user.getUsername()); // Gán vai trò mặc định cho người dùng
        return "redirect:/login"; // Chuyển hướng người dùng tới trang "login"
    }

    @GetMapping("/users-list")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getAllUser());
        List<User> list = userService.getAllUser();
        return "users/users-list";
    }

    @GetMapping("edit/{id}")
    public String EditUser (@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mon thi Id:" + id));
        model.addAttribute("user", user);
        return "/users/update-user";
    }
    @PostMapping("update-user/{id}")
    public String UpdateUser(@Valid User  user , BindingResult result, @PathVariable Long id) {
        user.setId(id);
        if(result.hasErrors()){
            return"/users/update-user";
        }

        userService.updateUser(user);
        return "redirect:/users-list";

    }

    @GetMapping("profile")
    public  String  getProfile(Model model){
        User user = userService.getUserLogin();
        model.addAttribute("user",user);
        return "/users/profile";
    }

    @GetMapping("profile-information")
    public  String  getInformation(Model model){
        User user = userService.getUserLogin();
        model.addAttribute("user",user);
        return "/users/profile-information";
    }

    @GetMapping("chitietdethi/{id}")
    public  String  getChitietdethi(Model model, @PathVariable Long id){
        User user = userService.getUserLogin();
        model.addAttribute("user",user);
        model.addAttribute("chitiet", chiTietDeThiService.getAllChiTietDeThiByUserAndIdDe(id));
        return "users/chitietdethi";
    }


    @PostMapping("update-profile")
    public String UpdateProfile(@Valid User  user , BindingResult result) {
        if(result.hasErrors()){
            return"/users/profile";
        }
        userService.updateUser(user);
        return "redirect:/home";

    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id , Model mode) {
        userService.deleteUser(id);
        return "redirect:/users-list";
    }


}
