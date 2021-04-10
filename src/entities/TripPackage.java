package entities;

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

    public void addHotel(Hotel h) {
        hotels.add(h);
    }

    public void removeHotel(Hotel h) {
        hotels.remove(h);
    }

    public void addDayTrip(DayTrip dt) {
        dayTrips.add(dt);
    }

    public void removeDayTrip(DayTrip dt) {
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
}
