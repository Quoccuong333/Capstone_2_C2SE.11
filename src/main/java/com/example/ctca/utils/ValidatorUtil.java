package com.example.ctca.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidatorUtil {

    public static HashMap<String, String> toErrors(List<FieldError> fieldErrors) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (fieldErrors != null) {
            for (FieldError fieldError : fieldErrors) {
                hashMap.put(fieldError.getField(), fieldError.getCode());
            }
        }

        return hashMap;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static boolean isEmpty(MultipartFile file) {
        if (file == null) {
            return true;
        }

        return file.getOriginalFilename() == null || file.getOriginalFilename().equalsIgnoreCase("");
    }

    // not null and length > 0
    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static boolean checkPhone(String phone) {
        String regexStr = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static List<String> checkDuplicateNumber(String[] str) {
        List<String> result = new ArrayList<>(Arrays.asList(str));

        if (result.size() == 1) {
            return result;
        } else {
            for (int i = 0; i < result.size() - 1; i++) {
                for (int j = i + 1; j < result.size(); j++) {
                    if (result.get(i).equalsIgnoreCase(result.get(j))) {
                        result.remove(j);
                        result.remove(i);
                    }

                }
            }
        }
        return result;
    }

}
