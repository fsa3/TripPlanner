package entities;

import java.util.ArrayList;

public abstract class Booking {
    protected User bookingUser;
    protected int bookingId;
    protected double price;
    protected int numAdults;
    protected int numChildren;
    protected String passengerName;
    protected boolean isChild;

    public int getBookingId() {
        return bookingId;
    }

    public User getBookingUser() {
        return bookingUser;
    }
}
