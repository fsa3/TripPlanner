package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Flight {
    private String flightNum;
    private LocalDate date;
    private String depCity;
    private String destCity;

    public Flight(String flightNum, LocalDate date, String depCity, String destCity) {
        this.flightNum = flightNum;
        this.date = date;
        this.depCity = depCity;
        this.destCity = destCity;
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

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    @Override
    public String toString() {
        return flightNum + " from " + depCity + " to " + destCity + " on " + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightNum.equals(flight.flightNum) && date.equals(flight.date) && depCity.equals(flight.depCity) && destCity.equals(flight.destCity);
    }
}
