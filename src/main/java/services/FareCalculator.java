package services;

import models.Location;
import models.Ride;
import utils.DistanceCalculator;

import java.sql.Time;

public class FareCalculator {
    Ride ride;
    private static final double BASE_FARE = 5.0;
    private static final double PER_KM_FARE = 2.0;
    private static final double WAIT_FEE = 1.5;
    public double calculateFare(Location pickupLocation, Location destination) {
        double distance = DistanceCalculator.getDistance(pickupLocation.toString(), destination.toString());
        return BASE_FARE + (distance * PER_KM_FARE);
    }
    public double calculateFare(Location pickupLocation, Location destination, Time wait) {
        double distance = DistanceCalculator.getDistance(pickupLocation.toString(), destination.toString());
        return BASE_FARE + (distance * PER_KM_FARE);
    }
    Time time = new Time(10);
}
