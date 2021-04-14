package ui.controllers;

import controllers.BookingController;
import entities.DayTrip;
import entities.SearchResult;
import entities.TripPackage;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingUiController {

    @FXML
    private AnchorPane bookRoot;
    @FXML
    private Label packageTitle;
    @FXML
    private Label packagePrice;
    @FXML
    private VBox flightsVB;
    @FXML
    private VBox dayTripsVB;
    @FXML
    private VBox hotelVB;
    @FXML
    private VBox adultsFirstNameVB;
    @FXML
    private VBox adultsLastNameVB;
    @FXML
    private VBox childrenFirstNameVB;
    @FXML
    private VBox childrenLastNameVB;
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
    @FXML
    private VBox adultsLuggageVB;
    @FXML
    private VBox childrenLuggageVB;
    @FXML
    private VBox adultsInsuranceVB;
    @FXML
    private VBox childrenInsuranceVB;

    private ArrayList<DatePicker> dayTripDatesDP = new ArrayList<>();

    private User user;
    private TripPackage tripPackage;
    private SearchResult searchResult;
    private TripPackage customPackage;

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

    public void setCustomPackage(TripPackage customPackage) {
        this.customPackage = customPackage;
    }

    public void updateView() {
        packageTitle.setText("Book " + tripPackage.getName());
        packagePrice.setText(String.valueOf(tripPackage.getPrice())+"$");
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
            fNumOut.setText(tripPackage.getOutFlights().get(0).getFlightNo());
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
            fNumIn.setText(tripPackage.getInFlights().get(0).getFlightNo());
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
            dayTripDatesDP.add(dayChooser);
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
            TextField adultFirstName = new TextField();
            adultFirstName.setPromptText("First name " + (i+1));
            adultFirstName.getStyleClass().add("personName");
            TextField adultLastName = new TextField();
            adultLastName.setPromptText("Last name " + (i+1));
            adultLastName.getStyleClass().add("personName");
            adultsFirstNameVB.getChildren().add(adultFirstName);
            adultsLastNameVB.getChildren().add(adultLastName);
            if(!tripPackage.getOutFlights().isEmpty()) {
                ComboBox seatOut = new ComboBox();
                flightOutSeat.getChildren().add(seatOut);
            }
            if(!tripPackage.getInFlights().isEmpty()) {
                ComboBox seatIn = new ComboBox();
                flightInSeat.getChildren().add(seatIn);
            }
            if(!tripPackage.getOutFlights().isEmpty() || !tripPackage.getInFlights().isEmpty()) {
                CheckBox luggage = new CheckBox();
                adultsLuggageVB.getChildren().add(luggage);
                adultsLuggageVB.getChildren().add(createSpacer());
                CheckBox insurance = new CheckBox();
                adultsInsuranceVB.getChildren().add(insurance);
                adultsInsuranceVB.getChildren().add(createSpacer());
            }
        }
        adultsFirstNameVB.setSpacing(5);
        adultsLastNameVB.setSpacing(5);
        flightOutSeat.setSpacing(5);
        flightInSeat.setSpacing(5);
        adultsLuggageVB.setSpacing(5);
        adultsLuggageVB.setSpacing(5);
        adultsLuggageVB.setPadding(new Insets(3,0,0,0));
        adultsInsuranceVB.setPadding(new Insets(3,0,0,0));

        for(int i = 0; i < searchResult.getNumChildren(); i++) {
            TextField childFirstName = new TextField();
            childFirstName.setPromptText("First name of child " + (i+1));
            childFirstName.getStyleClass().add("personName");
            TextField childLastName = new TextField();
            childLastName.setPromptText("Last name of child " + (i+1));
            childLastName.getStyleClass().add("personName");
            childrenFirstNameVB.getChildren().add(childFirstName);
            childrenLastNameVB.getChildren().add(childLastName);
            if(!tripPackage.getOutFlights().isEmpty()) {
                ComboBox seatOut = new ComboBox();
                flightOutSeatC.getChildren().add(seatOut);
            }
            if(!tripPackage.getInFlights().isEmpty()) {
                ComboBox seatIn = new ComboBox();
                flightInSeatC.getChildren().add(seatIn);
            }
            if(!tripPackage.getOutFlights().isEmpty() || !tripPackage.getInFlights().isEmpty()) {
                CheckBox luggage = new CheckBox();
                childrenLuggageVB.getChildren().add(luggage);
                childrenLuggageVB.getChildren().add(createSpacer());
                CheckBox insurance = new CheckBox();
                childrenInsuranceVB.getChildren().add(insurance);
                childrenInsuranceVB.getChildren().add(createSpacer());
            }
            childrenLuggageVB.setPadding(new Insets(3,0,0,0));
            childrenInsuranceVB.setPadding(new Insets(3,0,0,0));
            //todo setja users í fyrsta nafnaboxið
        }
        childrenFirstNameVB.setSpacing(5);
        childrenLastNameVB.setSpacing(5);
        flightOutSeatC.setSpacing(5);
        flightInSeatC.setSpacing(5);
        childrenLuggageVB.setSpacing(5);
        childrenInsuranceVB.setSpacing(5);
    }

    public void goBackToSearchResult(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/searchUI.fxml"));
        try {
            AnchorPane resultRoot = loader.load();
            SearchUiController searchUiController = loader.getController();
            bookRoot.getChildren().setAll(resultRoot);
            searchUiController.setUser(user);
            searchUiController.setSearchResult(searchResult);
            searchUiController.setCustomPackage(customPackage);
            searchUiController.searchButtonClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payButtonPressed() {
        BookingController bookingController = new BookingController(tripPackage, user, searchResult);
        bookingController.setDayTripDates(getDayTripDates());
        bookingController.bookPackage();
        goBackToSearch();
        openUserBookings();
    }

    private ArrayList<LocalDate> getDayTripDates() {
        ArrayList<LocalDate> dayTripDates = new ArrayList<>();
        for(DatePicker d : dayTripDatesDP) {
            dayTripDates.add(d.getValue());
        }
        return dayTripDates;
    }

    private void openUserBookings() {
        Stage bookWindow = (Stage) bookRoot.getScene().getWindow();

        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/user.fxml"));
        try {
            Region root = loader.load();
            Scene scene = new Scene(root,800,429);
            userStage.setScene(scene);

            UserUIController userUIController = loader.getController();
            userUIController.setUser(user);
            userUIController.openBookinsTab();

            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.initModality(Modality.WINDOW_MODAL);
            userStage.initOwner(bookWindow);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goBackToSearch() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/searchUI.fxml"));
        try {
            AnchorPane resultRoot = loader.load();
            SearchUiController searchUiController = loader.getController();
            bookRoot.getChildren().setAll(resultRoot);
            searchUiController.setUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node createSpacer() {
        final Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}
