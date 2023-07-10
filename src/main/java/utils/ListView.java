package utils;

import java.util.Arrays;
import java.util.List;

public class ListView {
    public static List<String> loginMenu = Arrays.asList("Welcome to QT taxi", "1. Login", "2. Sign up", "0. Quit");


    public static void printMenu(List<String> menu) {
        for (String str : menu) {
            System.out.println(str);
        }
    }
}
