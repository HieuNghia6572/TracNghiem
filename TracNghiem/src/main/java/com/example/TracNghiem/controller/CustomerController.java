package com.example.TracNghiem.controller;

import com.example.TracNghiem.entity.*;
import com.example.TracNghiem.repository.*;
import com.example.TracNghiem.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private MonThiService monThiService;
    @Autowired
    private CauHoiService cauHoiService;
    @Autowired
    private ThongBaoService thongBaoService;
    @Autowired
    private CaThiService caThiService;
    @Autowired
    private PhongThiService phongThiService;
    @Autowired
    private IMonThiRepository monThiRepository;
    @Autowired
    private ICaThiRepository caThiRepository;
    @Autowired
    private DeThiService deThiService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChiTietDeThiService chiTietDeThiService;
    @Autowired
    private IChiTietDeThiRepository chiTietDeThiRepository;
    @Autowired
    private IDeThiRepository deThiRepository;
    @Autowired
    private IUserDeThiRepository userDeThiRepository;

    @GetMapping("/home")
    public String showCauhoiList(Model model) {

        model.addAttribute("cauhois", cauHoiService.getAllCauHoi());
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        List<PhongThi> phongThiList = phongThiService.getAllPhongThi();
        List<MonThi> monThis = monThiService.getAllMonThi();
        List<CauHoi> cauHois = cauHoiService.getAllCauHoi();
        model.addAttribute("phongThiList", phongThiList);
        model.addAttribute("monthis", monThis);
        model.addAttribute("cauhois", cauHois);
        return "/userlayout"; // Đường dẫn tới template
    }
    @GetMapping("/giaodiencathi")
    public String showGiaoDienCaThi (Model model) {
        model.addAttribute("cathis", caThiService.getAllCaThi());
        return "/cathis/giaodiencathi";
    }
    @GetMapping("/userphongthi")
    public String showGiaoDienPhongThi (Model model) {
        model.addAttribute("phongthis", phongThiService.getAllPhongThi());
        model.addAttribute("cathis", caThiService.getAllCaThi());
        model.addAttribute("monthis", monThiService.getAllMonThi());

        return "/phongthis/userphongthi";
    }
    /*@GetMapping("/hienthidethi/{id}")
    public String hienThiDeThi(@PathVariable Long id, Model model) {
        // Lấy đề thi theo ID
        DeThi deThi = deThiService.getDeThiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đề thi với ID: " + id));

        // Lấy tên người dùng từ Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Tên người dùng đã đăng nhập

        // Lấy thông tin người dùng
        Optional<User> currentUserOptional = userService.findByUsername(username);
        User currentUser = currentUserOptional
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));

        // Lấy danh sách câu hỏi cho đề thi
        List<ChiTietDeThi> chiTietDeThiList = chiTietDeThiService.findByDeThiId(id);

        // Lấy thông tin ca thi từ database DuongDucTai
        CaThi caThi = caThiService.getCaThiById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CaThi với ID: " + id));
        int thoiLuong = caThiService.calculateThoiLuong(caThi.getTgbd(), caThi.getTgkt());

        // Tính thời gian còn lại theo phút DuongDucTai
        long minutesLeft = java.time.Duration.between(LocalDateTime.now(), caThi.getTgkt()).toMinutes();

        model.addAttribute("caThi", caThi);
        model.addAttribute("minutesLeft", minutesLeft);
        model.addAttribute("thoiLuong", thoiLuong);

        // Thêm thông tin vào model
        model.addAttribute("thongtinde", deThi);
        model.addAttribute("dethis", deThiService.getAllCauHoiByDeThi(deThi, currentUser));
        model.addAttribute("chiTietDeThiList", chiTietDeThiList); // Hiển thị tất cả câu hỏi
        model.addAttribute("userId", currentUser.getId()); // Thêm userId vào model

        return "/dethis/hienthidethi"; // Đường dẫn tới template
    }*/
    @GetMapping("/hienthidethi/{deThiId}/{caThiId}")
    public String hienThiDeThi(@PathVariable Long deThiId, @PathVariable Long caThiId, Model model) {
        // Lấy đề thi theo ID
        DeThi deThi = deThiService.getDeThiById(deThiId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đề thi với ID: " + deThiId));

        // Lấy thông tin ca thi từ database
        CaThi caThi = caThiService.getCaThiById(caThiId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CaThi với ID: " + caThiId));

        // Lấy thông tin người dùng từ Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));

        // Tính thời lượng và thời gian còn lại
        int thoiLuong = caThiService.calculateThoiLuong(caThi.getTgbd(), caThi.getTgkt());
        long minutesLeft = java.time.Duration.between(LocalDateTime.now(), caThi.getTgkt()).toMinutes();

        // Lấy danh sách câu hỏi cho đề thi
        List<ChiTietDeThi> chiTietDeThiList = chiTietDeThiService.findByDeThiId(deThiId);

        // Thêm thông tin vào model
        model.addAttribute("caThi", caThi);
        model.addAttribute("minutesLeft", minutesLeft);
        model.addAttribute("thoiLuong", thoiLuong);
        model.addAttribute("thongtinde", deThi);
        model.addAttribute("dethis", deThiService.getAllCauHoiByDeThi(deThi, currentUser));
        model.addAttribute("chiTietDeThiList", chiTietDeThiList);
        model.addAttribute("userId", currentUser.getId());

        return "/dethis/hienthidethi";
    }
    /*@PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Long deThiId,
                             @RequestParam Map<String, String> allParams,
                             Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            DeThi deThi = deThiService.getDeThiById(deThiId)
                    .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));

            UserDeThi userDeThi = userDeThiRepository.findByUserAndDeThi(user, deThi)
                    .orElse(new UserDeThi(user, deThi));

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("question_")) {
                    Long chiTietDeThiId = Long.parseLong(key.substring(9));
                    // Gọi phương thức saveChiTietDeThi
                    saveChiTietDeThi(deThiId, user, chiTietDeThiId, value);
                }
            }

            // Tính điểm và lưu
            int score = calculateScore(userDeThi);
            userDeThi.setDiem(score);
            userDeThiRepository.save(userDeThi);
        } else {
            return "redirect:/error";
        }

        return "redirect:/hienthidethi/" + deThiId;
    }

private void saveChiTietDeThi(Long deThiId, User user, Long id, String selectedAnswer) {
    // Tìm ChiTietDeThi từ cơ sở dữ liệu dựa trên id và user_id
    Optional<ChiTietDeThi> existingChiTietDeThi = chiTietDeThiRepository.findByCauHoiIdAndUserId(id, user.getId());

    ChiTietDeThi chiTietDeThi;

    if (existingChiTietDeThi.isPresent()) {
        ChiTietDeThi existingDetail = existingChiTietDeThi.get();

        // Kiểm tra xem user_id có trùng với user đang thực hiện hay không
        if (!existingDetail.getUser().getId().equals(user.getId())) {
            // Nếu user_id không trùng, tạo mới một ChiTietDeThi
            chiTietDeThi = new ChiTietDeThi();
            chiTietDeThi.setId(id);

            // Lấy DeThi từ cơ sở dữ liệu
            DeThi deThi = deThiRepository.findById(deThiId)
                    .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));
            CauHoi cauHoi = chiTietDeThiService.findById(id)
                    .map(ChiTietDeThi::getCauHoi)
                    .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));

            chiTietDeThi.setDeThi(deThi);
            chiTietDeThi.setCauHoi(cauHoi);
            chiTietDeThi.setUser(user);
            System.out.println("Đã tạo mới ChiTietDeThi cho user_id: " + user.getId());
        } else {
            // Nếu user_id trùng, sử dụng đối tượng đã tồn tại
            chiTietDeThi = existingDetail;
            System.out.println("ChiTietDeThi đã tồn tại cho user_id: " + user.getId());
        }
    } else {
        // Nếu không tìm thấy, tạo mới một ChiTietDeThi
        chiTietDeThi = new ChiTietDeThi();
        chiTietDeThi.setId(id);

        // Lấy DeThi từ cơ sở dữ liệu
        DeThi deThi = deThiRepository.findById(deThiId)
                .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));
        CauHoi cauHoi = chiTietDeThiService.findById(id)
                .map(ChiTietDeThi::getCauHoi)
                .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));

        chiTietDeThi.setDeThi(deThi);
        chiTietDeThi.setCauHoi(cauHoi);
        chiTietDeThi.setUser(user);
        System.out.println("Đã tạo mới ChiTietDeThi cho user_id: " + user.getId());
    }

    // Cập nhật giá trị "dapanchon"
    if (selectedAnswer != null && !selectedAnswer.trim().isEmpty()) {
        chiTietDeThi.setDapanchon(selectedAnswer);
        System.out.println("Đã cập nhật dapanchon: " + chiTietDeThi.getDapanchon() + " cho user_id: " + user.getId());
    } else {
        chiTietDeThi.setDapanchon(null);
        System.out.println("Đã cập nhật dapanchon: null cho user_id: " + user.getId());
    }

    // Lưu ChiTietDeThi vào cơ sở dữ liệu
    chiTietDeThiRepository.save(chiTietDeThi);
}*/





    @PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Long deThiId,
                             @RequestParam Long cathiId, // Thêm cathiId
                             @RequestParam Map<String, String> allParams,
                             Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            DeThi deThi = deThiService.getDeThiById(deThiId)
                    .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));

            UserDeThi userDeThi = userDeThiRepository.findByUserAndDeThi(user, deThi)
                    .orElse(new UserDeThi(user, deThi));

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("question_")) {
                    Long chiTietDeThiId = Long.parseLong(key.substring(9));
                    // Gọi phương thức saveChiTietDeThi với cathiId
                    saveChiTietDeThi(deThiId, cathiId, user, chiTietDeThiId, value);
                }
            }

            // Tính điểm và lưu
            int score = calculateScore(userDeThi);
            userDeThi.setDiem(score);
            userDeThiRepository.save(userDeThi);
        } else {
            return "redirect:/error";
        }

        return "redirect:/hienthidethi/" + deThiId + "/"+ cathiId;
    }

    private void saveChiTietDeThi(Long deThiId, Long cathiId, User user, Long id, String selectedAnswer) {
        // Tìm ChiTietDeThi từ cơ sở dữ liệu dựa trên id và user_id
        CaThi caThi = caThiService.getCaThiById(cathiId)
                .orElseThrow(() -> new RuntimeException("Ca thi không tồn tại"));
        Optional<ChiTietDeThi> existingChiTietDeThi = chiTietDeThiRepository.findByCauHoiIdAndUserId(id, user.getId());

        ChiTietDeThi chiTietDeThi;

        if (existingChiTietDeThi.isPresent()) {
            ChiTietDeThi existingDetail = existingChiTietDeThi.get();

            // Kiểm tra xem user_id có trùng với user đang thực hiện hay không
            if (!existingDetail.getUser().getId().equals(user.getId())) {
                // Nếu user_id không trùng, tạo mới một ChiTietDeThi
                chiTietDeThi = new ChiTietDeThi();
                chiTietDeThi.setId(id);

                // Lấy DeThi từ cơ sở dữ liệu
                DeThi deThi = deThiRepository.findById(deThiId)
                        .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));
                CauHoi cauHoi = chiTietDeThiService.findById(id)
                        .map(ChiTietDeThi::getCauHoi)
                        .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));



                chiTietDeThi.setDeThi(deThi);
                chiTietDeThi.setCauHoi(cauHoi);
                chiTietDeThi.setUser(user);
                chiTietDeThi.setCaThi(caThi); // Thêm cathiId
                System.out.println("Đã tạo mới ChiTietDeThi cho user_id: " + user.getId());
            } else {
                // Nếu user_id trùng, sử dụng đối tượng đã tồn tại
                chiTietDeThi = existingDetail;
                System.out.println("ChiTietDeThi đã tồn tại cho user_id: " + user.getId());
            }
        } else {
            // Nếu không tìm thấy, tạo mới một ChiTietDeThi
            chiTietDeThi = new ChiTietDeThi();
            chiTietDeThi.setId(id);

            // Lấy DeThi từ cơ sở dữ liệu
            DeThi deThi = deThiRepository.findById(deThiId)
                    .orElseThrow(() -> new RuntimeException("Đề thi không tồn tại"));
            CauHoi cauHoi = chiTietDeThiService.findById(id)
                    .map(ChiTietDeThi::getCauHoi)
                    .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));

            chiTietDeThi.setDeThi(deThi);
            chiTietDeThi.setCauHoi(cauHoi);
            chiTietDeThi.setUser(user);
            chiTietDeThi.setCaThi(caThi); // Thêm cathiId
            System.out.println("Đã tạo mới ChiTietDeThi cho user_id: " + user.getId());
        }

        // Cập nhật giá trị "dapanchon"
        if (selectedAnswer != null && !selectedAnswer.trim().isEmpty()) {
            chiTietDeThi.setDapanchon(selectedAnswer);
            System.out.println("Đã cập nhật dapanchon: " + chiTietDeThi.getDapanchon() + " cho user_id: " + user.getId());
        } else {
            chiTietDeThi.setDapanchon(null);
            System.out.println("Đã cập nhật dapanchon: null cho user_id: " + user.getId());
        }

        // Lưu ChiTietDeThi vào cơ sở dữ liệu
        chiTietDeThiRepository.save(chiTietDeThi);
    }
// tới đây user_id vẫn chua sua dc
    // Phương thức tính điểm
    private int calculateScore(UserDeThi userDeThi) {
        int score = 0;

        List<ChiTietDeThi> chiTietDeThiList = chiTietDeThiService.findByDeThi(userDeThi.getDeThi().getId());

        for (ChiTietDeThi chiTiet : chiTietDeThiList) {
            // Kiểm tra xem dapanchon và dapandung có khác null hay không trước khi so sánh
            String dapanchon = chiTiet.getDapanchon();
            String dapandung = chiTiet.getCauHoi().getDapandung();

            if (dapanchon != null && dapanchon.equals(dapandung)) {
                score += 1;
            }
        }

        return score;
    }

    @GetMapping("/ketquathi/{userId}/{deThiId}")
    public String showKetQuaThi(@PathVariable Long userId, @PathVariable Long deThiId, Model model) {
        UserDeThi userDeThi = userDeThiRepository.findByUserIdAndDeThiId(userId, deThiId);

        if (userDeThi == null) {
            model.addAttribute("message", "Không tìm thấy kết quả cho người dùng với ID: " + userId + " và đề thi ID: " + deThiId);
        } else {
            model.addAttribute("userDeThi", userDeThi);
            model.addAttribute("totalPoints", userDeThi.calculateTotalPoints()); // Thêm tổng điểm vào model
        }

        model.addAttribute("userId", userId);
        model.addAttribute("deThiId", deThiId);

        return "users/ketquathi";
    }
    // toi day

    private Long getUserIdFromPrincipal(String username) {
        Optional<User> optionalUser = userService.findByUsername(username); // Lưu ý là Optional
        if (optionalUser.isPresent()) {
            return optionalUser.get().getId(); // Lấy ID nếu có
        } else {
            return null; // Hoặc xử lý trường hợp không tìm thấy người dùng
        }
    }

}