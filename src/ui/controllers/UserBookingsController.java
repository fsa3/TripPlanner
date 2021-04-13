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
    private ArrayList<Integer> userBookingIds;
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
        for(Integer id : userBookingIds) {
            bookedPackagesView.getChildren().add(new Label(id.toString()));
            for(DayTripBooking dtB : dayTripBookings) {
                if(dtB.getBookingId() == id) {
                    Label dtLabel = new Label(dtB.toString());
                    dtLabel.setWrapText(true);
                    bookedPackagesView.getChildren().add(dtLabel);
                }
            }
        }

    }

    public void closeWindow(ActionEvent actionEvent) {
        ((Stage) userBookings.getScene().getWindow()).close();
    }
}
