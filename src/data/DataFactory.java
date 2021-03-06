package data;

import dayTripSystem.Trip;
import flightSystem.flightplanner.entities.Airport;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataFactory implements Database {
    public DataFactory() {

    }

    @Override
    public ArrayList<Flight> getFlights(String depCity, String destCity, LocalDate date) {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight(69, "FN-6969", new Airport(0, "flugv", depCity+" Airport", depCity), new Airport(1, "flugv", destCity+" Airport", destCity), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null, 100));
        flights.add(new Flight(70, "FN-6965", new Airport(0, "flugv", depCity+" Airport", depCity), new Airport(1, "flugv", destCity+" Airport", destCity), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null, 100));
        flights.add(new Flight(71, "FN-6513", new Airport(0, "flugv", depCity+" Airport", depCity), new Airport(1, "flugv", destCity+" Airport", destCity), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null, 100));
        flights.add(new Flight(72, "FN-6513", new Airport(0, "flugv", depCity+" Airport", depCity), new Airport(1, "flugv", destCity+" Airport", destCity), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null, 100));
        flights.add(new Flight(73, "FN-0015", new Airport(0, "flugv", depCity+" Airport", depCity), new Airport(1, "flugv", destCity+" Airport", destCity), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null, 100));
        flights.add(new Flight(74, "FN-8462", new Airport(0, "flugv", depCity+" Airport", depCity), new Airport(1, "flugv", destCity+" Airport", destCity), LocalDateTime.of(2020, 3, 18, 7, 36), LocalDateTime.of(2020, 3, 18, 10, 36), null, 100));
        return flights;
    }

    @Override
    public ArrayList<Accommodation> getHotels(String city) {
        ArrayList<Accommodation> hotels = new ArrayList<>();
        hotels.add(new Accommodation(0, "Hotel OLV", city, 3, null, null, null, "description"));
        hotels.add(new Accommodation(1, "Sk??tapleis", city, 3, null, null, null, "description"));
        hotels.add(new Accommodation(2, "Fannar's Room", city, 3, null, null, null, "description"));
        hotels.add(new Accommodation(3, "Svallh??s Bogga", city, 3, null, null, null, "description"));
        hotels.add(new Accommodation(4, "Hotel Kex", city, 3, null, null, null, "description"));
        hotels.add(new Accommodation(5, "Eldhus", city,3, null, null, null, "description"));
        hotels.add(new Accommodation(6, "H??tel Svanborg", city,3, null, null, null, "description"));
        return hotels;
    }

    @Override
    public ArrayList<Trip> getDayTrips(String city) {
        ArrayList<Trip> dayTrips = new ArrayList<>();
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "K??rstund at Fannar's Room", null,  false, 0));
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "Apple picking", null,  false, 0));
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "Apple picking", null,  false, 0));
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "Valorant gaming", null,  false, 0));
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "Poop Marathon", null,  false, 0));
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "Wait in traffic", null,  false, 0));
        dayTrips.add(new Trip("-1", city, DataConnection.localDateToDate(LocalDate.of(2021,3,26)), "", 30, 0, false, "Eat bisquits", null,  false, 0));
        return dayTrips;
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("bbm5@hi.is", "Bj??rn Borgar", "Magn??sson", "samur", "5540817", "1234", -1));
        users.add(new User("ahs33@hi.is", "??g??st Hei??ar", "Sveinbj??rnsson", "kallijons", "8975115", "5678", -1));
        return users;
    }
}
