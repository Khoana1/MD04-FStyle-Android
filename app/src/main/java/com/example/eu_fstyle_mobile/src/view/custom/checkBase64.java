package com.example.eu_fstyle_mobile.src.view.custom;

import java.util.Base64;

public class checkBase64 {
    public static boolean isBase64(String str) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(str);
            String encodedString = Base64.getEncoder().encodeToString(decodedBytes);
            return encodedString.equals(str);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
