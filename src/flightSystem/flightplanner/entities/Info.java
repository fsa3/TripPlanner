package flightSystem.flightplanner.entities;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class Info {
    private static Info instance;
    //private User user = new User("user", 2, "Jón Gunnar", "Hannesson", "0606060606", "jgh@hi.is", "1111111");
    private User user;
    private Flight flight;
    private Seat seat;
    private Passenger currentPassenger;
    // T-team additions
    private Button seatButton;
    private ArrayList<Seat> selectedSeatsOut = new ArrayList<>();
    private ArrayList<Seat> selectedSeatsIn = new ArrayList<>();
    // ----------------

    private Info(){

    }

    public static Info getInstance(){
        if(instance == null){
            instance = new Info();
        }
        return instance;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    public void setCurrentPassenger(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

    // T-team additions
    public Button getSeatButton() {
        return seatButton;
    }

    public void setSeatButton(Button seatButton) {
        this.seatButton = seatButton;
    }

    public void addSelectedSeat(Seat s) {
        if(seatButton.getStyleClass().contains("seat-out-button")) {
            selectedSeatsOut.add(s);
        }
        if(seatButton.getStyleClass().contains("seat-in-button")) {
            selectedSeatsIn.add(s);
        }
    }

    public void removeSelectedSeat(Seat s) {
        if(seatButton.getStyleClass().contains("seat-out-button")) {
            selectedSeatsOut.remove(s);
        }
        if(seatButton.getStyleClass().contains("seat-in-button")) {
            selectedSeatsIn.remove(s);
        }
    }

    public boolean isSeatSelected(Seat s) {
        if(seatButton.getStyleClass().contains("seat-out-button")) {
            return selectedSeatsOut.contains(s);
        }
        if(seatButton.getStyleClass().contains("seat-in-button")) {
            return selectedSeatsIn.contains(s);
        }
        return false;
    }
}
