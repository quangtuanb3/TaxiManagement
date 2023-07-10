package services;

import models.Ride;
import utils.DistanceCalculator;

public class FareCalculator {
    private Double expectedDistance;
    private Double actualDistance;

    public FareCalculator() {
    }

    public Double firstCalculateFare(Ride ride) {
        expectedDistance = DistanceCalculator.calculateDistance(ride.getPickupLocation().getAddress(), ride.getExpectedDestination().getAddress());
        if (expectedDistance <= 30) {
            return expectedDistance * ride.getCarType().getPriceUnder30()
                    + ride.getCarType().getOpenPrice() + ride.getExpectedWaitTime()
                    * ride.getCarType().getWaitPrice();
        } else {
            return (expectedDistance - 30) * ride.getCarType().getPriceUpper30() + 30 * ride.getCarType().getPriceUnder30() + ride.getCarType().getOpenPrice() + ride.getExpectedWaitTime() * ride.getCarType().getWaitPrice();
        }
    }

    public Double lastCalculateFare(Ride ride) {
        actualDistance = DistanceCalculator.calculateDistance(ride.getPickupLocation().getAddress(), ride.getActualDestination().getAddress());
        if (actualDistance <= 30) {
            return actualDistance * ride.getCarType().getPriceUnder30() + ride.getCarType().getOpenPrice() + ride.getActualWaitTime() * ride.getCarType().getWaitPrice();
        } else {
            return (actualDistance - 30) * ride.getCarType().getPriceUpper30() + 30 * ride.getCarType().getPriceUnder30() + ride.getCarType().getOpenPrice() + ride.getActualWaitTime() * ride.getCarType().getWaitPrice();
        }
    }

    public  Double getExpectedDistance() {
        return expectedDistance;
    }

    public  Double getActualDistance() {
        return actualDistance;
    }


}
