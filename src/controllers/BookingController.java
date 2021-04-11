package controllers;

import entities.Booking;
import entities.TripPackage;
import entities.User;

import java.util.ArrayList;

public class BookingController {
    private TripPackage tPackage;
    private ArrayList<Booking> bookings;
    private User user;

    public BookingController(TripPackage tPackage, User user) {
        this.tPackage = tPackage;
        this.user = user;
    }

    public BookingController(ArrayList<Booking> bookings, User user) {
        this.bookings = bookings;
        this.user = user;
    }

    public void bookPackage() {
        // todo kalla á föll frá hinum hópunum til að bóka

    }

    public void getPackageFromBookings() {
        // todo
    }
}
