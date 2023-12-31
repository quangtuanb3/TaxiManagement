package utils;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static String API_KEY = "tHhcvtGjlPCJsOE1HBmjKwL7u1j5wV1V";
    public static String MAP_GET_DISTANCE = "https://www.mapquestapi.com/directions/v2/route";
    public static String MAP_GET_ADDRESS = "https://www.mapquestapi.com/geocoding/v1/address?key=%s&location=%s";
    public static String MAP_GET_LOCATION = "https://www.mapquestapi.com/geocoding/v1/reverse?key=";


    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String EMAIL = "codegym.taxi@gmail.com";
    public static String PASSWORD = "Tuan789@";

    public static int MAX_RANGE = 60;

    public static final int SALT_LENGTH = 5; // Length of the salt in bytes
    public static final int ITERATIONS = 10000; // Number of iterations for the hashing algorithm
    public static final int KEY_LENGTH = 10; // Length of the generated hash in bits
}
