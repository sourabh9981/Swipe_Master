package com.swipe.shrinkcom.swipe.logicall;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class UitlLogic {

    public static Set<Integer> calender(){
        Map<Integer, ArrayList<String>> data = new LinkedHashMap<Integer, ArrayList<String>>();
        Calendar calendar = Calendar.getInstance();
        int year_from = 1901;
        int year_to = 2004;
        for (int year = year_from; year <= year_to; year++) {
            if(! data.containsKey(year)) {
                data.put(year, new ArrayList<String>());
            }
            ArrayList<String> dates = data.get(year);
            for (int month = 1; month <= 12; month++) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1); // since 0 = January
                int maxDaysInMonth = calendar.getActualMaximum(Calendar.DATE);
                for (int day = 1; day <= maxDaysInMonth; day++) {
                    // format date in such a way, that month and days are padded with zeroes up to 2 digits
                    String dateAsString = String.format("%d-%02d-%02d", year, month, day);
                    dates.add(dateAsString);
                }
            }
        }
        System.out.println(data.keySet());
        return data.keySet();
    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return  uuid;
    }
}
