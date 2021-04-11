package entities;

import java.time.LocalDate;

public class FlightBooking extends Booking {
    private Flight flight;
    private String flightNum;
    private LocalDate date;
    private String departureLocation;
    private String destination;
    private String seat;

    public FlightBooking(Flight flight) {
        this.flight = flight;
        flightNum = flight.getFlightNum();
        date = flight.getDate();
        departureLocation = flight.getDepCity();
        destination = flight.getDestCity();
    }

    public void cancelBooking() {
        // todo
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

}
