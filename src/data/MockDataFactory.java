package data;

import entities.DayTrip;
import entities.Flight;
import entities.Hotel;
import entities.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class MockDataFactory implements Database{
    @Override
    public ArrayList<Flight> getFlights(String depCity, String destCity, LocalDate date) {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight("FN-6969", date, depCity, destCity));
        return flights;
    }

    @Override
    public ArrayList<Hotel> getHotels(String city) {
        ArrayList<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel("Hotel OLV", city));
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