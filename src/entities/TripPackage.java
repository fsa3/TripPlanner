package entities;

import java.util.Date;

public class TripPackage extends SearchResult{
    private String name;
    private double price;
    private SearchResult masterSearch;
    private boolean customPackage = true;

    public TripPackage(String name, SearchResult search) {
        super(search.getStartDate(), search.getEndDate(), search.getDepCity(), search.getDestCity(), search.getNumAdults(), search.getNumChildren());
        this.name = name;
        masterSearch = search;
    }

    public void addFlight(Flight f) {
        flights.add(f);
    }

    public void removeFlight(Flight f) {
        flights.remove(flights.indexOf(f));
    }

    public void addHotel(Hotel h) {
        hotels.add(h);
    }

    public void removeHotel(Hotel h) {
        hotels.remove(hotels.indexOf(h));
    }

    public void addDayTrip(DayTrip dt) {
        dayTrips.add(dt);
    }

    public void removeDayTrip(DayTrip dt) {
        dayTrips.remove(dayTrips.indexOf(dt));
    }

    public boolean isCustom() {
        return customPackage;
    }

    public void changeToCustom() {
        if(!customPackage) customPackage = true;
    }

    public void bestValue() {
        // todo
    }

    public void highEnd() {
        // todo
    }
}
