package ui.controllers;

import controllers.BookingController;
import data.DataConnection;
import dayTripSystem.PaymentInfo;
import dayTripSystem.Trip;
import entities.SearchResult;
import entities.TripPackage;
import entities.User;
import flightSystem.flightplanner.entities.Flight;
import flightSystem.flightplanner.entities.Info;
import flightSystem.flightplanner.entities.Passenger;
import flightSystem.flightplanner.entities.Seat;
import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class BookingUiController {

    @FXML
    private AnchorPane bookRoot;
    @FXML
    private Label loginLabel;
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
    private VBox roomsVB;
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
    @FXML
    private TextField paymentName;
    @FXML
    private TextField cardNum;
    @FXML
    private TextField expiryMonth;
    @FXML
    private TextField expiryYear;
    @FXML
    private TextField cvv;
    @FXML
    private Label paymentError;

    private ArrayList<DatePicker> dayTripDatesDP = new ArrayList<>();

    private User user;
    private TripPackage tripPackage;
    private SearchResult searchResult;
    private TripPackage customPackage;

    public void initialize() {
        Info.getInstance().clearSeatLists();
    }

    public void setUser(User user) {
        this.user = user;
        if(user == null && loginLabel != null) loginLabel.setText("Log In");
        else if(loginLabel != null) loginLabel.setText(user.getFirstName());
        try {
            setUserAsFirstPassenger();
        }
        catch (Exception ignored) {}
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

    public void mouseOnLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(true);
    }

    public void mouseOffLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(false);
    }

    public void openLogin() throws IOException {
        Stage bookStage = (Stage) bookRoot.getScene().getWindow();

        if (user == null) {
            Stage loginStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/loginUI.fxml"));
            Region root = loader.load();

            Scene scene = new Scene(root);
            loginStage.setScene(scene);

            LoginUiController loginUiController = loader.getController();
            loginUiController.setOwningController(this);

            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.initModality(Modality.WINDOW_MODAL);
            loginStage.initOwner(bookStage);
            loginStage.show();
        } else {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/user.fxml"));
            Region root = loader.load();

            Scene scene = new Scene(root, 800, 429);
            userStage.setScene(scene);

            UserUIController userUIController = loader.getController();
            userUIController.setOwningController(this);
            userUIController.setUser(user);

            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.initModality(Modality.WINDOW_MODAL);
            userStage.initOwner(bookStage);
            userStage.show();
        }
    }

    public void updateView() {
        if(user != null) loginLabel.setText(user.getFirstName());
        packageTitle.setText("Book " + tripPackage.getName());
        packagePrice.setText(String.valueOf(tripPackage.getPrice())+"$");
        if(!tripPackage.getOutFlights().isEmpty()) {
            //out flight
            ImageView fImg = new ImageView();
            fImg.setImage(new Image("@../../img/landing.png"));
            fImg.setFitHeight(28);
            fImg.setPreserveRatio(true);
            fImg.setRotate(-45);
            Label flightLabel = new Label(displayFlight(tripPackage.getOutFlights().get(0)));
            Label flightLabel2 = new Label(displayDateTime(tripPackage.getOutFlights().get(0)));
            flightLabel.setWrapText(true);
            VBox flightLabels = new VBox();
            flightLabels.getChildren().addAll(flightLabel, flightLabel2);
            HBox flightHBox = new HBox();
            flightHBox.setSpacing(10);
            flightHBox.getChildren().addAll(fImg, flightLabels);
            flightsVB.getChildren().add(flightHBox);
            fNumOut.setText(tripPackage.getOutFlights().get(0).getFlightNo());
        }
        if(!tripPackage.getInFlights().isEmpty()) {
            //in flight
            ImageView fImg2 = new ImageView();
            fImg2.setImage(new Image("@../../img/landing.png"));
            fImg2.setFitHeight(28);
            fImg2.setPreserveRatio(true);
            Label flightLabel = new Label(displayFlight(tripPackage.getInFlights().get(0)));
            Label flightLabel2 = new Label(displayDateTime(tripPackage.getInFlights().get(0)));
            flightLabel.setWrapText(true);
            VBox flightLabels = new VBox();
            flightLabels.getChildren().addAll(flightLabel,flightLabel2);
            HBox flightHBox2 = new HBox();
            flightHBox2.setSpacing(10);
            flightHBox2.getChildren().addAll(fImg2, flightLabels);
            flightsVB.getChildren().add(flightHBox2);
            flightsVB.setPrefWidth(350);
            fNumIn.setText(tripPackage.getInFlights().get(0).getFlightNo());
        }

        // show day trips
        ArrayList<String> tripDisplayNames = new ArrayList<>(); // todo taka út þegar pakkar innihalda bara eitt stk af hverri tegund dagsferðar
        for(Trip dt : tripPackage.getDayTrips()) {
            if(tripDisplayNames.contains(dt.getCategory())) continue;
            HBox dtHBox = new HBox();
            ImageView tripIcon = new ImageView();
            tripIcon.setImage(new Image("@../../img/"+dt.getCategory().replaceAll(" ", "")+".png"));
            tripIcon.setFitHeight(28);
            tripIcon.setPreserveRatio(true);
            Label tripLabel = new Label(dt.getCategory());
            tripLabel.setPrefWidth(100);
            tripLabel.setPrefHeight(25);
            tripLabel.setPadding(new Insets(0,0,0,10));
            Label tripPrice = new Label(dt.getPrice() + "$");
            tripPrice.setPrefWidth(100);
            tripPrice.setPrefHeight(25);
            tripPrice.setTextAlignment(TextAlignment.RIGHT);
            tripPrice.setAlignment(Pos.CENTER_RIGHT);
            tripPrice.setPadding(new Insets(0, 20, 0, 0));
            DatePicker dayChooser = new DatePicker();
            //todo setja dagsetningar í dayChooser
            dayChooser.setValue(DataConnection.utilDateToLocalDate(dt.getDate()));
            ArrayList<LocalDate> availableDates = new ArrayList<>();
            for(Trip resultTrip : searchResult.getDayTrips()) {
                if(resultTrip.getCategory().equals(dt.getCategory())) {
                    availableDates.add(DataConnection.utilDateToLocalDate(resultTrip.getDate()));
                }
            }
            dayChooser.setDayCellFactory(d -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(!availableDates.contains(item));
                }
            });
            dayChooser.valueProperty().addListener(((observableValue, oldDate, newDate) -> {
                Trip oldTrip = null;
                Trip newTrip = null;
                for(Trip t : searchResult.getDayTrips()) {
                    if(!t.getCategory().equals(dt.getCategory())) continue;
                    if(DataConnection.utilDateToLocalDate(t.getDate()).equals(oldDate)) {
                        oldTrip = t;
                    }
                    if(DataConnection.utilDateToLocalDate(t.getDate()).equals(newDate)) {
                        newTrip = t;
                    }
                }
                if(oldTrip != null && newTrip != null) {
                    tripPackage.removeDayTrip(oldTrip);
                    tripPackage.addDayTrip(newTrip);
                    tripPrice.setText(newTrip.getPrice() + "$");
                }
                else dayChooser.setValue(oldDate);
            }));
            dayTripDatesDP.add(dayChooser);
            dayChooser.setPrefHeight(10);
            dtHBox.getChildren().addAll(tripIcon ,tripLabel, tripPrice, dayChooser);
            dayTripsVB.getChildren().addAll(dtHBox);

            tripDisplayNames.add(dt.getCategory());
        }

        if(!tripPackage.getHotels().isEmpty()){
            // show hotel
            HBox hotelHB = new HBox();
            hotelHB.setSpacing(10);
            ImageView hotelImg = new ImageView();
            hotelImg.setImage(new Image("@../../img/hotel.png"));
            hotelImg.setFitHeight(40);
            hotelImg.setPreserveRatio(true);
            hotelHB.getChildren().add(hotelImg);
            Accommodation accommodation = tripPackage.getHotels().get(0);
            hotelHB.getChildren().add(new Label(accommodation.toString() + " - " + accommodation.getRating()));
            ComboBox<Room> roomType = new ComboBox<Room>();
            hotelHB.getChildren().add(roomType);
            updateAvailableRooms(roomType);
            updateRoomList(roomType);
            Button addRoom = new Button("Add room");
            addRoom.getStyleClass().add("blue-button");
            hotelHB.getChildren().add(addRoom);
            hotelHB.setAlignment(Pos.CENTER_LEFT);
            hotelHB.setPrefWidth(450);
            hotelVB.getChildren().add(hotelHB);

            addRoom.setOnAction((evt) -> {
                Room r = roomType.getValue();
                if(r == null) return;
                tripPackage.addRoom(r);
                updateRoomList(roomType);
                updateAvailableRooms(roomType);
            });
        }

        for(int i = 0; i < searchResult.getNumAdults(); i++) {
            TextField adultFirstName = new TextField();
            adultFirstName.setPromptText("First name " + (i+1));
            adultFirstName.getStyleClass().add("personName");
            TextField adultLastName = new TextField();
            adultLastName.setPromptText("Last name " + (i+1));
            adultLastName.getStyleClass().add("personName");
            adultsFirstNameVB.getChildren().addAll(adultFirstName, createSpacer());
            adultsLastNameVB.getChildren().addAll(adultLastName, createSpacer());
            if(!tripPackage.getOutFlights().isEmpty()) {
                Button seatOut = new Button("Seat");
                seatOut.getStyleClass().add("seat-out-button");
                flightOutSeat.getChildren().addAll(seatOut, createSpacer());
                seatOut.getStyleClass().add("blue-button");

                seatOut.setOnAction((evt) -> {
                    pickSeat(tripPackage.getOutFlights().get(0), seatOut);
                });
            }
            if(!tripPackage.getInFlights().isEmpty()) {
                Button seatIn = new Button("Seat");
                seatIn.getStyleClass().add("seat-in-button");
                flightInSeat.getChildren().addAll(seatIn, createSpacer());
                seatIn.getStyleClass().add("blue-button");

                seatIn.setOnAction((evt) -> {
                    pickSeat(tripPackage.getInFlights().get(0), seatIn);
                });
            }
            if(!tripPackage.getOutFlights().isEmpty() || !tripPackage.getInFlights().isEmpty()) {
                CheckBox luggage = new CheckBox();
                luggage.setAlignment(Pos.CENTER);
                adultsLuggageVB.getChildren().add(luggage);
                adultsLuggageVB.getChildren().add(createSpacer());
                CheckBox insurance = new CheckBox();
                adultsInsuranceVB.getChildren().add(insurance);
                adultsInsuranceVB.getChildren().add(createSpacer());
            }
            if(i == 0 && user != null) setUserAsFirstPassenger();
        }

        adultsFirstNameVB.setSpacing(5);
        adultsLastNameVB.setSpacing(5);
        flightOutSeat.setSpacing(5);
        flightInSeat.setSpacing(5);
        adultsLuggageVB.setSpacing(5);
        adultsInsuranceVB.setSpacing(5);

        for(int i = 0; i < searchResult.getNumChildren(); i++) {
            TextField childFirstName = new TextField();
            childFirstName.setPromptText("First name of child " + (i+1));
            childFirstName.getStyleClass().add("personName");
            TextField childLastName = new TextField();
            childLastName.setPromptText("Last name of child " + (i+1));
            childLastName.getStyleClass().add("personName");
            childrenFirstNameVB.getChildren().addAll(childFirstName, createSpacer());
            childrenLastNameVB.getChildren().addAll(childLastName, createSpacer());
            if(!tripPackage.getOutFlights().isEmpty()) {
                Button seatOut = new Button("Seat");
                seatOut.getStyleClass().add("seat-out-button");
                flightOutSeatC.getChildren().addAll(seatOut, createSpacer());
                seatOut.getStyleClass().add("blue-button");

                seatOut.setOnAction((evt) -> {
                    pickSeat(tripPackage.getOutFlights().get(0), seatOut);
                });
            }
            if(!tripPackage.getInFlights().isEmpty()) {
                Button seatIn = new Button("Seat");
                seatIn.getStyleClass().add("seat-in-button");
                flightInSeatC.getChildren().addAll(seatIn, createSpacer());
                seatIn.getStyleClass().add("blue-button");

                seatIn.setOnAction((evt) -> {
                    pickSeat(tripPackage.getInFlights().get(0), seatIn);
                });
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
        }
        childrenFirstNameVB.setSpacing(5);
        childrenLastNameVB.setSpacing(5);
        flightOutSeatC.setSpacing(5);
        flightInSeatC.setSpacing(5);
        childrenLuggageVB.setSpacing(5);
        childrenInsuranceVB.setSpacing(5);
    }

    private void setUserAsFirstPassenger() {
        ((TextField)adultsFirstNameVB.getChildren().get(1)).setText(user.getFirstName());
        ((TextField)adultsLastNameVB.getChildren().get(1)).setText(user.getLastName());
    }

    private void updateRoomList(ComboBox<Room> roomSelector) {
        roomsVB.getChildren().clear();
        if(!tripPackage.getRooms().isEmpty()) {
            Label header = new Label("Selected rooms:");
            header.getStyleClass().add("packageTitle");
            roomsVB.getChildren().add(header);
        }
        for(Room r : tripPackage.getRooms()) {
            HBox roomEntry = new HBox();
            Label roomTitle = new Label(r.getRoomType().name());
            Label capacity = new Label(r.getCap() + " people");
            Label price = new Label(r.getPrice() + "$");
            Hyperlink removeRoom = new Hyperlink("Remove room");
            removeRoom.getStyleClass().add("rrHyperlink");
            roomTitle.setMinHeight(20);
            roomTitle.setMinWidth(50);
            capacity.setMinHeight(20);
            capacity.setMinWidth(30);
            price.setMinHeight(20);
            price.setMinWidth(30);
            roomEntry.getChildren().addAll(roomTitle, capacity, price, removeRoom);
            roomEntry.setSpacing(10);
            roomsVB.getChildren().add(roomEntry);

            removeRoom.setOnAction((evt) -> {
                tripPackage.removeRoom(r);
                updateAvailableRooms(roomSelector);
                updateRoomList(roomSelector);
            });
        }
    }

    private void updateAvailableRooms(ComboBox<Room> roomSelector) {
        Accommodation accommodation = tripPackage.getHotels().get(0);
        ArrayList<Room> availableRooms = accommodation.getAvailableRooms(DataConnection.localDateToDate(tripPackage.getStartDate()), DataConnection.localDateToDate(tripPackage.getEndDate()));
        for(Room r : tripPackage.getRooms()) {
            availableRooms.remove(r);
        }
        availableRooms = removeDuplicates(availableRooms);
        roomSelector.setItems(FXCollections.observableArrayList(availableRooms));
    }

    private ArrayList<Room> removeDuplicates(ArrayList<Room> list) {
        ArrayList<Room> newList = new ArrayList<>();

        for(Room r : list) {
            if(!roomInList(r, newList)) {
                newList.add(r);
            }
        }
        return newList;
    }

    private boolean roomInList(Room room, ArrayList<Room> newList) {
        for(Room r : newList) {
            if(r.getRoomType().equals(room.getRoomType()) && r.getPrice() == room.getPrice() && r.getCap() == room.getCap()) {
                return true;
            }
        }
        return false;
    }

    private void pickSeat(Flight flight, Button seatButton) {
        Parent root = null;
        Info information = Info.getInstance();
        information.setFlight(flight);
        information.setSeatButton(seatButton);
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../../flightSystem/flightplanner/ui/saetaval.fxml")));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("BookMaster");
            primaryStage.setScene(new Scene(root));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBackToSearchResult(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/searchUI.fxml"));
        try {
            AnchorPane resultRoot = loader.load();
            SearchUiController searchUiController = loader.getController();
            bookRoot.getChildren().setAll(resultRoot);
            searchUiController.setUser(user);
            searchUiController.setSearchResult(searchResult);
            customPackage.clearRooms();
            searchUiController.setCustomPackage(customPackage);
            searchUiController.searchButtonClicked();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payButtonPressed() throws IOException {
        if(user == null) {
            openLogin();
            return;
        }
        PaymentInfo paymentInfo = new PaymentInfo("-1", expiryMonth.getText()+"/"+expiryYear.getText().substring(2), cardNum.getText(), cvv.getText());
        if(!paymentInfo.validate()) {
            paymentError.setText("Payment error"); // todo setja réttan texta hér
            //return; todo taka út
        }
        BookingController bookingController = new BookingController(tripPackage, user, searchResult, paymentInfo);
        if(!tripPackage.getOutFlights().isEmpty() || !tripPackage.getInFlights().isEmpty()) {
            ArrayList<Passenger> passengers = getPassengers();
            ArrayList<Seat> outSeats = getOutSeats();
            ArrayList<Seat> inSeats = getInSeats();
            bookingController.setPassengers(passengers);
            bookingController.setOutSeats(outSeats);
            bookingController.setInSeats(inSeats);
        }
        bookingController.bookPackage();
        goBackToSearch();
        openUserBookings();
    }

    private ArrayList<Seat> getInSeats() {
        ArrayList<Seat> seats = new ArrayList<>();
        Info information = Info.getInstance();
        for(int i = 0; i < tripPackage.getNumAdults(); i++) {
            String seatNr = ((Button)flightInSeat.getChildren().get(2*i+1)).getText();
            for(Seat s : information.getSelectedSeatsIn()){
                if(seatNr.equals(s.getSeatNumber())) {
                    seats.add(s);
                    System.out.println(s.getSeatNumber());
                }
            }
        }
        for(int i = 0; i < tripPackage.getNumChildren(); i++) {
            String seatNr = ((Button)flightInSeatC.getChildren().get(2*i)).getText();
            for(Seat s : information.getSelectedSeatsIn()){
                if(seatNr.equals(s.getSeatNumber())) {
                    seats.add(s);
                    System.out.println(s.getSeatNumber());
                }
            }
        }
        return seats;
    }

    private ArrayList<Seat> getOutSeats() {
        ArrayList<Seat> seats = new ArrayList<>();
        Info information = Info.getInstance();
        for(int i = 0; i < tripPackage.getNumAdults(); i++) {
            String seatNr = ((Button)flightOutSeat.getChildren().get(2*i+1)).getText();
            for(Seat s : information.getSelectedSeatsOut()){
                if(seatNr.equals(s.getSeatNumber())) {
                    seats.add(s);
                    System.out.println(s.getSeatNumber());
                }
            }
        }
        for(int i = 0; i < tripPackage.getNumChildren(); i++) {
            String seatNr = ((Button)flightOutSeatC.getChildren().get(2*i)).getText();
            for(Seat s : information.getSelectedSeatsOut()){
                if(seatNr.equals(s.getSeatNumber())) {
                    seats.add(s);
                    System.out.println(s.getSeatNumber());
                }
            }
        }
        return seats;
    }

    private ArrayList<Passenger> getPassengers() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        for(int i = 0; i < tripPackage.getNumAdults(); i++) {
            String kennitala = "";
            if(((TextField)adultsFirstNameVB.getChildren().get(2*i+1)).getText().equals(user.getFirstName())) {
                kennitala = user.getSsNum();
            }
            Passenger p = new Passenger(-1, ((TextField)adultsFirstNameVB.getChildren().get(2*i+1)).getText(), ((TextField)adultsLastNameVB.getChildren().get(2*i+1)).getText(), kennitala, user.getEmail(), "");
            p.setLuggage(((CheckBox)adultsLuggageVB.getChildren().get(2*i+1)).isSelected());
            p.setInsurance(((CheckBox)adultsLuggageVB.getChildren().get(2*i+1)).isSelected());
            passengers.add(p);
        }
        for(int i = 0; i < tripPackage.getNumChildren(); i++) {
            Passenger p = new Passenger(-1, ((TextField)childrenFirstNameVB.getChildren().get(2*i)).getText(), ((TextField)childrenLastNameVB.getChildren().get(2*i)).getText(), "", user.getEmail(), "");
            p.setLuggage(((CheckBox)childrenLuggageVB.getChildren().get(2*i)).isSelected());
            p.setInsurance(((CheckBox)childrenInsuranceVB.getChildren().get(2*i)).isSelected());
            passengers.add(p);
        }
        return passengers;
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

    private String displayFlight(Flight f) {
        return f.getFlightNo() + " from " + f.getDeparture().getFullName() + " to " + f.getArrival().getFullName();
    }

    private String displayDateTime(Flight f) {
        LocalDateTime d = f.getDepartureTime();
        LocalDateTime a = f.getArrivalTime();
        return "on "+d.getDayOfMonth()+"."+d.getMonthValue()+"."+d.getYear()+" at "+d.getHour()+":"+d.getMinute()+" - arrival approx. "+a.getHour()+":"+a.getMinute();
    }
}
