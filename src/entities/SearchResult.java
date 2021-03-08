package entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DataFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchResult {
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String depCity;
    protected String destCity;
    protected int numAdults;
    protected int numChildren;
    protected ArrayList<Flight> flights;
    protected ArrayList<Hotel> hotels;
    protected ArrayList<DayTrip> dayTrips;

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

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public ArrayList<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    public ArrayList<DayTrip> getDayTrips() {
        return dayTrips;
    }

    public void setDayTrips(ArrayList<DayTrip> dayTrips) {
        this.dayTrips = dayTrips;
    }

    public ObservableList<Flight> getFlightsObservable() {
        ObservableList<Flight> oFlights = FXCollections.observableArrayList();
        oFlights.addAll(flights);
        return oFlights;
    }

    public void search() {
        // todo
        DataFactory dataFactory = new DataFactory();
        flights = dataFactory.getFlights();
        hotels = dataFactory.getHotels();
        dayTrips = dataFactory.getDayTrips();
    }
}
