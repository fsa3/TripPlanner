package ui.controllers;

import data.DataConnection;
import entities.DayTripBooking;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserBookingsController {
    @FXML private BorderPane userBookings;
    @FXML private VBox bookedPackagesView;

    private User user;
    private ArrayList<DayTripBooking> dayTripBookings;

    public void initialize() {

    }

    public void setUser(User user) {
        this.user = user;

        DataConnection dc = new DataConnection();
        String bookings = dc.getUserBookings(user);
        dc.closeConnection();
        bookedPackagesView.getChildren().add(new Label(bookings));

        //dayTripBookings = dc.getDayTripBookings(user);
    }

    public void closeWindow(ActionEvent actionEvent) {
        ((Stage) userBookings.getScene().getWindow()).close();
    }
}
