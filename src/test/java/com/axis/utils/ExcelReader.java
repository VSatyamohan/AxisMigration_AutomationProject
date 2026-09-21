package com.axis.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.*;

public class ExcelReader {
    public static List<Map<String, String>> readSheet(Path file, String sheetName) {
        List<Map<String, String>> rows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file.toFile());
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

            Row header = sheet.getRow(0);
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                Map<String, String> data = new LinkedHashMap<>();
                for (int c = 0; c < header.getLastCellNum(); c++) {
                    data.put(header.getCell(c).getStringCellValue(),
                            getCellValue(row.getCell(c)));
                }
                rows.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to read Excel: " + file, e);
        }
        return rows;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }
}
