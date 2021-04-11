package ui.controllers;

import entities.DayTrip;
import entities.TripPackage;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BookingUiController {

    @FXML
    private AnchorPane bookRoot;
    @FXML
    private VBox flightsVB;
    @FXML
    private VBox dayTripsVB;

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

    public void updateView() {
        // todo stinga inn nafni á pakka
        // todo stinga inn verði á pakka
        //out flight
        ImageView fImg = new ImageView();
        fImg.setImage(new Image("@../../img/landing.png"));
        fImg.setFitHeight(28);
        fImg.setPreserveRatio(true);
        fImg.setRotate(-45);
        Label flightLabel = new Label(tripPackage.getOutFlights().get(0).toString());
        HBox flightHBox = new HBox();
        flightHBox.setSpacing(5);
        flightHBox.getChildren().addAll(fImg, flightLabel);
        flightsVB.getChildren().add(flightHBox);
        //in flight
        ImageView fImg2 = new ImageView();
        fImg2.setImage(new Image("@../../img/landing.png"));
        fImg2.setFitHeight(28);
        fImg2.setPreserveRatio(true);
        flightLabel = new Label(tripPackage.getInFlights().get(0).toString());
        HBox flightHBox2 = new HBox();
        flightHBox2.setSpacing(5);
        flightHBox2.getChildren().addAll(fImg2, flightLabel);
        flightsVB.getChildren().add(flightHBox2);
        flightsVB.setPrefWidth(350);

        for(DayTrip dt : tripPackage.getDayTrips()) {
            HBox dtHBox = new HBox();
            Label tripLabel = new Label(dt.toString());
            dtHBox.getChildren().addAll(tripLabel);
            dayTripsVB.getChildren().add(dtHBox);
        }
    }

    public void goBackToSearchResult(ActionEvent actionEvent) {
        // todo fara til baka í leitarniðurstöður
    }
}
