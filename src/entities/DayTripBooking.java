package entities;

import java.time.LocalDate;

public class DayTripBooking extends Booking {
    private DayTrip dayTrip;
    private String dayTripName;
    private String city;
    private LocalDate date;
    private int numHours;
    private boolean familyFriendly;

    public DayTripBooking(DayTrip dayTrip, LocalDate date, int id, User user) {
        this.dayTrip = dayTrip;
        this.date = date;
        dayTripName = dayTrip.getName();
        city = dayTrip.getCity();
        numHours = dayTrip.getNumHours();
        familyFriendly = dayTrip.isFamilyFriendly();
        bookingId = id;
        bookingUser = user;
    }

    public DayTripBooking(String name, String city, int numHours, LocalDate date, int id, User user) {
        dayTripName = name;
        this.city = city;
        this.numHours = numHours;
        this.date = date;
        bookingId = id;
        bookingUser = user;
        // todo fylla í dayTrip attribute
        dayTrip = new DayTrip(name, city);
    }

    public void cancelBooking() {
        // todo
    }

    public DayTrip getDayTrip() {
        return dayTrip;
    }

    public void setDayTrip(DayTrip dayTrip) {
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

    public int getNumHours() {
        return numHours;
    }

    public void setNumHours(int numHours) {
        this.numHours = numHours;
    }

    public boolean isFamilyFriendly() {
        return familyFriendly;
    }

    public void setFamilyFriendly(boolean familyFriendly) {
        this.familyFriendly = familyFriendly;
    }

    @Override
    public String toString() {
        return "DayTripBooking{" +
                "dayTrip=" + dayTrip +
                ", dayTripName='" + dayTripName + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", numHours=" + numHours +
                '}';
    }
}
