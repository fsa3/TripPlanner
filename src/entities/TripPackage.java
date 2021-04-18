package entities;

import data.DataConnection;
import dayTripSystem.Trip;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class TripPackage extends SearchResult{
    private String name;
    private double price;
    private SearchResult masterSearch;
    private boolean customPackage = true;

    public TripPackage(String name, SearchResult search) {
        super(search.getStartDate(), search.getEndDate(), search.getDepCity(), search.getDestCity(), search.getNumAdults(), search.getNumChildren());
        this.name = name;
        masterSearch = search;
        outFlights = new ArrayList<>();
        inFlights = new ArrayList<>();
        hotels = new ArrayList<>();
        dayTrips = new ArrayList<>();
        rooms = new ArrayList<>();
        /*
        outFlights.addAll(masterSearch.getOutFlights());
        inFlights.addAll(masterSearch.getInFlights());
        hotels.addAll(masterSearch.getHotels());
        dayTrips.addAll(masterSearch.getDayTrips());
         */
    }

    public void addInFlight(Flight f) {
        inFlights.add(f);
    }

    public void removeInFlight(Flight f) {
        inFlights.remove(f);
    }

    public void addOutFlight(Flight f) {
        outFlights.add(f);
    }

    public void removeOutFlight(Flight f) {
        outFlights.remove(f);
    }

    public void addHotel(Accommodation h) {
        hotels.add(h);
    }

    public void removeHotel(Accommodation h) {
        hotels.remove(h);
    }

    public void addRoom(Room r) {
        rooms.add(r);
    }

    public void removeRoom(Room r) {
        rooms.remove(r);
    }

    public void addDayTrip(Trip dt) {
        dayTrips.add(dt);
    }

    public void removeDayTrip(Trip dt) {
        dayTrips.remove(dt);
    }

    public void removeAllOutFlights() {
        outFlights.clear();
    }

    public void removeAllInFlights() {
        inFlights.clear();
    }

    public void removeAllHotels() {
        hotels.clear();
    }

    public void removeAllDayTrips() {
        dayTrips.clear();
    }

    public boolean isCustom() {
        return customPackage;
    }

    public void changeToCustom() {
        if(!customPackage) customPackage = true;
    }

    public void bestValue() {
        // todo
        inFlights = masterSearch.inFlights;
        outFlights = masterSearch.outFlights;
        hotels = masterSearch.hotels;
        dayTrips = masterSearch.dayTrips;
        price = 45.69;
    }

    public void highEnd() {
        // todo
    }

    public void testPackage() {
        if(!masterSearch.getOutFlights().isEmpty()) outFlights.add(masterSearch.getOutFlights().get(0));
        if(!masterSearch.getInFlights().isEmpty()) inFlights.add(masterSearch.getInFlights().get(0));
        if(!masterSearch.getHotels().isEmpty()) {
            hotels.add(masterSearch.getHotels().get(0));
            ArrayList<Room> availableRooms = hotels.get(0).getAvailableRooms(DataConnection.localDateToDate(startDate), DataConnection.localDateToDate(endDate));
            if(!availableRooms.isEmpty()) rooms.add(availableRooms.get(0));
        }
        dayTrips.addAll(masterSearch.getDayTrips());
        calculatePrice();
    }

    public void emptyPackage() {
        outFlights.clear();
        inFlights.clear();
        hotels.clear();
        dayTrips.clear();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void clearRooms() {
        rooms.clear();
    }

    public void calculatePrice() {
        int p = 0;
        for(Flight f : outFlights) {
            // todo sækja verð á flugi
        }
        for(Flight f : inFlights) {
            // todo sækja verð á flugi
        }
        for(Room r : rooms) {
            p += r.getPrice();
        }
        for(Trip t : dayTrips) {
            p += t.getPrice();
        }
        price = p;
    }

    @Override
    public String toString() {
        return "TripPackage{" +
                "outFlights=" + outFlights +
                ", inFlights=" + inFlights +
                ", hotels=" + hotels +
                ", rooms=" + rooms +
                ", dayTrips=" + dayTrips +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", customPackage=" + customPackage +
                '}';
    }
}
