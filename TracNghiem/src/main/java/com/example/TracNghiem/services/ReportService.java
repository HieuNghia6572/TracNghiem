package com.example.TracNghiem.services;

import com.example.TracNghiem.dto.Report;
import com.example.TracNghiem.entity.CaThi;
import com.example.TracNghiem.repository.ICaThiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ICaThiRepository caThiRepository;

    public List<Report> generateReport() {
        List<CaThi> caThis = caThiRepository.findAll();
        List<Report> reports = new ArrayList<>();

        for (CaThi caThi : caThis) {
            long soLuongThiSinh = caThi.getPhongthi().stream()
                    .mapToLong(phongThi -> phongThi.getUser().size()) // Đếm số người dùng trong mỗi phòng thi
                    .sum();

            reports.add(new Report(
                    caThi.getTencathi(),
                    caThi.getMonthi().getTenmonthi(),
                    caThi.getTgbd(),
                    caThi.getTgkt(),
                    soLuongThiSinh
            ));
        }

        return reports;
    }
}
