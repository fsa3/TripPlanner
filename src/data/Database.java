package data;

import entities.DayTrip;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import entities.User;

import java.time.LocalDate;
import java.util.ArrayList;

public interface Database {
    ArrayList<Flight> getFlights(String depCity, String destCity, LocalDate date);

    ArrayList<Accommodation> getHotels(String city);

    ArrayList<DayTrip> getDayTrips(String city);

    ArrayList<User> getUsers();
}
