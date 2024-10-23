package com.student.student_management.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    public List<Map<String, String>> readExcel(InputStream inputStream) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            Row headerRow = sheet.getRow(0); // Assume the first row is the header

            int numberOfColumns = headerRow.getLastCellNum();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Start from row 1, skipping the header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < numberOfColumns; j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell cell = row.getCell(j);

                    String headerValue = headerCell != null ? headerCell.getStringCellValue() : "Column" + j;
                    String cellValue = getCellValue(cell);

                    rowData.put(headerValue, cellValue);
                }
                result.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}

