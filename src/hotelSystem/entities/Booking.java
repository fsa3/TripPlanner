package hotelSystem.entities;

import entities.User;

import java.util.ArrayList;

public class Booking {
    private User user;  //vonandi virkar thetta
    private ArrayList<String> guests; //Eigum við að hafa þetta sem arraylist?
    private Accommodation accommodation;
    private Room room;
    private String id;
    private java.sql.Date bookingDateFrom; //Hvernig ætlum við að geyma þetta?
    private java.sql.Date bookingDateTo;

    public Booking(User user, ArrayList<String> guests, Accommodation accommodation, Room room, String id, java.sql.Date bookingDateFrom, java.sql.Date bookingDateTo) {
        this.user = user;
        this.guests = guests;
        this.accommodation = accommodation;
        this.room = room;
        this.id = id;
        this.bookingDateFrom = bookingDateFrom;
        this.bookingDateTo = bookingDateTo;
    }

    public Booking(Accommodation accommodation, Room room, java.sql.Date bookingDateFrom, java.sql.Date bookingDateTo){
        this.accommodation = accommodation;
        this.room = room;
        this.bookingDateFrom = bookingDateFrom;
        this.bookingDateTo = bookingDateTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<String> guests) {
        this.guests = guests;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public java.sql.Date getBookingDateFrom() {
        return bookingDateFrom;
    }

    public void setBookingDateFrom(java.sql.Date bookingDateFrom) {
        this.bookingDateFrom = bookingDateFrom;
    }

    public java.sql.Date getBookingDateTo() {
        return bookingDateTo;
    }

    public void setBookingDateTo(java.sql.Date bookingDateTo) {
        this.bookingDateTo = bookingDateTo;
    }
}
