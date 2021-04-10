package ui.controllers;

import controllers.SearchController;
import controllers.UserController;
import entities.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SearchUiController implements Initializable {


    public VBox selectedFlightsVB;
    public VBox selectedHotelVB;
    public VBox selectedDayTripsVB;
    public VBox allOutFlightsVB;
    public VBox allInFlightsVB;
    public VBox allHotelsVB;
    public VBox allDayTripsVB;
    private User user;

    @FXML
    private Label loginLabel;
    @FXML
    private TextField originInput;
    @FXML
    private TextField destinationInput;
    @FXML
    private DatePicker departureInput;
    @FXML
    private DatePicker returnInput;
    @FXML
    private TextField adultsInput;
    @FXML
    private TextField childrenInput;
    @FXML
    private Button searchButton;
    @FXML
    public AnchorPane sceneRoot;
    @FXML
    public VBox packagesVBox;
    @FXML
    private ScrollPane resultScrollPane;

    private Stage searchStage;

    private TripPackage customPackage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialize");
    }

    public void openLogin(MouseEvent mouseEvent) throws IOException {
        searchStage = (Stage) sceneRoot.getScene().getWindow();

        if(user == null) {
            Stage loginStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/loginUI.fxml"));
            Region root = loader.load();

            Scene scene = new Scene(root);
            loginStage.setScene(scene);

            LoginUiController loginUiController = loader.getController();
            loginUiController.setOwningController(this);
            if(user != null) loginUiController.setUser(user);

            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.initModality(Modality.WINDOW_MODAL);
            loginStage.initOwner(searchStage);
            loginStage.show();
        }
        else {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/user.fxml"));
            Region root = loader.load();

            Scene scene = new Scene(root,800,429);
            userStage.setScene(scene);

            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.initModality(Modality.WINDOW_MODAL);
            userStage.initOwner(searchStage);
            userStage.show();
        }
    }

    public void searchButtonClicked(ActionEvent actionEvent) {
        try {
            SearchResult searchResult = new SearchResult(LocalDate.of(2021, 3, 23),
                    LocalDate.of(2021, 3, 28),
                    "Reykjavík", "Akureyri", 2, 1
            );
            searchResult.search();
            customPackage = new TripPackage("Custom Package" ,searchResult);

            AnchorPane resultRoot = FXMLLoader.load(getClass().getResource("../views/searchresultsUI.fxml"));
            sceneRoot.getChildren().setAll(resultRoot);
            // (VBox) sceneRoot.lookup("#packagesVBox")
            resultScrollPane = (ScrollPane) sceneRoot.lookup("#resultScrollPane");
            resultScrollPane.applyCss();
            resultScrollPane.layout();
            VBox packagesVBox = (VBox) resultScrollPane.lookup("#packagesVBox");

            displayTripPackage(createTestPackage(), packagesVBox);
            displayTripPackage(createTestPackage(), packagesVBox);
            displayTripPackage(createTestPackage(), packagesVBox);

            allOutFlightsVB = (VBox) resultScrollPane.lookup("#allOutFlightsVB");
            allInFlightsVB = (VBox) resultScrollPane.lookup("#allInFlightsVB");
            allHotelsVB = (VBox) resultScrollPane.lookup("#allHotelsVB");
            allDayTripsVB = (VBox) resultScrollPane.lookup("#allDayTripsVB");
            selectedFlightsVB = (VBox) resultScrollPane.lookup("#selectedFlightsVB");
            selectedHotelVB = (VBox) resultScrollPane.lookup("#selectedHotelsVB");
            selectedDayTripsVB = (VBox) resultScrollPane.lookup("#selectedDayTripsVB");

            ToggleGroup outFlightsSelected = new ToggleGroup();
            for(Flight f : searchResult.getOutFlights()) {
                HBox flight = new HBox();
                RadioButton radioButton = new RadioButton();
                radioButton.setToggleGroup(outFlightsSelected);
                flight.getChildren().add(radioButton);
                flight.getChildren().add(new Label(f.toString()));
                allOutFlightsVB.getChildren().add(flight);

                radioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                        if (isNowSelected) {
                            customPackage.removeAllOutFlights();
                            customPackage.addInFlight(f);
                            displayCustomPackage();
                        } else {
                            // ...
                        }
                    }
                });
            }
            for(Flight f : searchResult.getInFlights()) {
                allInFlightsVB.getChildren().add(new Label(f.toString()));
            }
            for(Hotel h : searchResult.getHotels()) {
                allHotelsVB.getChildren().add(new Label(h.toString()));
            }
            for(DayTrip dt : searchResult.getDayTrips()) {
                allDayTripsVB.getChildren().add(new Label(dt.toString()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayCustomPackage() {
        selectedFlightsVB.getChildren().removeAll();
        for(Flight f : customPackage.getOutFlights()) {
            selectedFlightsVB.getChildren().add(new Label(f.toString()));
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        loginLabel.setText(this.user.getFirstName());
    }

    public void mouseOnLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(true);
    }

    public void mouseOffLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(false);
    }

    public TripPackage createTestPackage() {
        SearchResult searchResult = new SearchResult(LocalDate.of(2021, 3, 23),
                LocalDate.of(2021, 3, 28),
                "Reykjavík", "Akureyri", 2, 1
        );
        searchResult.search();
        SearchController searchController = new SearchController(searchResult);
        searchController.createTripPackages();
        return searchController.getPackages().get(0);
    }

    private void displayTripPackage(TripPackage tPackage, VBox packagesVBox) {
        AnchorPane packageContainer = new AnchorPane();
        packageContainer.getStyleClass().add("packageContainer");

        GridPane gp = new GridPane();

        Label name = new Label(tPackage.getName());
        name.getStyleClass().add("packageTitle");
        VBox price = new VBox();
        price.getChildren().add(new Label(String.valueOf(tPackage.getPrice()) + "$"));
        price.getStyleClass().add("packagePrice");
        price.setAlignment(Pos.CENTER_RIGHT);
        gp.add(name, 0,0);
        gp.add(price, 2, 0);

        VBox flights = new VBox();
        ImageView fImg = new ImageView();
        fImg.setImage(new Image("@../../img/landing.png"));
        fImg.setFitHeight(28);
        fImg.setPreserveRatio(true);
        //out flight
        fImg.setRotate(-45);
        Label flightLabel = new Label(tPackage.getOutFlights().get(0).toString());
        HBox flightHBox = new HBox();
        flightHBox.setSpacing(5);
        flightHBox.getChildren().addAll(fImg, flightLabel);
        flights.getChildren().add(flightHBox);
        //in flight
        ImageView fImg2 = new ImageView();
        fImg2.setImage(new Image("@../../img/landing.png"));
        fImg2.setFitHeight(28);
        fImg2.setPreserveRatio(true);
        flightLabel = new Label(tPackage.getInFlights().get(0).toString());
        HBox flightHBox2 = new HBox();
        flightHBox2.setSpacing(5);
        flightHBox2.getChildren().addAll(fImg2, flightLabel);
        flights.getChildren().add(flightHBox2);
        flights.setPrefWidth(350);
        gp.add(flights, 0, 1);

        VBox dayTrips = new VBox();
        for(DayTrip dt : tPackage.getDayTrips()) {
            HBox dtHBox = new HBox();
            dtHBox.getChildren().add(new Label(dt.toString()));
            dayTrips.getChildren().add(dtHBox);
        }
        gp.add(dayTrips, 1, 1, 2, 1);

        HBox hotel = new HBox();
        ImageView hotelImg = new ImageView();
        hotelImg.setImage(new Image("@../../img/hotel.png"));
        hotelImg.setFitHeight(40);
        hotelImg.setPreserveRatio(true);
        hotel.getChildren().add(hotelImg);
        hotel.getChildren().add(new Label(tPackage.getHotels().get(0).toString()));
        hotel.setAlignment(Pos.CENTER_LEFT);
        hotel.setPrefWidth(450);
        gp.add(hotel, 0, 2, 2, 1);

        HBox buttons = new HBox();
        buttons.setPrefHeight(40);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.getChildren().add(new Button("See more"));
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(20, 1);
        buttons.getChildren().add(spacer);
        buttons.getChildren().add(new Button("Book package"));
        gp.add(buttons, 2, 2);

        packagesVBox.setSpacing(10);
        packageContainer.getChildren().add(gp);
        packagesVBox.getChildren().add(packageContainer);
    }
}
