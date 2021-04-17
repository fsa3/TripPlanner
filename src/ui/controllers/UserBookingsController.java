package ui.controllers;

import data.DataConnection;
import entities.DayTripBooking;
import entities.HotelBooking;
import entities.User;
import hotelSystem.entities.Accommodation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
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
        bookedPackagesView.setSpacing(10);
        // get user bookings
        DataConnection dc = new DataConnection();
        userBookingIds = dc.getUserBookings(user);
        dayTripBookings = dc.getDayTripBookings(user);
        hotelBookings = dc.getHotelBookings(user);

        for(Integer id : userBookingIds) {
            Double price = dc.getBookingPrice(id);
            VBox bookingVB = new VBox();

            HBox bookingHeader = new HBox();
            Label bookingTitle = new Label("My booking, id " + id);
            bookingTitle.getStyleClass().add("title1-bookings");
            Button editBooking = new Button("Cancel booking");
            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            editBooking.getStyleClass().add("blue-button");
            bookingHeader.getChildren().addAll(bookingTitle, spacer, editBooking);
            bookingVB.getChildren().add(bookingHeader);

            // flights and passengers
            HBox fp = new HBox();
            VBox flightsVB = new VBox();
            Label flightsHeader = new Label("Flights");
            flightsHeader.getStyleClass().add("title2");
            flightsVB.getChildren().add(flightsHeader);

            VBox passengersVB = new VBox();
            Label passengersHeader = new Label("Passengers");
            passengersHeader.getStyleClass().add("title2");
            passengersVB.getChildren().add(passengersHeader);

            fp.getChildren().addAll(flightsVB, passengersVB);
            bookingVB.getChildren().add(fp);

            // hotel
            Label hotelHeader = new Label("Hotel");
            hotelHeader.getStyleClass().add("title2");
            bookingVB.getChildren().add(hotelHeader);
            for(HotelBooking hb : hotelBookings) {
                if(hb.getBookingId() == id) {
                    HBox hotelHB = new HBox();
                    Label h1 = new Label("Hotel:");
                    Label h2 = new Label(hb.getHotelName());
                    Label h3 = new Label("City:");
                    Label h4 = new Label(hb.getCity());
                    Label h5 = new Label("Room:");
                    Label h6 = new Label(hb.getRoom().toString());
                    Label h7 = new Label(hb.getNumberOfNights() + " nights");
                    hotelHB.getChildren().addAll(h1,h2,h3,h4,h5,h6,h7);
                    bookingVB.getChildren().add(hotelHB);
                }
            }

            Label dtHeader = new Label("Day Trips");
            dtHeader.getStyleClass().add("title2");
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

            Label priceLabel = new Label("Total price: " + price + "$");
            priceLabel.getStyleClass().add("title2");
            bookingVB.getChildren().add(priceLabel);

            bookingVB.getStyleClass().add("packageContainer");
            bookedPackagesView.getChildren().add(bookingVB);
        }
        dc.closeConnection();

    }

    public void closeWindow(ActionEvent actionEvent) {
        ((Stage) userBookings.getScene().getWindow()).close();
    }
}
