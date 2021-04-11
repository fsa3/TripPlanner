package ui.controllers;

import entities.DayTrip;
import entities.SearchResult;
import entities.TripPackage;
import entities.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingUiController {

    @FXML
    private AnchorPane bookRoot;
    @FXML
    private VBox flightsVB;
    @FXML
    private VBox dayTripsVB;
    @FXML
    private VBox hotelVB;
    @FXML
    private VBox adultsVB;
    @FXML
    private VBox childrenVB;
    @FXML
    private Label fNumOut;
    @FXML
    private Label fNumIn;
    @FXML
    private VBox flightOutSeat;
    @FXML
    private VBox flightInSeat;
    @FXML
    private VBox flightOutSeatC;
    @FXML
    private VBox flightInSeatC;

    private User user;
    private TripPackage tripPackage;
    private SearchResult searchResult;
    private AnchorPane searchRoot;

    public void initialize() {
        System.out.println("initialize book UI");
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTripPackage(TripPackage tPackage) {
        tripPackage = tPackage;
    }

    public void setSearchResult(SearchResult result) {
        searchResult = result;
    }

    public void setSearchRoot(AnchorPane root) {
        searchRoot = root;
    }

    public void updateView() {
        // todo stinga inn nafni á pakka
        // todo stinga inn verði á pakka
        if(!tripPackage.getOutFlights().isEmpty()) {
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
            fNumOut.setText(tripPackage.getOutFlights().get(0).getFlightNum());
        }
        if(!tripPackage.getInFlights().isEmpty()) {
            //in flight
            ImageView fImg2 = new ImageView();
            fImg2.setImage(new Image("@../../img/landing.png"));
            fImg2.setFitHeight(28);
            fImg2.setPreserveRatio(true);
            Label flightLabel = new Label(tripPackage.getInFlights().get(0).toString());
            HBox flightHBox2 = new HBox();
            flightHBox2.setSpacing(5);
            flightHBox2.getChildren().addAll(fImg2, flightLabel);
            flightsVB.getChildren().add(flightHBox2);
            flightsVB.setPrefWidth(350);
            fNumIn.setText(tripPackage.getInFlights().get(0).getFlightNum());
        }

        // show day trips
        for(DayTrip dt : tripPackage.getDayTrips()) {
            HBox dtHBox = new HBox();
            Label tripLabel = new Label(dt.toString());
            tripLabel.setPrefWidth(200);
            DatePicker dayChooser = new DatePicker();
            dayChooser.setDayCellFactory(d -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(searchResult.getEndDate()) || item.isBefore(searchResult.getStartDate()));
                }});
            dayChooser.setPrefHeight(10);
            dtHBox.getChildren().addAll(tripLabel, dayChooser);
            dayTripsVB.getChildren().addAll(dtHBox);
        }

        if(!tripPackage.getHotels().isEmpty()){
            // show hotel
            HBox hotel = new HBox();
            ImageView hotelImg = new ImageView();
            hotelImg.setImage(new Image("@../../img/hotel.png"));
            hotelImg.setFitHeight(40);
            hotelImg.setPreserveRatio(true);
            hotel.getChildren().add(hotelImg);
            hotel.getChildren().add(new Label(tripPackage.getHotels().get(0).toString()));
            ComboBox roomType = new ComboBox();
            hotel.getChildren().add(roomType);
            hotel.setAlignment(Pos.CENTER_LEFT);
            hotel.setPrefWidth(450);
            hotelVB.getChildren().add(hotel);
        }

        for(int i = 0; i < searchResult.getNumAdults(); i++) {
            TextField adultName = new TextField();
            adultName.setPromptText("Name of person " + (i+1));
            adultName.getStyleClass().add("personName");
            adultsVB.getChildren().add(adultName);
            if(!tripPackage.getOutFlights().isEmpty()) {
                ComboBox seatOut = new ComboBox();
                flightOutSeat.getChildren().add(seatOut);
            }
            if(!tripPackage.getInFlights().isEmpty()) {
                ComboBox seatIn = new ComboBox();
                flightInSeat.getChildren().add(seatIn);
            }
        }
        adultsVB.setSpacing(5);
        flightOutSeat.setSpacing(5);
        flightInSeat.setSpacing(5);

        for(int i = 0; i < searchResult.getNumChildren(); i++) {
            TextField childName = new TextField();
            childName.setPromptText("Name of child " + (i+1));
            childName.getStyleClass().add("personName");
            childrenVB.getChildren().add(childName);
            if(!tripPackage.getOutFlights().isEmpty()) {
                ComboBox seatOut = new ComboBox();
                flightOutSeatC.getChildren().add(seatOut);
            }
            if(!tripPackage.getInFlights().isEmpty()) {
                ComboBox seatIn = new ComboBox();
                flightInSeatC.getChildren().add(seatIn);
            }
        }
        childrenVB.setSpacing(5);
        flightOutSeatC.setSpacing(5);
        flightInSeatC.setSpacing(5);
    }

    public void goBackToSearchResult(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/searchUI.fxml"));
        try {
            AnchorPane resultRoot = loader.load();
            SearchUiController searchUiController = loader.getController();
            bookRoot.getChildren().setAll(resultRoot);
            searchUiController.setUser(user);
            searchUiController.setSearchResult(searchResult);
            searchUiController.goBackToSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}