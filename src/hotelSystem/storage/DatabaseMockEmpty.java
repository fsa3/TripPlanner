package hotelSystem.storage;

import hotelSystem.entities.Accommodation;

import java.util.ArrayList;

public abstract class DatabaseMockEmpty implements Database {

    ArrayList<Accommodation> accommodations;

    public DatabaseMockEmpty(ArrayList<Accommodation> mockData) {
        this.accommodations = mockData;
    }

    @Override
    public ArrayList<Accommodation> getAllHotels() {
        return accommodations;
    }
    @Override
    public ArrayList<Accommodation> getHotelsByLocation(String location) {
        return null;
    }

    @Override
    public ArrayList<Accommodation> getHotelsByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Accommodation> getHotelsByRating(double minRating) {
        return null;
    }
}
