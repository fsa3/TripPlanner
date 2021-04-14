package entities;

import flightSystem.flightplanner.entities.Airport;
import flightSystem.flightplanner.entities.Flight;

import java.time.LocalDateTime;

public class FlightBooking extends Booking {
    private Flight flight;
    private String flightNum;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Airport departureLocation;
    private Airport destination;
    private String seat;

    public FlightBooking(Flight flight) {
        this.flight = flight;
        flightNum = flight.getFlightNo();
        departureTime = flight.getDepartureTime();
        arrivalTime = flight.getArrivalTime();
        departureLocation = flight.getDeparture();
        destination = flight.getArrival();
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureLocation() {
        return departureLocation.toString();
    }

    public void setDepartureLocation(Airport departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestination() {
        return destination.toString();
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

}
