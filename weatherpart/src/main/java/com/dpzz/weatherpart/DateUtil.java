package com.dpzz.weatherpart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * xxxx
 * Date: 2024/7/26 16:09
 * Author: liangdp
 */
public class DateUtil {

    public static String transformISO8601Date(String dateISO8601) {
        // ISO 8601 formatted date-time string
       // String isoDateTime = "2024-07-26T17:00+08:00";
        String isoDateTime = dateISO8601;

        // Parse the ISO 8601 date-time string to OffsetDateTime
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(isoDateTime);
            // Convert OffsetDateTime to LocalDateTime
            LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
            // Define the desired output format
         //   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            // Format the LocalDateTime to the desired format
            return localDateTime.format(formatter);
        }else {
            try {
                // Remove the colon in the timezone offset for SimpleDateFormat compatibility
                isoDateTime = isoDateTime.replace(":", "");
                // Define the input format
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HHmmZ");
                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                // Parse the ISO 8601 date-time string to a Date object
                Date date = inputFormat.parse(isoDateTime);
                // Define the desired output format
               // SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd HH:mm");
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
                outputFormat.setTimeZone(TimeZone.getDefault());
                // Format the Date object to the desired format
                String formattedDateTime = outputFormat.format(date);
                // Print the formatted date-time
                return formattedDateTime;
             //   System.out.println(formattedDateTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isoDateTime;
    }
}
