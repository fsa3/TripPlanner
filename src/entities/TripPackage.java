package entities;

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
    }

    public void highEnd() {
        // todo
    }
}
