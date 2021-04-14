package data;

import entities.DayTrip;
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
        flights.add(new Flight(69, "FN-6969", new Airport(0, "Rey", "Reykjavík Airport", "Reykjavík"), new Airport(1, "AK", "Akureyri Airport", "Akureyri"), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null));
        return flights;
    }

    @Override
    public ArrayList<Accommodation> getHotels(String city) {
        ArrayList<Accommodation> hotels = new ArrayList<>();
        return hotels;
    }

    @Override
    public ArrayList<DayTrip> getDayTrips(String city) {
        ArrayList<DayTrip> dayTrips = new ArrayList<>();
        dayTrips.add(new DayTrip("Kúrstund at Fannar's Room!", city));
        return dayTrips;
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("ahs33@hi.is", "Ágúst Heiðar", "Sveinbjörnsson", "kallijons", "8975115", "5678"));
        return users;
    }
}
