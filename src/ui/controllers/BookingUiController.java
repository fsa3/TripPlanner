package ui.controllers;

import entities.TripPackage;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BookingUiController {

    @FXML
    private AnchorPane bookRoot;

    private User user;
    private TripPackage tripPackage;

    public void initialize() {
        System.out.println("initialize book UI");
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTripPackage(TripPackage tPackage) {
        tripPackage = tPackage;
    }

    public void update() {
        bookRoot.getChildren().add(new Label("Book " + tripPackage.getName()));
    }

    public void goBackToSearchResult(ActionEvent actionEvent) {
        // todo fara til baka í leitarniðurstöðum
    }
}
