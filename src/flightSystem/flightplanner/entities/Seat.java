package flightSystem.flightplanner.entities;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Seat {
    private SimpleStringProperty seatNumber;
    private SimpleBooleanProperty isBooked;
    public Seat(String seatNumber, boolean isBooked){
        this.seatNumber = new SimpleStringProperty(seatNumber);
        this.isBooked = new SimpleBooleanProperty(isBooked);
    }

    public String getSeatNumber() {
        return seatNumber.get();
    }

    public boolean isBooked() {
        return isBooked.get();
    }

    public void setBooked(boolean booked) {
        isBooked.set(booked);
    }
    public SimpleBooleanProperty getBookedProperty(){
        return isBooked;
    }
    public String toString() {
        return getSeatNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return seatNumber.get().equals(seat.seatNumber.get()) && isBooked.get() == seat.isBooked.get();
    }
}
