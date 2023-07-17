package services;

import Data.Enum.ECarType;
import models.Ride;

public class FareCalculator {

    public FareCalculator() {
    }

    private static FareCalculator instance;

    public static FareCalculator getInstance() {
        if (instance == null) {
            instance = new FareCalculator();
        }
        return instance;
    }

    public double calculateFirstFare(Ride ride) {
        double distance = ride.getExpectedDistance();
        double waitTime = ride.getExpectedWaitTime();
        return calculateBaseFare(ride.getCarType(), distance, waitTime);
    }

    public double calculateLastFare(Ride ride) {
        double distance = ride.getActualDistance();
        double waitTime = ride.getActualWaitTime();
        return calculateBaseFare(ride.getCarType(), distance, waitTime);

    }

    public double calculateBaseFare(ECarType eCarType, double distance, double waitTime) {
        if (distance <= 30) {
            return distance * eCarType.getPriceUnder30()
                    + eCarType.getOpenPrice()
                    + (waitTime > 20 ? waitTime / 60 : 0) * eCarType.getWaitPrice();
        } else {
            return (distance - 30) * eCarType.getPriceUpper30()
                    + 30 * eCarType.getPriceUnder30()
                    + eCarType.getOpenPrice()
                    + (waitTime > 20 ? waitTime / 60 : 0) * eCarType.getWaitPrice();
        }
    }

}
