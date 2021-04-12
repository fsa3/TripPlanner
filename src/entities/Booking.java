package entities;

import java.util.ArrayList;

public abstract class Booking {
    protected User bookingUser;
    protected String bookingId;
    protected double price;
    protected int numAdults;
    protected int numChildren;
    protected String passengerName;
    protected boolean isChild;

    public abstract void cancelBooking();
}
