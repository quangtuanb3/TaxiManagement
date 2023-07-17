package utils;

import java.util.Arrays;
import java.util.List;

public class ListView {
    public static List<String> loginMenuList = Arrays.asList(
            "Welcome to QT taxi",
            "1. Login",
            "2. Sign up",
            "0. Quit");
    public static List<String> clientMenuList = Arrays.asList("Client menu",
            "1. Book ride",
            "2. Cancel ride",
            "3. Get ride detail",
            "4. Get ride history",
            "5. Edit profile",
            "0. Back to login menu");
    public static List<String> driverMenuList = Arrays.asList("Driver menu",
            "1. Approve ride",
            "2. Start ride",
            "3. Finish ride",
            "4. Get car detail",
            "5. Get ride detail",
            "6. Get ride history",
            "0. Back to login menu");
    public static List<String> updateClientList = Arrays.asList(
            "Select field you want to update: ",
            "1. Name",
            "2. Password",
            "3. Phone number",
            "0. Back to Client menu");

    public static List<String> managerMenuList = Arrays.asList(
            "Manger Menu",
            "1. Cars manger",
            "2. Drivers manger",
            "3. Clients manger",
            "4. Rides manger",
            "0. Back to Login menu");

    public static List<String> carManagerMenuList = Arrays.asList(
            "Cars manager menu",
            "1. List Cars",
            "2. Add new Car",
            "3. Remove Car",
            "4. Recall Car",
            "5. Assign Car",
            "6. Get Car's detail",
            "7. Update Car's Information",
            "0. Back to main menu");
    public static List<String> updateCarMenuList = Arrays.asList(
            "Update car Menu",
            "1. Edit Model",
            "2. Edit license plate number",
            "3. Edit insurance expiry date",
            "4. Edit registration expiry date",
            "0. Back to Cars manager menu "
    );
    public static List<String> clientManagerMenuList = Arrays.asList(
            "Client manager menu",
            "1. List Client",
            "2. Remove Client",
            "3. Get Client's detail",
            "0. Back to main menu"
    );
    public static List<String> driverManagerMenuList = Arrays.asList(
            "Driver manager menu",
            "1. List Drivers",
            "2. Add new Driver",
            "3. Remove Driver",
            "4. Update Driver's Information",
            "0. Back to main menu"
    );
    public static List<String> rideManagerMenuList = Arrays.asList(
            "Ride Manager Menu",
            "1. Print rides",
            "2. Get all rides in date",
            "3. Get all rides in month",
            "4. Get all rides in year",
            "0. Back to main menu");

    public static void printMenu(List<String> menu) {

        printMenuDecoration("open");
        for (String str : menu) {
            System.out.printf("║ %-40s ║%n", str);
        }

        printMenuDecoration("close");
    }

    public static void printMenuDecoration(String open) {
        if (open.equals("open")) {
            System.out.println("╔══════════════════════════════════════════╗");
        } else {
            System.out.println("╚══════════════════════════════════════════╝");
        }
    }

}
