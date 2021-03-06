package ui.controllers;

import controllers.SearchController;
import data.DataConnection;
import dayTripSystem.Trip;
import entities.*;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
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
    private ComboBox<String> originInput;
    @FXML
    private ComboBox<String> destinationInput;
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
    @FXML
    private Button backToSearch;
    @FXML
    private Button bookCustomPackage;
    @FXML
    private Label villuBod;

    private Stage searchStage;

    private TripPackage customPackage;

    public SearchResult searchResult;
    public SearchController searchController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user != null) {
            loginLabel.setText(user.getFirstName());
        }
    }

    public void openLogin() throws IOException {
        searchStage = (Stage) sceneRoot.getScene().getWindow();

        if(user == null) {
            Stage loginStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/loginUI.fxml"));
            Region root = loader.load();

            Scene scene = new Scene(root);
            loginStage.setScene(scene);

            LoginUiController loginUiController = loader.getController();
            loginUiController.setOwningSearchController(this);

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

            UserUIController userUIController = loader.getController();
            userUIController.setOwningController(this);
            userUIController.setUser(user);

            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.initModality(Modality.WINDOW_MODAL);
            userStage.initOwner(searchStage);
            userStage.show();
        }
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public void setCustomPackage(TripPackage customPackage) {
        this.customPackage = customPackage;
    }

    public void searchButtonClicked() throws Exception {
        try {
            if(searchResult == null) {
                String from = originInput.getValue();
                String to = destinationInput.getValue();
                LocalDate startDate = departureInput.getValue();
                LocalDate endDate = returnInput.getValue();
                if (from.isEmpty() || to.isEmpty() || startDate == null || endDate == null || adultsInput.getText().isEmpty() || childrenInput.getText().isEmpty()) {
                    villuBod.setVisible(true);
                    return;
                }
                int numAdults = Integer.parseInt(adultsInput.getText());
                int numChildren = Integer.parseInt(childrenInput.getText());
                searchResult = new SearchResult(startDate, endDate, from, to, numAdults, numChildren);
                searchResult.search();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/searchresultsUI.fxml"));
            AnchorPane resultRoot = loader.load();
            SearchUiController resultController = loader.getController();
            if(user != null) {
                resultController.setUser(user);
            }
            sceneRoot.getChildren().setAll(resultRoot);

            searchController = new SearchController(searchResult, user);
            searchController.createTripPackages();
            updateSearchView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSearchView() {
        backToSearch = (Button) sceneRoot.lookup("#backToSearch");

        backToSearch.setOnAction((evt) -> {
            goBackToSearch();
        });

        if(customPackage == null) {
            customPackage = new TripPackage("Custom Package" ,searchResult, user);
            customPackage.emptyPackage();
        }

        // (VBox) sceneRoot.lookup("#packagesVBox")
        resultScrollPane = (ScrollPane) sceneRoot.lookup("#resultScrollPane");
        resultScrollPane.applyCss();
        resultScrollPane.layout();
        packagesVBox = (VBox) resultScrollPane.lookup("#packagesVBox");

        for(TripPackage tripPackage : searchController.getPackages()) {
            displayTripPackage(tripPackage, packagesVBox);
        }

        allOutFlightsVB = (VBox) resultScrollPane.lookup("#allOutFlightsVB");
        allInFlightsVB = (VBox) resultScrollPane.lookup("#allInFlightsVB");
        allHotelsVB = (VBox) resultScrollPane.lookup("#allHotelsVB");
        allDayTripsVB = (VBox) resultScrollPane.lookup("#allDayTripsVB");
        selectedFlightsVB = (VBox) resultScrollPane.lookup("#selectedFlightsVB");
        selectedHotelVB = (VBox) resultScrollPane.lookup("#selectedHotelVB");
        selectedDayTripsVB = (VBox) resultScrollPane.lookup("#selectedDayTripsVB");

        bookCustomPackage = (Button) resultScrollPane.lookup("#bookCustomPackage");
        bookCustomPackage.setOnAction((evt) -> {
            book(customPackage);
        });

        displayAllSearchResult();
    }

    public void goBackToSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/searchUI.fxml"));
            AnchorPane searchRoot = loader.load();
            SearchUiController searchController = loader.getController();
            if(user != null) {
                searchController.setUser(user);
            }
            sceneRoot.getChildren().setAll(searchRoot);
            originInput = (ComboBox<String>) searchRoot.lookup("#originInput");
            destinationInput = (ComboBox<String>) searchRoot.lookup("#destinationInput");
            departureInput = (DatePicker) searchRoot.lookup("#departureInput");
            returnInput = (DatePicker) searchRoot.lookup("#returnInput");
            adultsInput = (TextField) searchRoot.lookup("#adultsInput");
            childrenInput = (TextField) searchRoot.lookup("#childrenInput");
            originInput.setValue(searchResult.getDepCity());
            destinationInput.setValue(searchResult.getDestCity());
            departureInput.setValue(searchResult.getStartDate());
            returnInput.setValue(searchResult.getEndDate());
            adultsInput.setText(String.valueOf(searchResult.getNumAdults()));
            childrenInput.setText(String.valueOf(searchResult.getNumChildren()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayAllSearchResult() {
        // Display all out flights
        ToggleGroup outFlightsToggleGroup = new ToggleGroup();
        for(Flight f : searchResult.getOutFlights()) {
            RadioButton flightRadio = new RadioButton(displayFlight(f));
            flightRadio.setToggleGroup(outFlightsToggleGroup);
            allOutFlightsVB.getChildren().add(flightRadio);
            flightRadio.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                    if (isSelected) {
                        customPackage.removeAllOutFlights();
                        customPackage.addOutFlight(f);
                        displayCustomPackage();
                    }
                }
            });
            if(customPackage.getOutFlights().contains(f)) flightRadio.setSelected(true);
        }
        // display all in flights
        ToggleGroup inFlightsToggleGroup = new ToggleGroup();
        for(Flight f : searchResult.getInFlights()) {
            RadioButton flightRadio = new RadioButton(displayFlight(f));
            flightRadio.setToggleGroup(inFlightsToggleGroup);
            allInFlightsVB.getChildren().add(flightRadio);
            flightRadio.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                    if(isSelected) {
                        customPackage.removeAllInFlights();
                        customPackage.addInFlight(f);
                        displayCustomPackage();
                    }
                }
            });
            if(customPackage.getInFlights().contains(f)) flightRadio.setSelected(true);
        }
        // display all hotels
        ToggleGroup hotelsToggleGroup = new ToggleGroup();
        for(Accommodation h : searchResult.getHotels()) {
            RadioButton hotelRadio = new RadioButton(h.getName()+" - "+h.getRating()+" stars");
            hotelRadio.setToggleGroup(hotelsToggleGroup);
            allHotelsVB.getChildren().add(hotelRadio);
            hotelRadio.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                    if(isSelected) {
                        customPackage.removeAllHotels();
                        customPackage.addHotel(h);
                        displayCustomPackage();
                    }
                }
            });
            if(customPackage.getHotels().contains(h)) hotelRadio.setSelected(true);
        }
        // display all day trips
        ArrayList<String> dtNames = new ArrayList<>();
        ArrayList<String> dtNamesInCustomPackage = new ArrayList<>();
        for(Trip t : customPackage.getDayTrips()) {
            if(!dtNamesInCustomPackage.contains(t.getCategory())) dtNamesInCustomPackage.add(t.getCategory());
        }
        for(Trip dt : searchResult.getDayTrips()) {
            if(dtNames.contains(dt.getCategory())) continue;
            dtNames.add(dt.getCategory());
            CheckBox dayTripCheck = new CheckBox(dt.getCategory());
            allDayTripsVB.getChildren().add(dayTripCheck);
            dayTripCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
               @Override
               public void changed(ObservableValue<? extends Boolean> obs, Boolean unchecked, Boolean checked) {
                   if(checked) {
                       customPackage.addDayTrip(dt);
                   }
                   if (unchecked) {
                       customPackage.removeDayTrip(dt);
                   }
                   displayCustomPackage();
               }
            });
            if(dtNamesInCustomPackage.contains(dt.getCategory())) {
                Trip tripToRemove = null;
                for(Trip t : customPackage.getDayTrips()) {
                    if(t.getCategory().equals(dt.getCategory())) {
                        tripToRemove = t;
                    }
                }
                if(tripToRemove != null) customPackage.removeDayTrip(tripToRemove);
                dayTripCheck.setSelected(true);
            }
        }
    }

    private void displayCustomPackage() {
        selectedFlightsVB.getChildren().clear();
        for(Flight f : customPackage.getOutFlights()) {
            selectedFlightsVB.getChildren().add(new Label(displayFlight(f)));
        }
        for(Flight f : customPackage.getInFlights()) {
            selectedFlightsVB.getChildren().add(new Label(displayFlight(f)));
        }
        selectedHotelVB.getChildren().clear();
        for(Accommodation h : customPackage.getHotels()) {
            selectedHotelVB.getChildren().add(new Label(h.toString()));
        }
        selectedDayTripsVB.getChildren().clear();
        for(Trip dt : customPackage.getDayTrips()) {
            selectedDayTripsVB.getChildren().add(new Label(dt.getCategory()));
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user == null && loginLabel != null) loginLabel.setText("Log In");
        else if(loginLabel != null) loginLabel.setText(this.user.getFirstName());
    }

    public void mouseOnLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(true);
    }

    public void mouseOffLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(false);
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
        if(!tPackage.getOutFlights().isEmpty()) {
            fImg.setRotate(-45);
            Flight outFlight = tPackage.getOutFlights().get(0);
            Label flightLabel = new Label(displayFlight(outFlight));
            flightLabel.setWrapText(true);
            HBox flightHBox = new HBox();
            flightHBox.setSpacing(5);
            flightHBox.getChildren().addAll(fImg, flightLabel);
            flights.getChildren().add(flightHBox);
        }
        //in flight
        if(!tPackage.getInFlights().isEmpty()) {
            ImageView fImg2 = new ImageView();
            fImg2.setImage(new Image("@../../img/landing.png"));
            fImg2.setFitHeight(28);
            fImg2.setPreserveRatio(true);
            Flight inFlight = tPackage.getInFlights().get(0);
            Label flightLabel = new Label(displayFlight(inFlight));
            flightLabel.setWrapText(true);
            HBox flightHBox2 = new HBox();
            flightHBox2.setSpacing(5);
            flightHBox2.getChildren().addAll(fImg2, flightLabel);
            flights.getChildren().add(flightHBox2);
            flights.setPrefWidth(350);
            gp.add(flights, 0, 1);
        }

        VBox dayTrips = new VBox();
        for(Trip dt : tPackage.getDayTrips()) {
            HBox dtHBox = new HBox();
            ImageView tripIcon = new ImageView();
            tripIcon.setImage(new Image("@../../img/"+dt.getCategory().replaceAll(" ", "")+".png"));
            tripIcon.setFitHeight(28);
            tripIcon.setPreserveRatio(true);
            Label dayTripLabel = new Label(dt.getCategory() + " on "+displayDate(DataConnection.utilDateToLocalDate(dt.getDate())));
            dayTripLabel.setWrapText(true);
            dayTripLabel.setPadding(new Insets(0,0,0,20));
            dtHBox.getChildren().addAll(tripIcon, dayTripLabel);
            dayTrips.getChildren().add(dtHBox);
        }
        gp.add(dayTrips, 1, 1, 2, 1);

        HBox hotel = new HBox();
        if(!tPackage.getHotels().isEmpty()) {
            ImageView hotelImg = new ImageView();
            hotelImg.setImage(new Image("@../../img/hotel.png"));
            hotelImg.setFitHeight(40);
            hotelImg.setPreserveRatio(true);
            hotel.getChildren().add(hotelImg);
            Accommodation accommodation = tPackage.getHotels().get(0);
            Label hotelName = new Label(accommodation.toString() + " - " + accommodation.getRating() + " stars");
            hotel.getChildren().add(hotelName);
            Label rooms;
            if(tPackage.getRooms().size() > 1) {
                rooms = new Label(tPackage.getRooms().size() + " rooms");
            }
            else {
                rooms = new Label(tPackage.getRooms().get(0).toString());
            }
            Label nights = new Label(Period.between(tPackage.getStartDate(), tPackage.getEndDate()).getDays() + " nights");
            hotel.getChildren().add(rooms);
            hotel.getChildren().add(nights);
            hotel.setAlignment(Pos.CENTER_LEFT);
        }
        hotel.setPrefWidth(450);
        gp.add(hotel, 0, 2, 2, 1);

        HBox buttons = new HBox();
        buttons.setPrefHeight(40);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        Button seemoreButton = new Button("See more");
        seemoreButton.getStyleClass().add("blue-button");
        buttons.getChildren().add(seemoreButton);
        seemoreButton.setVisible(false);
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(20, 1);
        buttons.getChildren().add(spacer);
        Button bookButton = new Button("Book package");
        bookButton.getStyleClass().add("orange-button");
        buttons.getChildren().add(bookButton);
        gp.add(buttons, 2, 2);

        bookButton.setOnAction((evt) -> {
            book(tPackage);
        });

        packagesVBox.setSpacing(10);
        packageContainer.getChildren().add(gp);
        packagesVBox.getChildren().add(packageContainer);
    }

    private String displayFlight(Flight f) {
        return f.getFlightNo() + " from " + f.getDeparture().getFullName() + " to " + f.getArrival().getFullName() + " on " + displayDateTime(f.getDepartureTime());
    }

    private String displayDateTime(LocalDateTime d) {
        return d.getDayOfMonth()+"."+d.getMonthValue()+"."+d.getYear()+" at "+d.getHour()+":"+d.getMinute();
    }

    private String displayDate(LocalDate d) {
        return d.getDayOfMonth()+"."+d.getMonthValue()+"."+d.getYear();
    }

    private void book(TripPackage tPackage) {
        /*if(user == null) {
            try {
                openLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }*/
        // todo taka ??etta ??t
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/bookingUI.fxml"));
        try {
            AnchorPane bookRoot = loader.load();
            BookingUiController bookingUiController = loader.getController();
            sceneRoot.getChildren().setAll(bookRoot);
            bookingUiController.setUser(user);
            bookingUiController.setTripPackage(tPackage);
            bookingUiController.setSearchResult(searchResult);
            bookingUiController.setCustomPackage(customPackage);
            bookingUiController.updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
