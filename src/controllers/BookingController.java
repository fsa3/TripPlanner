package controllers;

import data.DataConnection;
import dayTripSystem.Account;
import dayTripSystem.PaymentInfo;
import dayTripSystem.Trip;
import entities.*;
import flightSystem.flightplanner.controllers.FlightBookingController;
import flightSystem.flightplanner.controllers.FlightSearchController;
import flightSystem.flightplanner.entities.Flight;
import flightSystem.flightplanner.entities.Info;
import flightSystem.flightplanner.entities.Passenger;
import flightSystem.flightplanner.entities.Seat;
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
    private PaymentInfo paymentInfo;
    private ArrayList<Passenger> passengers;
    private ArrayList<Seat> outSeats;
    private ArrayList<Seat> inSeats;

    public BookingController(TripPackage tPackage, User user, SearchResult searchResult, PaymentInfo paymentInfo) {
        this.tPackage = tPackage;
        this.user = user;
        this.searchResult = searchResult;
        this.paymentInfo = paymentInfo;
        bookingId = Integer.parseInt(String.valueOf(new Date().getTime()).substring(10) + user.getSsNum().substring(5));
        System.out.println(bookingId);
    }

    public BookingController(ArrayList<Booking> bookings, User user) {
        this.bookings = bookings;
        this.user = user;
    }

    public void bookPackage() {
        DataConnection dataConnection = new DataConnection();
        int flightOutId = -1;
        int flightInId = -1;
        if(!tPackage.getOutFlights().isEmpty()) flightOutId = tPackage.getOutFlights().get(0).getID();
        if(!tPackage.getInFlights().isEmpty()) flightInId = tPackage.getInFlights().get(0).getID();
        dataConnection.createBooking(user, bookingId, tPackage.getPrice(), tPackage.getNumAdults(), tPackage.getNumChildren());
        bookings = new ArrayList<>();
        // todo kalla á föll frá hinum hópunum til að bóka

        // Book flights
        FlightBookingController flightBookingController = FlightBookingController.getInstance();
        Info information = Info.getInstance();
        flightSystem.flightplanner.entities.User flightUser = new flightSystem.flightplanner.entities.User("user", user.getId(), user.getFirstName(), user.getLastName(), user.getSsNum(), user.getEmail(), user.getPhoneNumber());
        information.setUser(flightUser);
        for(Flight f : tPackage.getOutFlights()) {
            information.setFlight(f);
            for(int i = 0; i < passengers.size(); i++) {
                information.setCurrentPassenger(passengers.get(i));
                information.setSeat(outSeats.get(i));
                try {
                    int flightBookingId = flightBookingController.createBooking();
                    FlightBooking flightBooking = new FlightBooking(bookingId, flightBookingId);
                    dataConnection.createFlightBooking(flightBooking);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for(Flight f : tPackage.getInFlights()) {
            information.setFlight(f);
            for(int i = 0; i < passengers.size(); i++) {
                information.setCurrentPassenger(passengers.get(i));
                information.setSeat(inSeats.get(i));
                try {
                    int flightBookingId = flightBookingController.createBooking();
                    FlightBooking flightBooking = new FlightBooking(bookingId, flightBookingId);
                    dataConnection.createFlightBooking(flightBooking);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



        // Book hotel rooms
        if(!tPackage.getHotels().isEmpty()) {
            Accommodation h = tPackage.getHotels().get(0);
            for (Room r : tPackage.getRooms()) {
                HotelBooking hotelBooking = new HotelBooking(h, tPackage.getStartDate(), tPackage.getEndDate(), bookingId, user, r);
                hotelSystem.entities.Booking hotelSystemBooking = new hotelSystem.entities.Booking(h, r, DataConnection.localDateToDate(tPackage.getStartDate()), DataConnection.localDateToDate(tPackage.getEndDate()));
                AccommodationBookingController acBookingController = new AccommodationBookingController();
                acBookingController.addBooking(hotelSystemBooking);
                dataConnection.createHotelBooking(hotelBooking);
                bookings.add(hotelBooking);
            }
        }

        // Book Day Trips
        for(Trip t : tPackage.getDayTrips()) {
            DayTripBooking dtBooking = new DayTripBooking(t, DataConnection.utilDateToLocalDate(t.getDate()), bookingId, user);
            Account dayTripAccount = new Account("-1", user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getPhoneNumber(), paymentInfo, null);
            dayTripSystem.Booking dayTripSystemBooking = new dayTripSystem.Booking(t, dayTripAccount, true/*todo setja pickup valmöguleika?*/, 0, false, tPackage.getNumAdults()+tPackage.getNumChildren());
            dayTripSystemBooking.book(); // todo beila á að tékka hvort það virkar að bóka hjá þeim
            dataConnection.createDayTripBooking(dtBooking);
            bookings.add(dtBooking);
        }
        dataConnection.closeConnection();
    }

    public void getPackageFromBookings() {
        // todo
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setOutSeats(ArrayList<Seat> outSeats) {
        this.outSeats = outSeats;
        System.out.println("out seats: " + outSeats);
    }

    public void setInSeats(ArrayList<Seat> inSeats) {
        this.inSeats = inSeats;
        System.out.println("in seats: " + inSeats);
    }
}
