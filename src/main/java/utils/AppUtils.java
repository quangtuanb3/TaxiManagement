package utils;

import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static void main(String[] args) {
        String path = getDirectoryPath("src/main/java/database/managers.txt");
        System.out.println(path);
    }

}
