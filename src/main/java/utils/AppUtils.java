package utils;

import models.Password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static utils.Constant.DATE_TIME_FORMATTER;

public class AppUtils {
    private static final Scanner sc;

    static {
        sc = new Scanner(System.in);
    }

    public static double round(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String roundedNumber = decimalFormat.format(d).replace(",", ".");
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

    public static LocalDate getDate(String str) {
        try {
            System.out.println("Please enter date with format yyyy-mm-dd");
            return LocalDate.parse(getString(str));
        } catch (Exception e) {
            System.out.println("Invalid Date Format");
            return getDate(str);
        }
    }

    public static LocalDate getDateWithBound(String message, LocalDate startDate, LocalDate endDate) {
        try {
            System.out.printf("Please enter date from %s to %s with format yyyy-mm-dd \n", startDate.toString(), endDate.toString());
            LocalDate date = LocalDate.parse(getString(message));
            if (date.isBefore(startDate) || date.isAfter(endDate)) {
                System.out.println("Entered date is outside the allowed range.");
                return getDateWithBound(message, startDate, endDate);
            }
            return date;
        } catch (Exception e) {
            System.out.println("Invalid Date Format");
            return getDateWithBound(message, startDate, endDate);
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
            if (getDuration(now, dateTime) < 0 || getDuration(now, dateTime) > 4320) {
                throw new RuntimeException("Invalid Date Range. Please enter a date and time within the last 72 hours.");
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
        Duration duration = Duration.between(startTime, endTime);
        return (int) duration.toMinutes();
    }

    public static int getNextId(List<Integer> integerList) {
        return integerList.stream().max(Integer::compareTo).orElse(0) + 1;
    }

    public static String convertPrice(double price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        return currencyFormatter.format(price);
    }

//Pass here:
    // 3 Constant: trong lop Constant
    // Tao lop Password để luu 2 giá trị passcode và key
    // Chinh sua 1 tý ở login service là xong!
    public static Password hashPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hash = generateHash(password, salt);
        String encodedSaltAndHash = encodeSaltAndHash(salt, hash);
        return new Password(salt, encodedSaltAndHash);
    }

    public static String hashPassword(String password, byte[] salt) {
        byte[] hash = generateHash(password, salt);
        return encodeSaltAndHash(salt, hash);
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[Constant.SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] generateHash(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, Constant.ITERATIONS, Constant.KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String encodeSaltAndHash(byte[] salt, byte[] hash) {
        byte[] saltAndHash = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
        System.arraycopy(hash, 0, saltAndHash, salt.length, hash.length);
        return Base64.getEncoder().encodeToString(saltAndHash);
    }
}





