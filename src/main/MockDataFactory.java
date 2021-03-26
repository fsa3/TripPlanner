package main;

import entities.DayTrip;
import entities.Flight;
import entities.Hotel;
import entities.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class MockDataFactory implements Database{
    @Override
    public ArrayList<Flight> getFlights(String depCity, String destCity, LocalDate date) {
        return null;
    }

    @Override
    public ArrayList<Hotel> getHotels(String city) {
        return null;
    }

    @Override
    public ArrayList<DayTrip> getDayTrips(String city) {
        return null;
    }

    @Override
    public ArrayList<User> getUsers() {
        return null;
    }
}
