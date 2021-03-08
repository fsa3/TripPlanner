package main;

import entities.DayTrip;
import entities.Flight;
import entities.Hotel;
import entities.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class DataFactory {
    public DataFactory() {

    }

    public ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight("FN-6969", LocalDate.of(2000, Month.MAY, 20), "Reykjavík", "Paradise City"));
        flights.add(new Flight("FN-5369", LocalDate.of(2000, Month.MAY, 13), "Boggville", "Paradise City"));
        flights.add(new Flight("FN-2979", LocalDate.of(2000, Month.MAY, 14), "Katrínarlind 7", "Saurbær"));
        flights.add(new Flight("FN-6968", LocalDate.of(2000, Month.MAY, 15), "Paradise City", "Mávahraun"));
        flights.add(new Flight("FN-8962", LocalDate.of(2000, Month.MAY, 29), "Saurbær", "Pött Island"));
        flights.add(new Flight("FN-5961", LocalDate.of(2000, Month.MAY, 31), "Pött Island", "Boggville"));
        return flights;
    }

    public ArrayList<Hotel> getHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel("Hotel OLV", "Paradise City"));
        hotels.add(new Hotel("Skítapleis", "Saurbær"));
        hotels.add(new Hotel("Fannar's Room", "Katrínarlind 7"));
        hotels.add(new Hotel("Svallhús Bogga", "Boggville"));
        hotels.add(new Hotel("Hotel Kex", "Pött Island"));
        hotels.add(new Hotel("Eldhus", "Katrínarlind 7"));
        hotels.add(new Hotel("Hótel Svanborg", "Reykjavík"));
        return hotels;
    }

    public ArrayList<DayTrip> getDayTrips() {
        ArrayList<DayTrip> dayTrips = new ArrayList<>();
        dayTrips.add(new DayTrip("Kúrstund at Fannar's Room!", "Katrínarlind 7"));
        dayTrips.add(new DayTrip("Apple picking", "Paradise City"));
        dayTrips.add(new DayTrip("Valorant gaming", "Boggville"));
        dayTrips.add(new DayTrip("Poop Marathon", "Saurbær"));
        dayTrips.add(new DayTrip("Wait in traffic", "Reykjavik"));
        dayTrips.add(new DayTrip("Eat bisquits", "Pött Island"));
        return dayTrips;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("bbm5@hi.is", "Björn Borgar", "Magnússon", "samur", "5540817", "1234"));
        users.add(new User("ahs33@hi.is", "Ágúst Heiðar", "Sveinbjörnsson", "nanna", "8975115", "5678"));
        return users;
    }
}
