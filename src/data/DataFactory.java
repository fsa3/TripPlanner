package data;

import entities.DayTrip;
import entities.Flight;
import entities.Hotel;
import entities.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataFactory implements Database {
    public DataFactory() {

    }

    @Override
    public ArrayList<Flight> getFlights(String depCity, String destCity, LocalDate date) {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight("FN-6969", date, depCity, destCity));
        flights.add(new Flight("FN-5369", date, depCity, destCity));
        flights.add(new Flight("FN-2979", date, depCity, destCity));
        flights.add(new Flight("FN-6968", date, depCity, destCity));
        flights.add(new Flight("FN-8962", date, depCity, destCity));
        flights.add(new Flight("FN-5961", date, depCity, destCity));
        return flights;
    }

    @Override
    public ArrayList<Hotel> getHotels(String city) {
        ArrayList<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel("Hotel OLV", city));
        hotels.add(new Hotel("Skítapleis", city));
        hotels.add(new Hotel("Fannar's Room", city));
        hotels.add(new Hotel("Svallhús Bogga", city));
        hotels.add(new Hotel("Hotel Kex", city));
        hotels.add(new Hotel("Eldhus", city));
        hotels.add(new Hotel("Hótel Svanborg", city));
        return hotels;
    }

    @Override
    public ArrayList<DayTrip> getDayTrips(String city) {
        ArrayList<DayTrip> dayTrips = new ArrayList<>();
        dayTrips.add(new DayTrip("Kúrstund at Fannar's Room!", city));
        dayTrips.add(new DayTrip("Apple picking", city));
        dayTrips.add(new DayTrip("Valorant gaming", city));
        dayTrips.add(new DayTrip("Poop Marathon", city));
        dayTrips.add(new DayTrip("Wait in traffic", city));
        dayTrips.add(new DayTrip("Eat bisquits", city));
        return dayTrips;
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("bbm5@hi.is", "Björn Borgar", "Magnússon", "samur", "5540817", "1234"));
        users.add(new User("ahs33@hi.is", "Ágúst Heiðar", "Sveinbjörnsson", "kallijons", "8975115", "5678"));
        return users;
    }
}
