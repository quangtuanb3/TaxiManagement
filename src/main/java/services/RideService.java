package services;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static models.CurrentUser.currentUser;

public class RideService implements BasicCRUD<Ride> {
    static List<Ride> listRides = new ArrayList<>();

    private FareCalculator fareCalculator;

    public RideService() {
    }

    @Override
    public List<Ride> getAll() {
        return listRides;
    }

    @Override
    public void create(Ride ride) {
        listRides.add(ride);
    }

    public Ride bookRide(Location pickupLocation, Location expectedDestination) {
        double fare = fareCalculator.calculateFare(pickupLocation, expectedDestination);
        Client client = ClientService.getByUsername(currentUser);
        Ride ride = new Ride(client, pickupLocation, expectedDestination, fare);
        CurrentUser.currentRide = ride;
        return ride;
    }

    @Override
    public void update(Ride ride) {
        for (int i = 0; i < listRides.size(); i++) {
            Ride existingRide = listRides.get(i);
            if (existingRide.getId() == ride.getId()) {
                listRides.set(i, ride);
                break;
            }
        }
    }

    @Override
    public boolean isExist(int rideId) {
        Ride ride = listRides.stream()
                .filter(e -> Objects.equals(e.getId(), rideId))
                .findFirst()
                .orElse(null);
        return ride != null;
    }

    @Override
    public void delete(int rideId) {
        listRides = listRides.stream()
                .filter(e -> !Objects.equals(e.getId(), rideId))
                .collect(Collectors.toList());
    }

    @Override
    public void print() {
        for (Ride ride : listRides) {
            System.out.println(ride.toString());
        }
    }

    public static void updateRideStatus(Ride ride, RideStatus status) {
        ride.setStatus(status);
    }

    @Override
    public Ride getById(int id) {
        return listRides.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void cancelRide(int rideId) {
        getById(rideId).setStatus(RideStatus.CANCELLED);
        updateRideStatus(getById(rideId), RideStatus.CANCELLED);
    }

    public void getRideDetail() {
        System.out.println(CurrentUser.currentRide.toString());
    }


}
