package entities;

import java.util.Date;

public class Flight {
    private String flightNum;
    private Date date;
    private String depCity;
    private String destCity;

    public Flight(String flightNum, Date date, String depCity, String destCity) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
