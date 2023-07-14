package services;

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

    public Double firstCalculateFare(Ride ride) {
        if (ride.getExpectedDistance() <= 30) {
            return ride.getExpectedDistance() * ride.getCarType().getPriceUnder30()
                    + ride.getCarType().getOpenPrice() + ride.getExpectedWaitTime()
                    * ride.getCarType().getWaitPrice();
        } else {
            return (ride.getExpectedDistance() - 30) * ride.getCarType().getPriceUpper30()
                    + 30 * ride.getCarType().getPriceUnder30() + ride.getCarType().getOpenPrice()
                    + ride.getExpectedWaitTime() * ride.getCarType().getWaitPrice();
        }
    }

    public Double lastCalculateFare(Ride ride) {
        if ( ride.getActualDistance()  <= 30) {
            return  ride.getActualDistance()  * ride.getCarType().getPriceUnder30()
                    + ride.getCarType().getOpenPrice() + ride.getActualWaitTime()
                    * ride.getCarType().getWaitPrice();
        } else {
            return ( ride.getActualDistance()  - 30) * ride.getCarType().getPriceUpper30()
                    + 30 * ride.getCarType().getPriceUnder30() + ride.getCarType().getOpenPrice()
                    + ride.getActualWaitTime() * ride.getCarType().getWaitPrice();
        }
    }
}
