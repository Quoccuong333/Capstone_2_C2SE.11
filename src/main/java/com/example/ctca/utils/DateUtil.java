package com.example.ctca.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

    private String pattern = "yyyy-MM-dd";

    public boolean compareDate(String endDate, String startDate) {
        if (!endDate.isEmpty() || !startDate.isEmpty()) {
            Date end = convertStringToDate(endDate, pattern);
            Date start = convertStringToDate(startDate, pattern);
            return start.before(end);
        }
        return false;
    }

    public static Date convertStringToDate(String value, String pattern) {
        try {
            if (value != null) {
                return new SimpleDateFormat(pattern).parse(value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDateToString(Date value, String pattern) {
        return new SimpleDateFormat(pattern).format(value);
    }

    public static boolean compareDate(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

    public static boolean compareEndDate(Date dateInput) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        int checkResult = dateInput.compareTo(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 30);
        Date date30 = calendar.getTime();

        int checkDate = dateInput.compareTo(date30);
        if (checkResult >= 0 && checkDate <= 0) {
            return true;
        }
        return false;
    }

    public static boolean compareStartDate(Date dateInput) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String strDate = formatter.format(today);

        Date date = DateUtil.convertStringToDate(strDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        int checkResult1 = dateInput.compareTo(date);
        if (checkResult1 >= 0){
            return true;
        }
        return false;
    }

    public static boolean compareStartDateEndDate(Date startDate, Date endDate){
        int result = startDate.compareTo(endDate);
        if (result <= 0){
            return true;
        }
        return false;
    }

}
