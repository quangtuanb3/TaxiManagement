package models;

public class CurrentUser {
//    public static String currentUser;

    public static Ride currentRide;
    public static Person user;

    public static Ride getCurrentRide() {
        return currentRide;
    }

    public static void setCurrentRide(Ride currentRide) {
        CurrentUser.currentRide = currentRide;
    }

    public static Person getUser() {
        return user;
    }

    public static void setUser(Person user) {
        CurrentUser.user = user;
    }
}
