package entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class HotelBooking extends Booking{
    private Hotel hotel;
    private String hotelName;
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;

    public HotelBooking(Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate) {
        this.hotel = hotel;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        Period p = Period.between(checkInDate, checkOutDate);
        numberOfNights = p.getDays();
        hotelName = hotel.getName();
        city = hotel.getCity();
    }

    public void cancelBooking() {
        // todo
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
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

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }
}
