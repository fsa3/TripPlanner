package main;

import entities.DayTrip;
import entities.Flight;
import entities.Hotel;
import entities.User;

import java.time.LocalDate;
import java.util.ArrayList;

public interface Database {
    ArrayList<Flight> getFlights(String depCity, String destCity, LocalDate date);

    ArrayList<Hotel> getHotels(String city);

    ArrayList<DayTrip> getDayTrips(String city);

    ArrayList<User> getUsers();
}
