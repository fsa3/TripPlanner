package controllers;

import entities.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class BookingController {
    private TripPackage tPackage;
    private ArrayList<Booking> bookings;
    private User user;
    private SearchResult searchResult;

    public BookingController(TripPackage tPackage, User user, SearchResult searchResult) {
        this.tPackage = tPackage;
        this.user = user;
        this.searchResult = searchResult;
    }

    public BookingController(ArrayList<Booking> bookings, User user) {
        this.bookings = bookings;
        this.user = user;
    }

    public void bookPackage() {
        bookings = new ArrayList<>();
        // todo kalla á föll frá hinum hópunum til að bóka
        for(Flight f : tPackage.getOutFlights()) {
            FlightBooking flightBooking = new FlightBooking(f);
            bookings.add(flightBooking);
        }
        for(Flight f : tPackage.getInFlights()) {
            FlightBooking flightBooking = new FlightBooking(f);
            bookings.add(flightBooking);
        }/*
        for(Hotel h : tPackage.getHotels()) {
            HotelBooking hotelBooking = new HotelBooking(h, searchResult.getStartDate(), searchResult.getEndDate());
            bookings.add(hotelBooking);
        }
        for(DayTrip dt : tPackage.getDayTrips()) {
            DayTripBooking dayTripBooking = new DayTripBooking(dt, LocalDate.of(2020, new Month(3), ))
        }
        */
    }

    public void getPackageFromBookings() {
        // todo
    }
}
