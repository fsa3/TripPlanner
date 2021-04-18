package data;

import dayTripSystem.Trip;
import flightSystem.flightplanner.entities.Airport;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MockDataFactory implements Database{
    @Override
    public ArrayList<Flight> getFlights(String depCity, String destCity, LocalDate date) {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight(69, "FN-6969", new Airport(0, "Rey", "Reykjavík Airport", "Reykjavík"), new Airport(1, "AK", "Akureyri Airport", "Akureyri"), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null, 100));
        return flights;
    }

    @Override
    public ArrayList<Accommodation> getHotels(String city) {
        ArrayList<Accommodation> hotels = new ArrayList<>();
        return hotels;
    }

    @Override
    public ArrayList<Trip> getDayTrips(String city) {
        ArrayList<Trip> dayTrips = new ArrayList<>();
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "Kúrstund at Fannar's Room", null,  false, 0/*todo setja verð*/));
        return dayTrips;
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("ahs33@hi.is", "Ágúst Heiðar", "Sveinbjörnsson", "kallijons", "8975115", "5678"));
        return users;
    }
}
