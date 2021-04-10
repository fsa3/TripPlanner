package ui.controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserBookingsController {
    @FXML private BorderPane userBookings;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void closeWindow(ActionEvent actionEvent) {
        ((Stage) userBookings.getScene().getWindow()).close();
    }
}
