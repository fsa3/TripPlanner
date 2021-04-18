package controllers;

import data.DataConnection;
import entities.*;
import flightSystem.flightplanner.entities.Passenger;
import hotelSystem.controllers.AccommodationBookingController;
import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BookingController {
    private TripPackage tPackage;
    private ArrayList<Booking> bookings;
    private User user;
    private SearchResult searchResult;
    private int bookingId;
    private ArrayList<Passenger> passengers;

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
        }*/
        if(!tPackage.getHotels().isEmpty()) {
            Accommodation h = tPackage.getHotels().get(0);
            for (Room r : tPackage.getRooms()) {
                HotelBooking hotelBooking = new HotelBooking(h, tPackage.getStartDate(), tPackage.getEndDate(), bookingId, user, r);
                hotelSystem.entities.Booking hotelSystemBooking = new hotelSystem.entities.Booking(h, r, DataConnection.localDateToDate(tPackage.getStartDate()), DataConnection.localDateToDate(tPackage.getEndDate()));
                AccommodationBookingController acBookingController = new AccommodationBookingController();
                acBookingController.addBooking(hotelSystemBooking);
                DataConnection dc = new DataConnection();
                dc.createHotelBooking(hotelBooking);
                dc.closeConnection();
                bookings.add(hotelBooking);
            }
        }
        for(int i = 0; i < tPackage.getDayTrips().toArray().length; i++) {
            /*DayTripBooking dtBooking = new DayTripBooking(tPackage.getDayTrips().get(i), dayTripDates.get(i), bookingId, user);
            DataConnection dc = new DataConnection();
            dc.createDayTripBooking(dtBooking);
            dc.closeConnection();
            bookings.add(dtBooking);*/
        }
    }

    public void getPackageFromBookings() {
        // todo
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }
}
