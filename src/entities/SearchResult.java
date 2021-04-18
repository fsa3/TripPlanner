package entities;

import data.DataConnection;
import dayTripSystem.SearchController;
import dayTripSystem.Trip;
import flightSystem.flightplanner.controllers.FlightSearchController;
import flightSystem.flightplanner.data.FlDataConnection;
import flightSystem.flightplanner.entities.Airport;
import flightSystem.flightplanner.entities.Flight;
import flightSystem.flightplanner.entities.Seat;
import hotelSystem.controllers.AccommodationSearchController;
import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;
import hotelSystem.storage.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import data.DataFactory;
import data.Database;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchResult {
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String depCity;
    protected String destCity;
    protected int numAdults;
    protected int numChildren;
    protected ArrayList<Flight> outFlights;
    protected ArrayList<Flight> inFlights;
    protected ArrayList<Accommodation> hotels;
    protected ArrayList<Room> rooms;
    protected ArrayList<Trip> dayTrips;

    public SearchResult(LocalDate startDate, LocalDate endDate, String depCity, String destCity, int numAdults, int numChildren) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.depCity = depCity;
        this.destCity = destCity;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public ArrayList<Flight> getOutFlights() {
        return outFlights;
    }

    public void setOutFlights(ArrayList<Flight> outFlights) {
        this.outFlights = outFlights;
    }

    public ArrayList<Flight> getInFlights() {
        return inFlights;
    }

    public void setInFlights(ArrayList<Flight> inFlights) {
        this.inFlights = inFlights;
    }

    public ArrayList<Accommodation> getHotels() {
        return hotels;
    }

    public void setHotels(ArrayList<Accommodation> hotels) {
        this.hotels = hotels;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Trip> getDayTrips() {
        return dayTrips;
    }

    public void setDayTrips(ArrayList<Trip> dayTrips) {
        this.dayTrips = dayTrips;
    }

    public ObservableList<Flight> getFlightsObservable() {
        ObservableList<Flight> oFlights = FXCollections.observableArrayList();
        oFlights.addAll(inFlights);
        oFlights.addAll(outFlights);
        return oFlights;
    }

    public ObservableList<Accommodation> getHotelsObservable() {
        ObservableList<Accommodation> oHotels = FXCollections.observableArrayList();
        oHotels.addAll(hotels);
        return oHotels;
    }
    public ObservableList<Trip> getDayTripsObservable() {
        ObservableList<Trip> oDayTrips = FXCollections.observableArrayList();
        oDayTrips.addAll(dayTrips);
        return oDayTrips;
    }

    public void search() throws Exception {
        // todo
        Database dataFactory = new DataFactory();

        // Find flights
        outFlights = new ArrayList<>();
        inFlights = new ArrayList<>();

        FlightSearchController flightSearchController = FlightSearchController.getInstance();
        ArrayList<Airport> departureAirports = flightSearchController.getAirportByCity(depCity);
        ArrayList<Airport> destinationAirports = flightSearchController.getAirportByCity(destCity);

        for(Airport departureAirport : departureAirports) {
            for(Airport destinationAirport : destinationAirports) {
                outFlights.addAll(flightSearchController.searchFlightsByFilter(departureAirport, destinationAirport, startDate, startDate.plusDays(1)));
                inFlights.addAll(flightSearchController.searchFlightsByFilter(destinationAirport, departureAirport, endDate, endDate.plusDays(1)));
            }
        }

        // Find hotels
        DatabaseConnection hotelData = new DatabaseConnection();
        AccommodationSearchController acSearchController = new AccommodationSearchController(hotelData);
        hotels = acSearchController.search(destCity, 0, "");
        rooms = new ArrayList<Room>();
        ArrayList<Accommodation> unavailableHotels = new ArrayList<>();
        for(Accommodation h : hotels) {
            ArrayList<Room> availableRooms = h.getAvailableRooms(DataConnection.localDateToDate(startDate), DataConnection.localDateToDate(endDate));
            int availableCapacity = 0;
            for(Room room : availableRooms) {
                availableCapacity += room.getCap();
            }
            if(availableRooms.isEmpty() || availableCapacity < numAdults+numChildren) {
                unavailableHotels.add(h);
            }
            else {
                rooms.addAll(availableRooms);
            }
        }
        hotels.removeAll(unavailableHotels);

        System.out.println(startDate);
        System.out.println(endDate);
        // Find day trips
        dayTrips = new ArrayList<>();
        for(LocalDate date : getDateRange(startDate, endDate)) {
            SearchController dayTripSearchController = new SearchController();
            ObservableList<Trip> trips = dayTripSearchController.getTripsByDestinationAndDate(destCity, DataConnection.localDateToUtilDate(date));
            dayTrips.addAll(trips);
        }

    }

    private ArrayList<LocalDate> getDateRange(LocalDate start, LocalDate end) {
        ArrayList<LocalDate> ret = new ArrayList<>();
        LocalDate tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Flight> outFlights = new ArrayList<>();
        ArrayList<Flight> inFlights = new ArrayList<>();
        ArrayList<Trip> trips = new ArrayList<>();

        String depCity = "Akureyri";
        String destCity = "Reykjavík";

        LocalDate startDate = LocalDate.of(2021, 5, 6);
        LocalDate endDate = LocalDate.of(2021, 5, 8);

        FlightSearchController flightSearchController = FlightSearchController.getInstance();
        ArrayList<Airport> departureAirports = flightSearchController.getAirportByCity(depCity);
        ArrayList<Airport> destinationAirports = flightSearchController.getAirportByCity(destCity);

        for(Airport departureAirport : departureAirports) {
            for(Airport destinationAirport : destinationAirports) {
                outFlights.addAll(flightSearchController.searchFlightsByFilter(departureAirport, destinationAirport, startDate, startDate.plusDays(1)));
                inFlights.addAll(flightSearchController.searchFlightsByFilter(destinationAirport, departureAirport, endDate, endDate.plusDays(1)));
            }
        }

        dayTripSystem.Database db = dayTripSystem.Database.getInstance();
        db.generateData();
        SearchController tripsSearchController = new SearchController();
        SearchResult searchResult = new SearchResult(null, null, null, null, 0, 0);
        for(LocalDate date : searchResult.getDateRange(startDate, endDate)) {
            ObservableList<Trip> tripsOnDay = tripsSearchController.getTripsByDestinationAndDate(destCity, DataConnection.localDateToUtilDate(date));
            trips.addAll(tripsOnDay);
        }

        System.out.println("departure airports");
        for(Airport a : departureAirports) {
            System.out.println(a.getFullName());
        }
        System.out.println("destination airport");
        for(Airport a : destinationAirports) {
            System.out.println(a.getFullName());
        }
        System.out.println("out flights");
        for(Flight f : outFlights) {
            System.out.println(f);
        }
        System.out.println("in flights");
        for(Flight f : inFlights) {
            System.out.println(f);
        }
        System.out.println("Day Trips");
        for(Trip t : trips) {
            System.out.println(t.toString());
        }

    }

}
