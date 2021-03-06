package ui.controllers;

import data.DataConnection;
import dayTripSystem.Account;
import dayTripSystem.BookingController;
import entities.DayTripBooking;
import entities.FlightBooking;
import entities.HotelBooking;
import entities.User;
import flightSystem.flightplanner.controllers.FlightBookingController;
import flightSystem.flightplanner.data.FlDataConnection;
import flightSystem.flightplanner.entities.Booking;
import flightSystem.flightplanner.entities.Flight;
import flightSystem.flightplanner.entities.Passenger;
import flightSystem.flightplanner.entities.Seat;
import hotelSystem.controllers.AccommodationBookingController;
import hotelSystem.entities.Accommodation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class UserBookingsController {
    @FXML private BorderPane userBookings;
    @FXML private VBox bookedPackagesView;

    private User user;
    private ArrayList<Integer> userBookingIds;
    private ArrayList<Booking> flightBookings;
    private ArrayList<HotelBooking> hotelBookings;
    private ArrayList<DayTripBooking> dayTripBookings;

    public void initialize() {

    }

    public void setUser(User user) {
        this.user = user;
        try {
            displayBookings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayBookings() throws Exception {
        bookedPackagesView.setSpacing(10);
        // get user bookings
        DataConnection dc = new DataConnection();
        FlDataConnection flightData = FlDataConnection.getInstance();
        flightBookings = flightData.getBookings(user.getId());
        userBookingIds = dc.getUserBookings(user);
        dayTripBookings = dc.getDayTripBookings(user);
        hotelBookings = dc.getHotelBookings(user);

        for(Integer id : userBookingIds) {
            Double price = dc.getBookingPrice(id);
            VBox bookingVB = new VBox();

            HBox bookingHeader = new HBox();
            Label bookingTitle = new Label("Booking " + id + " for " + dc.getNumPeople(id) + " people");
            bookingTitle.getStyleClass().add("title1-bookings");
            Button editBooking = new Button("Cancel booking");
            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            editBooking.getStyleClass().add("blue-button");
            bookingHeader.getChildren().addAll(bookingTitle, spacer, editBooking);
            bookingVB.getChildren().add(bookingHeader);

            // flights and passengers
            HBox fp = new HBox();
            VBox flightsVB = new VBox();
            flightsVB.setPrefWidth(280);
            Label flightsHeader = new Label("Flights");
            flightsHeader.getStyleClass().add("title2");
            flightsHeader.setVisible(false);
            flightsVB.getChildren().add(flightsHeader);

            ArrayList<Integer> flightBookingsIds = dc.getFlightBookinIds(id);
            ArrayList<Flight> flights = new ArrayList<>();
            for(Booking b : flightBookings) {
                if(flightBookingsIds.contains(b.getID())) {
                    if(!flights.contains(b.getFlight())) flights.add(b.getFlight());
                }
            }

            if(!flights.isEmpty()) flightsHeader.setVisible(true);
            for(Flight f : flights) {
                Label flightLabel1 = new Label(displayFlight(f));
                Label flightLabel2 = new Label(displayDateTime(f));
                flightLabel1.getStyleClass().add("booking-small");
                flightLabel2.getStyleClass().add("booking-small");
                flightsVB.getChildren().addAll(flightLabel1, flightLabel2, createSpacer());
            }

            VBox passengersVB = new VBox();
            passengersVB.setPrefWidth(400);
            Label passengersHeader = new Label("Passengers");
            passengersHeader.getStyleClass().add("title2");
            passengersHeader.setVisible(false);
            passengersVB.getChildren().add(passengersHeader);


            ArrayList<Passenger> passengersToDisplay = new ArrayList<>();
            TreeMap<String, Seat> passengerSeats = new TreeMap<>();
            for(Booking b : flightBookings) {
                if(flightBookingsIds.contains(b.getID())) {
                    Passenger p = b.getPassenger();
                    String passengerKey = p.getFirstName()+b.getFlight().getID();
                    passengerSeats.put(passengerKey, b.getSeat());
                    if(!passengersToDisplay.contains(p)) passengersToDisplay.add(p);
                }
            }
            if(!passengersToDisplay.isEmpty()) passengersHeader.setVisible(true);
            for(Passenger p : passengersToDisplay) {
                HBox pHB = new HBox();
                VBox passengerNames = new VBox();
                VBox passengerSeat1 = new VBox();
                VBox passengerSeat2 = new VBox();
                VBox passengerLuggage = new VBox();
                VBox passengerInsurance = new VBox();

                Label passengerName = new Label(p.getFirstName() + " " + p.getLastName());
                passengerName.setWrapText(true);
                passengerName.setPrefWidth(160);
                passengerName.setMaxWidth(160);
                passengerName.setPrefHeight(Label.USE_COMPUTED_SIZE);
                passengerName.getStyleClass().add("booking-small");
                passengerNames.getChildren().add(passengerName);
                if(!flights.isEmpty()) {
                    Label seat1 = new Label(passengerSeats.get(p.getFirstName()+flights.get(0).getID()).getSeatNumber());
                    seat1.setPrefWidth(40);
                    seat1.getStyleClass().add("booking-small");
                    passengerSeat1.getChildren().add(seat1);
                }
                if(flights.size() > 1) {
                    Label seat2 = new Label(passengerSeats.get(p.getFirstName()+flights.get(1).getID()).getSeatNumber());
                    seat2.setPrefWidth(40);
                    seat2.getStyleClass().add("booking-small");
                    passengerSeat2.getChildren().add(seat2);
                }
                String luggage = "no luggage";
                String insurance = "no insurance";
                if(p.isLuggage()) luggage = "luggage";
                if(p.isInsurance()) insurance = "insurance";
                Label luggageLabel = new Label(luggage);
                Label insuranceLabel = new Label(insurance);
                luggageLabel.setPrefWidth(70);
                insuranceLabel.setPrefWidth(70);
                luggageLabel.setStyle("-fx-font-size: 10px");
                insuranceLabel.setStyle("-fx-font-size: 10px");
                passengerLuggage.getChildren().add(luggageLabel);
                passengerInsurance.getChildren().add(insuranceLabel);

                pHB.getChildren().addAll(passengerNames, passengerSeat1, passengerSeat2, passengerLuggage, passengerInsurance);
                passengersVB.getChildren().add(pHB);
            }


            fp.getChildren().addAll(flightsVB, passengersVB);
            bookingVB.getChildren().add(fp);

            // hotel
            Label hotelHeader = new Label("Hotel");
            hotelHeader.getStyleClass().add("title2");
            hotelHeader.setVisible(false);
            bookingVB.getChildren().add(hotelHeader);
            for(HotelBooking hb : hotelBookings) {
                if(hb.getBookingId() == id) {
                    hotelHeader.setVisible(true);
                    HBox hotelHB = new HBox();
                    Label h1 = new Label("Hotel:");
                    h1.setStyle("-fx-font-weight: bold");
                    Label h2 = new Label(hb.getHotelName());
                    Label h3 = new Label("City:");
                    h3.setPrefWidth(50);
                    h3.setStyle("-fx-font-weight: bold");
                    h3.setPadding(new Insets(0, 10, 0, 0));
                    h3.setAlignment(Pos.BOTTOM_RIGHT);
                    h3.setTextAlignment(TextAlignment.RIGHT);
                    Label h4 = new Label(hb.getCity());
                    Label h5 = new Label("Room:");
                    h5.setPrefWidth(70);
                    h5.setStyle("-fx-font-weight: bold");
                    h5.setPadding(new Insets(0, 10, 0, 0));
                    h5.setAlignment(Pos.BOTTOM_RIGHT);
                    h5.setTextAlignment(TextAlignment.RIGHT);
                    Label h6 = new Label(hb.getRoom().toString());
                    Label h7 = new Label(hb.getNumberOfNights() + " nights");
                    hotelHB.getChildren().addAll(h1,h2,h3,h4,h5,h6,h7);
                    bookingVB.getChildren().add(hotelHB);
                }
            }

            Label dtHeader = new Label("Day Trips");
            dtHeader.getStyleClass().add("title2");
            dtHeader.setVisible(false);
            bookingVB.getChildren().add(dtHeader);
            for(DayTripBooking dtB : dayTripBookings) {
                if(dtB.getBookingId() == id) {
                    dtHeader.setVisible(true);
                    HBox dtHb = new HBox();
                    Label d1 = new Label(dtB.getDayTripName());
                    d1.setPrefWidth(100);
                    Label d2 = new Label(dtB.getCity());
                    d2.setPrefWidth(80);
                    Label d3 = new Label("on " + displayDate(dtB.getDate()));
                    d3.setPrefWidth(150);
                    dtHb.getChildren().addAll(d1,d2,d3);
                    bookingVB.getChildren().add(dtHb);
                }
            }

            Label priceLabel = new Label("Total price: " + price + "$");
            priceLabel.getStyleClass().add("title2");
            bookingVB.getChildren().add(priceLabel);

            bookingVB.getStyleClass().add("packageContainer");
            bookedPackagesView.getChildren().add(bookingVB);

            editBooking.setOnAction((evt) -> {
                // cancel flights
                FlightBookingController flightBookingController = FlightBookingController.getInstance();
                DataConnection dataConnection = new DataConnection();
                for(Booking b : flightBookings) {
                    if(flightBookingsIds.contains(b.getID())) {
                        try {
                            flightBookingController.cancelBooking(b);
                            dataConnection.cancelFlightBooking(new FlightBooking(id, b.getID()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // cancel hotels
                dataConnection.deleteHotelBookingsByBookingId(id);
                AccommodationBookingController hotelBookingController = new AccommodationBookingController();
                for(HotelBooking hb : hotelBookings) {
                    if (hb.getBookingId() == id) {
                        hotelBookingController.removeBooking(new hotelSystem.entities.Booking(hb.getHotel(), hb.getRoom(), DataConnection.localDateToDate(hb.getCheckInDate()), DataConnection.localDateToDate(hb.getCheckOutDate())));
                    }
                }

                // cancel day trips
                int numPeople = dataConnection.getNumPeople(id);
                Account dayTripAccount = new Account("-1", user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getPhoneNumber(), null, null);
                BookingController dayTripBookingController = new BookingController();
                for(DayTripBooking dtB : dayTripBookings) {
                    if (dtB.getBookingId() == id) {
                        dataConnection.deleteDayTripBooking(dtB);
                        dayTripSystem.Booking dayTripSystemBooking = new dayTripSystem.Booking(dtB.getDayTrip(), dayTripAccount, false, 0, true, numPeople);
                        dayTripBookingController.cancelBooking(dayTripSystemBooking);
                    }
                }

                dataConnection.deleteBooking(id);
                dataConnection.closeConnection();
                try {
                    bookedPackagesView.getChildren().clear();
                    displayBookings();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        dc.closeConnection();
    }

    private String displayDate(LocalDate d) {
        return d.getDayOfMonth()+"."+d.getMonthValue()+"."+d.getYear();
    }

    private String displayFlight(Flight f) {
        return f.getFlightNo() + " from " + f.getDeparture().getFullName() + " to " + f.getArrival().getFullName();
    }

    private String displayDateTime(Flight f) {
        LocalDateTime d = f.getDepartureTime();
        LocalDateTime a = f.getArrivalTime();
        return "on "+d.getDayOfMonth()+"."+d.getMonthValue()+"."+d.getYear()+" at "+d.getHour()+":"+d.getMinute()+" - arrival approx. "+a.getHour()+":"+a.getMinute();
    }

    private Node createSpacer() {
        final Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public void closeWindow(ActionEvent actionEvent) {
        ((Stage) userBookings.getScene().getWindow()).close();
    }
}
