package com.example.TracNghiem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DhoController {
    private long totalSeconds = 90 * 60; // 5 phút = 300 giây
    private long remainingTime = totalSeconds; // thời gian còn lại bắt đầu bằng tổng số giây

    @GetMapping("/time-remaining")
    public Map<String, Object> getTimeRemaining() {
        // Giảm dần thời gian mỗi lần gọi (giả sử bạn có một cách để điều khiển việc này)
        remainingTime--;

        // Đảm bảo thời gian không âm
        if (remainingTime < 0) {
            remainingTime = 0;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("remainingTime", remainingTime);

        return response;
    }
}
