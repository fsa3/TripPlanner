package entities;

import data.DataConnection;
import dayTripSystem.Trip;

import java.time.LocalDate;

public class DayTripBooking extends Booking {
    private Trip dayTrip;
    private String dayTripName;
    private String city;
    private LocalDate date;

    public DayTripBooking(Trip dayTrip, LocalDate date, int id, User user) {
        this.dayTrip = dayTrip;
        this.date = date;
        dayTripName = dayTrip.getCategory();
        city = dayTrip.getDestination();
        bookingId = id;
        bookingUser = user;
    }

    public DayTripBooking(String name, String city, LocalDate date, int id, User user) {
        dayTripName = name;
        this.city = city;
        this.date = date;
        bookingId = id;
        bookingUser = user;
        // todo fylla í dayTrip attribute
        dayTrip = new Trip("-1", city, DataConnection.localDateToDate(date), "", 30, 0, false, name, null,  false, 0/*todo setja verð*/);
    }

    public void cancelBooking() {
        // todo
    }

    public Trip getDayTrip() {
        return dayTrip;
    }

    public void setDayTrip(Trip dayTrip) {
        this.dayTrip = dayTrip;
    }

    public String getDayTripName() {
        return dayTripName;
    }

    public void setDayTripName(String dayTripName) {
        this.dayTripName = dayTripName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DayTripBooking{" +
                "dayTrip=" + dayTrip +
                ", dayTripName='" + dayTripName + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                '}';
    }
}
