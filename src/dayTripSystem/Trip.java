package dayTripSystem;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Objects;

public class Trip {
    private String tripID;
    private String destination;
    //private String dateAndTime;
    private Date date;
    private String hostEmail;
    private int maxReservations;
    private int minReservations;
    private boolean isFullyBooked;
    private String category;
    private BufferedImage schedule;
    public int capacity;
    private boolean canceledTrip;
    private int price;


    public Trip(String tripID, String destination, Date date, String hostEmail,
                int maxReservations, int minReservations, boolean isFullyBooked,
                String category, BufferedImage schedule,
                boolean canceledTrip, int price) {
        this.tripID = tripID;
        this.destination = destination;
        this.date = date;
        this.hostEmail = hostEmail;
        this.maxReservations = maxReservations;
        this.minReservations = minReservations;
        this.isFullyBooked = isFullyBooked;
        this.category = category;
        this.schedule = schedule;
        this.capacity = maxReservations;
        this.canceledTrip = canceledTrip;
        this.price = price;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public int getMaxReservations() {
        return maxReservations;
    }

    public void setMaxReservations(int maxReservations) {
        this.maxReservations = maxReservations;
    }

    public int getMinReservations() {
        return minReservations;
    }

    public void setMinReservations(int minReservations) {
        this.minReservations = minReservations;
    }

    public boolean getIsFullyBooked() {
        return isFullyBooked == true;
    }

    public void setFullyBooked(boolean fullyBooked) {
        isFullyBooked = fullyBooked;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BufferedImage getSchedule() {
        return schedule;
    }

    public void setSchedule(BufferedImage schedule) {
        this.schedule = schedule;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        if (this.capacity <= 0) {
            isFullyBooked = true;
        }
    }

    public boolean isCanceledTrip() {
        return canceledTrip;
    }

    public void cancelTrip() {
        canceledTrip = true;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;
        Trip trip = (Trip) o;
        return getMaxReservations() == trip.getMaxReservations() &&
                getMinReservations() == trip.getMinReservations() &&
                isFullyBooked == trip.isFullyBooked &&
                getCapacity() == trip.getCapacity() &&
                isCanceledTrip() == trip.isCanceledTrip() &&
                getPrice() == trip.getPrice() &&
                Objects.equals(getTripID(), trip.getTripID()) &&
                Objects.equals(getDestination(), trip.getDestination()) &&
                Objects.equals(getDate(), trip.getDate()) &&
                Objects.equals(getHostEmail(), trip.getHostEmail()) &&
                Objects.equals(getCategory(), trip.getCategory()) &&
                Objects.equals(getSchedule(), trip.getSchedule());
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripID='" + tripID + '\'' +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                ", hostEmail='" + hostEmail + '\'' +
                ", maxReservations=" + maxReservations +
                ", minReservations=" + minReservations +
                ", isFullyBooked=" + isFullyBooked +
                ", category='" + category + '\'' +
                ", schedule=" + schedule +
                ", capacity=" + capacity +
                ", canceledTrip=" + canceledTrip +
                ", price=" + price +
                '}';
    }
}
