package entities;

import java.util.ArrayList;
import java.util.Date;

public class SearchResult {
    protected Date startDate;
    protected Date endDate;
    protected String depCity;
    protected String destCity;
    protected int numAdults;
    protected int numChildren;
    protected ArrayList<Flight> flights;
    protected ArrayList<Hotel> hotels;
    protected ArrayList<DayTrip> dayTrips;

    public SearchResult(Date startDate, Date endDate, String depCity, String destCity, int numAdults, int numChildren) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.depCity = depCity;
        this.destCity = destCity;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public void search() {
        // todo
    }
}
