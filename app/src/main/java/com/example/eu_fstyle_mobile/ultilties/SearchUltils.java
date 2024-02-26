package com.example.eu_fstyle_mobile.ultilties;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SearchUltils {

    // Hàm loại bỏ dấu từ chuỗi
    public static String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }
}