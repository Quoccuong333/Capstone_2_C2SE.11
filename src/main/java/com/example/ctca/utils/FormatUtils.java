package com.example.ctca.utils;

import java.text.DecimalFormat;

public class FormatUtils {

    private static final DecimalFormat df = new DecimalFormat("###,###,###");

    public static String formatNumber(int value) {
        try {
            String result = df.format(value);
            return result.startsWith(".") ? "0" + result : result;
        } catch (Exception ex) {
            return "";
        }
    }

    public static int formatNumber(String value) {
        try {
            String target = value.replaceAll(",", "").trim();
            return Integer.parseInt(target);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static String toEncodePrice(String input) {
        return input.replace(",", "");
    }

}
