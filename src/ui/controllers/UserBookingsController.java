package ui.controllers;

import data.DataConnection;
import entities.DayTripBooking;
import entities.HotelBooking;
import entities.User;
import hotelSystem.entities.Accommodation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserBookingsController {
    @FXML private BorderPane userBookings;
    @FXML private VBox bookedPackagesView;

    private User user;
    private ArrayList<Integer> userBookingIds;
    private ArrayList<HotelBooking> hotelBookings;
    private ArrayList<DayTripBooking> dayTripBookings;

    public void initialize() {

    }

    public void setUser(User user) {
        this.user = user;
        displayBookings();
    }

    private void displayBookings() {
        // get user bookings
        DataConnection dc = new DataConnection();
        userBookingIds = dc.getUserBookings(user);
        dayTripBookings = dc.getDayTripBookings(user);
        hotelBookings = dc.getHotelBookings(user);
        dc.closeConnection();

        for(Integer id : userBookingIds) {
            VBox bookingVB = new VBox();

            HBox bookingHeader = new HBox();
            Label bookingTitle = new Label("My booking, id " + id);
            Button editBooking = new Button("Cancel booking");
            editBooking.getStyleClass().add("orange-button");
            bookingHeader.getChildren().addAll(bookingTitle, editBooking);
            bookingVB.getChildren().add(bookingHeader);

            // flights and passengers
            HBox fp = new HBox();
            bookingVB.getChildren().add(fp);

            // hotel
            Label hotelHeader = new Label("Hotel");
            bookingVB.getChildren().add(hotelHeader);
            for(HotelBooking hb : hotelBookings) {
                if(hb.getBookingId() == id) {
                    HBox hotelHB = new HBox();
                    Label h1 = new Label("Hotel:");
                    Label h2 = new Label(hb.getHotelName());
                    Label h3 = new Label("City:");
                    Label h4 = new Label(hb.getCity());
                    Label h5 = new Label("Room:");
                    Label h6 = new Label(hb.getRoom());
                    Label h7 = new Label(hb.getNumberOfNights() + " nights");
                    hotelHB.getChildren().addAll(h1,h2,h3,h4,h5,h6,h7);
                    bookingVB.getChildren().add(hotelHB);
                }
            }

            Label dtHeader = new Label("Day Trips");
            bookingVB.getChildren().add(dtHeader);
            for(DayTripBooking dtB : dayTripBookings) {
                if(dtB.getBookingId() == id) {
                    HBox dtHb = new HBox();
                    Label d1 = new Label(dtB.getDayTripName());
                    Label d2 = new Label(dtB.getCity());
                    Label d3 = new Label(dtB.getDate().toString());
                    dtHb.getChildren().addAll(d1,d2,d3);
                    bookingVB.getChildren().add(dtHb);
                }
            }

            Label price = new Label("Total price: " + "setja verð");
            bookingVB.getChildren().add(price);

            bookedPackagesView.getChildren().add(bookingVB);
        }

    }

    public void closeWindow(ActionEvent actionEvent) {
        ((Stage) userBookings.getScene().getWindow()).close();
    }
}
