package entities;

import java.time.LocalDate;

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
}
