package flightSystem.flightplanner.data;

import flightSystem.flightplanner.entities.Booking;
import flightSystem.flightplanner.entities.Flight;
import flightSystem.flightplanner.entities.Person;

import java.sql.SQLException;

public interface FlightDataConnection {
    public Flight getFlightById(int id) throws SQLException;
    public Person getPerson(int id) throws SQLException;
    public Booking getBooking(int id) throws SQLException;
    public void createBooking(Booking booking ) throws SQLException;
    public void updateFood(int id, Boolean newFood) throws SQLException;

}
