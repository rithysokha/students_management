package com.student.student_management.controller;

import com.student.student_management.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<List<Map<String, String>>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            List<Map<String, String>> excelData = excelService.readExcel(inputStream);
            return ResponseEntity.ok(excelData);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

