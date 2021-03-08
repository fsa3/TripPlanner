package entities;

import java.util.ArrayList;

public abstract class Booking {
    protected User bookingUser;
    protected String bookingId;
    protected double price;
    protected int numAdults;
    protected int numChildren;
    protected ArrayList<String> adultNames;
    protected ArrayList<String> childrenNames;

    public abstract void cancelBooking();
}
