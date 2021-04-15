package flightSystem.flightplanner.controllers;

import flightSystem.flightplanner.data.FlDataConnection;
import flightSystem.flightplanner.entities.Airport;
import flightSystem.flightplanner.entities.Booking;
import flightSystem.flightplanner.entities.Flight;
import flightSystem.flightplanner.entities.Person;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FlightSearchController{
    private static FlightSearchController instance = null;
    private FlDataConnection dataConnection = null;
    private FlightSearchController(){
    }

    public static FlightSearchController getInstance(){
        if(instance == null){
            instance = new FlightSearchController();
        }
        return instance;
    }

    public void setConnection(FlDataConnection dataConnection){
        this.dataConnection = dataConnection;
    }

    public Flight searchFlightById(int id) throws Exception{
        return dataConnection.getFlightById(id);
    }
    public Person searchPerson(int userId) throws Exception{
        return dataConnection.getPerson(userId);
    }

    public Booking searchBooking(int id) throws Exception{
        return dataConnection.getBooking(id);
    }

    public ArrayList<Airport> searchAirports() throws Exception{
        return dataConnection.getAirports();
    }
}