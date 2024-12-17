package com.example.TracNghiem.controller;


import com.example.TracNghiem.entity.EmailDetails;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.services.EmailService;
import com.example.TracNghiem.services.UserService;
import com.example.TracNghiem.utills.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.SecureRandom;
import java.util.Objects;


@Controller
@RequestMapping("/userForgot")
public class ForgotPasswordController {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();



    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "/users/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = generateRandomString(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/userForgot/reset_password?token=" + token;
            String status = sendEmail(email, resetPasswordLink);
            if(Objects.equals(status, "Lỗi khi gửi thư")){
                model.addAttribute("error", status);
            }
            else
                model.addAttribute("message", "Chúng tôi đã gửi liên kết đặt lại mật khẩu đến email của bạn. Vui lòng kiểm tra");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "/users/forgot_password_form";
    }

    private static String generateRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Độ dài phải lớn hơn 0.");
        }

        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(index));
        }

        return randomString.toString();
    }

    private String sendEmail(String recipientEmail, String link)
            {

        String subject = " phanhieunghia1054@gmail.com, Hỗ trợ NTT\n" +
                " Đây là liên kết để đặt lại mật khẩu của bạn";
        String content = "Xin Chào,\n"
                + "Bạn đã yêu cầu đặt lại mật khẩu của mình.\n"
                + "Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:\n"
                + "Truy cập đường dẫn: "+ link + " để đổi mật khẩu"
                + "\n"
                + "Bỏ qua email này nếu bạn nhớ mật khẩu của mình, "
                + "hoặc bạn chưa thực hiện yêu cầu.";
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(recipientEmail);
        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(content);
        return emailService.sendSimpleMail(emailDetails);



    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User customer = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (customer == null) {
            model.addAttribute("message", "Mã thông báo không hợp lệ");
            return "message";
        }

        return "/users/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User customer = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Đặt lại mật khẩu của bạn");

        if (customer == null) {
            model.addAttribute("message", "Mã thông báo không hợp lệ");
            return "message";
        } else {
            userService.updatePassword(customer, password);

            model.addAttribute("message", "Bạn đã đổi mật khẩu thành công.");
        }

        return "/users/reset_password_form";
    }


}
