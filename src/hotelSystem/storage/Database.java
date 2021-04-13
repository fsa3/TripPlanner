package hotelSystem.storage;

import hotelSystem.entities.Accommodation;

import java.sql.Date;
import java.util.ArrayList;

public interface Database  {
    ArrayList<Accommodation> getAllHotels();
    ArrayList<Accommodation> getHotelsByLocation(String location);
    ArrayList<Accommodation> getHotelsByTimePeriod(Date from, Date to);
    ArrayList<Accommodation> getHotelsByRating(double minRating);
    ArrayList<Accommodation> getHotelsByName(String name);
}

