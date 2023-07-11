package utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static utils.Constant.DATE_TIME_FORMATTER;

public class AppUtils {
    private static Scanner sc;

    static {
        sc = new Scanner(System.in);
    }

    public static double round(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String roundedNumber = decimalFormat.format(d).replace(",", ".");
        System.out.println(roundedNumber);
        return Double.parseDouble(roundedNumber);
    }

    public static String getString(String str) {
        try {
            System.out.println(str);
            String data = sc.nextLine();
            if (data.equals("")) {
                throw new Exception();
            }
            return data;
        } catch (Exception e) {
            System.out.println("Empty data. Input again!");
            return getString(str);
        }
    }

    public static int getInt(String str) {
        try {
            return Integer.parseInt(getString(str));
        } catch (Exception e) {
            System.out.println("Input invalid");
            return getInt(str);
        }
    }

    public static int getIntWithBound(String str, int begin, int end) {
        try {
            int number = getInt(str);
            if (number < begin || number > end) {
                throw new NumberFormatException(String.format("Please input number between %d and %d", begin, end));
            }
            return number;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return getIntWithBound(str, begin, end);
        }
    }

    public static Date getDate(String str) {
        try {
            System.out.println("Please enter date with format YYYY-MM-DD");
            return Date.valueOf(getString(str));
        } catch (Exception e) {
            System.out.println("Invalid Date Format");
            return getDate(str);
        }
    }

    public static Time getTime(String str) {
        try {
            System.out.println("Please enter date with format HH:MM:SS");
            String time = getString(str);
            int hour = Integer.parseInt(time.split(":")[0]);
            if (hour > 24 || hour < 0) throw new RuntimeException("Hours Invalid");
            return Time.valueOf(time);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return getTime(str);
        } catch (Exception e) {
            System.out.println("Invalid Time");
            return getTime(str);
        }
    }


    public static String getDirectoryPath(String relPath) {
        Path path = Paths.get(relPath);
        return path.toAbsolutePath().toString();
    }

    public static LocalDateTime getDateTimeNow() {
        return LocalDateTime.now();
    }
    public static LocalDateTime getDateTime(String str) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(getString(str + " (yyyy-MM-dd HH:mm:ss):"), DATE_TIME_FORMATTER);
            LocalDateTime now = getDateTimeNow();
            if (getDuration(now, dateTime ) < 0 || getDuration(now, dateTime) > 1440) {
                throw new RuntimeException("Invalid Date Range. Please enter a date and time within the last 24 hours.");
            }
            return dateTime;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date and time in the format 'yyyy-MM-dd HH:mm:ss'.");
            return getDateTime(str);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return getDateTime(str);
        }
    }

    public static LocalDateTime parseDateTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(input, formatter);
    }

    public static int getDuration(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between( startTime, endTime);
        return (int) duration.toMinutes();
    }
    public static int getNextId(List<Integer> integerList){
        return integerList.stream().max(Integer::compareTo).orElse(0) + 1;
    }

}


