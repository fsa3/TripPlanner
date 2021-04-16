package hotelSystem.storage;

import hotelSystem.entities.Accommodation;

import java.util.ArrayList;


public interface Database {
    ArrayList<Accommodation> getAllHotels() throws Exception;

    ArrayList<Accommodation> getHotelsByLocation(String location) throws Exception;

    ArrayList<Accommodation> getHotelsByRating(double minRating) throws Exception;

    ArrayList<Accommodation> getHotelsByName(String name) throws Exception;

}

