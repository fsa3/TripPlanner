package hotelSystem.controllers;


import hotelSystem.entities.Booking;
import hotelSystem.entities.Occupancy;
import hotelSystem.entities.Room;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class AccommodationBookingController {
    public AccommodationBookingController() {

    }

    public void addBooking(Booking booking) {
        Date from = booking.getBookingDateFrom();
        Date to = booking.getBookingDateTo();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        
        if (to.after(from) && !fmt.format(from).equals(fmt.format(to))) {
            Occupancy occupancy = new Occupancy(booking.getBookingDateFrom(), booking.getBookingDateTo());
            booking.getRoom().addOccupancy(occupancy);
            System.out.println("Room booked");
        } else {
            System.out.println("Illegal booking dates");
        }

    }

    public void removeBooking(Booking booking) {
        Date from = booking.getBookingDateFrom();
        Date to = booking.getBookingDateTo();
        Room room = booking.getRoom();

        room.removeOccupancy(room.getRoomId(), from, to);
    }

    public void changeBooking(Booking booking) {

    }
}
