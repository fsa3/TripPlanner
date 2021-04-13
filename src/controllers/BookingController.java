package controllers;

import data.DataConnection;
import entities.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

public class BookingController {
    private TripPackage tPackage;
    private ArrayList<Booking> bookings;
    private User user;
    private SearchResult searchResult;
    private int bookingId;
    private ArrayList<LocalDate> dayTripDates;

    public BookingController(TripPackage tPackage, User user, SearchResult searchResult) {
        this.tPackage = tPackage;
        this.user = user;
        this.searchResult = searchResult;
        bookingId = Integer.parseInt(String.valueOf(new Date().getTime()).substring(10) + user.getSsNum().substring(5));
        System.out.println(bookingId);
    }

    public BookingController(ArrayList<Booking> bookings, User user) {
        this.bookings = bookings;
        this.user = user;
    }

    public void setDayTripDates(ArrayList<LocalDate> dayTripDates) {
        this.dayTripDates = dayTripDates;
    }

    public void bookPackage() {
        DataConnection dataConnection = new DataConnection();
        dataConnection.createBooking(user, bookingId, tPackage.getPrice(), tPackage.getNumAdults(), tPackage.getNumChildren());
        dataConnection.closeConnection();
        bookings = new ArrayList<>();
        // todo kalla á föll frá hinum hópunum til að bóka
        /*for(Flight f : tPackage.getOutFlights()) {
            FlightBooking flightBooking = new FlightBooking(f);
            bookings.add(flightBooking);
        }
        for(Flight f : tPackage.getInFlights()) {
            FlightBooking flightBooking = new FlightBooking(f);
            bookings.add(flightBooking);
        }
        for(Hotel h : tPackage.getHotels()) {
            HotelBooking hotelBooking = new HotelBooking(h, searchResult.getStartDate(), searchResult.getEndDate());
            bookings.add(hotelBooking);
        }*/
        for(int i = 0; i < tPackage.getDayTrips().toArray().length; i++) {
            DayTripBooking dtBooking = new DayTripBooking(tPackage.getDayTrips().get(i), dayTripDates.get(i), bookingId, user);
            DataConnection dc = new DataConnection();
            dc.createDayTripBooking(dtBooking);
            dc.closeConnection();
            bookings.add(dtBooking);
        }
    }

    public void getPackageFromBookings() {
        // todo
    }
}
