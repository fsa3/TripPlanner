package entities;

import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;

import java.time.LocalDate;
import java.time.Period;

public class HotelBooking extends Booking{
    private Accommodation hotel;
    private String hotelName;
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;
    private Room room;

    public HotelBooking(Accommodation hotel, LocalDate checkInDate, LocalDate checkOutDate, int bookingId, User user, Room room) {
        this.hotel = hotel;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        Period p = Period.between(checkInDate, checkOutDate);
        numberOfNights = p.getDays();
        hotelName = hotel.getName();
        city = hotel.getLocation();
        this.bookingId = bookingId;
        bookingUser = user;
        this.room = room;
    }

    public HotelBooking(String hotelName, LocalDate checkInDate, LocalDate checkOutDate, int bookingId, User user, Room room, String city) {
        this.hotelName = hotelName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        Period p = Period.between(checkInDate, checkOutDate);
        numberOfNights = p.getDays();
        this.city = city;
        this.bookingId = bookingId;
        bookingUser = user;
        this.room = room;
    }

    public void cancelBooking() {
        // todo
    }

    public Accommodation getHotel() {
        return hotel;
    }

    public void setHotel(Accommodation hotel) {
        this.hotel = hotel;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
