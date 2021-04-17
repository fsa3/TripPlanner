package dayTripSystem;

import java.awt.image.BufferedImage;
import java.util.Date;

public class TripController {
    private Booking booking;
    private Trip trip;

    public TripController(Trip trip, Booking booking) {
        this.trip = trip;
        this.booking = booking;

    }

    public Trip cancelTrip() {
        if (booking.getNmbRes() <= trip.getMinReservations()) {
            trip.cancelTrip();
            System.out.println("Trip is canceled");
        }
        return trip;
    }

    // Geyma aðeins
    public Trip createTrip(String tripID, String destination, Date date, String hostEmail, int maxReservations, int minReservations, boolean isFullyBooked, String category, BufferedImage schedule, int capacity, boolean canceledTrip, int price) {
        trip = new Trip(tripID, destination, date, hostEmail, maxReservations, minReservations, isFullyBooked, category, schedule, capacity, canceledTrip, price);
        return trip;
    }

}






