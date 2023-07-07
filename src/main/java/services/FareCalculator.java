package services;

import database.Enum.ECarType;
import models.Location;
import models.Ride;
import utils.DistanceCalculator;

public class FareCalculator {
    Ride ride;

    public double calculateFare(Location pickupLocation, Location destination, ECarType eCarType) {
        double distance = DistanceCalculator.calculateDistance(pickupLocation.getAddress(), destination.getAddress());
        if (distance <= 30) {
            return distance * eCarType.getPriceUnder30() + eCarType.getOpenPrice();
        } else {
            return (distance - 30) * eCarType.getPriceUpper30() + 30 * eCarType.getPriceUnder30() + eCarType.getOpenPrice();
        }
    }

    public double calculateFare(Location pickupLocation, Location destination, ECarType eCarType, int waitTime) {
        double distance = DistanceCalculator.calculateDistance(pickupLocation.getAddress(), destination.getAddress());
        if (distance <= 30) {
            return distance * eCarType.getPriceUnder30() + eCarType.getOpenPrice() + waitTime * eCarType.getWaitPrice();
        } else {
            return (distance - 30) * eCarType.getPriceUpper30() + 30 * eCarType.getPriceUnder30() + eCarType.getOpenPrice() + waitTime * eCarType.getWaitPrice();
        }
    }
}
