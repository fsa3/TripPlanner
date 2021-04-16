package hotelSystem.controllers;

import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;
import hotelSystem.storage.Database;
import hotelSystem.storage.DatabaseConnection;

import java.sql.Date;
import java.util.ArrayList;


public class AccommodationSearchController {
    private Database data;
    private ArrayList<Accommodation> accommodations;

    public AccommodationSearchController(Database data) {

        this.data = data;
        try {
            accommodations = data.getAllHotels();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public ArrayList<Accommodation> findByLocation(String location) {
        try {
            return data.getHotelsByLocation(location);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null; // laga!!
    }

    public ArrayList<Accommodation> findByRating(double minRating) {
        try {
            return data.getHotelsByRating(minRating);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null; // laga!! hotfix til ad geta buildad eftir DatabaseConnection impladi Database Interface
    }


    public ArrayList<Room> filterRoomsByPrice(Accommodation accommodation, double maxPrice) {
        ArrayList<Room> result = new ArrayList<>();

        for (Room room : accommodation.getAllRooms()) {
            if (room.getPrice() <= maxPrice) {
                result.add(room);
            }
        }
        return result;
    }

    public ArrayList<Room> filterRoomsByPeriod(Accommodation accommodation, Date from, Date to) {
        // pæling hvort við viljum leyfa from og to að vera null
        if (accommodation != null && from != null && to != null) {
            return accommodation.getAvailableRooms(from, to);
        }
        // else
        return new ArrayList<>();
    }

    public ArrayList<Room> filterRoomsByPriceAndPeriod(Accommodation accommodation, double maxPrice, Date from, Date to) {
        ArrayList<Room> result = new ArrayList<>();
        result.addAll(accommodation.getAvailableRooms(from, to));
        result.retainAll(filterRoomsByPrice(accommodation, maxPrice));
        return result;
    }

    public ArrayList<Accommodation> findByName(String name) {
        try {
            return data.getHotelsByName(name);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new ArrayList<>(); /// sama og hitt!! aLAGA

    }


    // endurrada args

    /**
     * Fall sem leitar að gistingu eftir ákveðnum skilyrðum. Sjá nánar fyrir hvern og einn param ef á bara að taka tillit
     * til sumra þeirra.
     *
     * @param location  String, tómi strengurinn ef á ekki að taka tillit til
     * @param minRating double, <=0.0 ef á ekki að taka tillit til
     * @param name      String, tómi strengurinn ef á ekki að taka tillit til
     * @return ArrayList<Accommodation>, result úr leit. Ath. að Accommodations innihalda method
     * getAvailableRooms(Date from, Date to) svo það sér um að vita availability
     */
    public ArrayList<Accommodation> search(String location, double minRating,
                                           String name) {

        // init
        try {
            ArrayList<Accommodation> theResult = new ArrayList<>(data.getAllHotels());

            ArrayList<Accommodation> nameResult = findByName(name);
            ArrayList<Accommodation> locationResult = findByLocation(location);
            ArrayList<Accommodation> ratingResult = findByRating(minRating);

            // finnum sniðmengi af þeim results úr queries sem innihalda ekki tóma strenginn (eða null í Date)
            // munum alltaf nota minRating og maxPrice, g.r.f. 0 og inf default gildum ef ekki á að leita eftir því
            theResult.retainAll(ratingResult);
            if (!location.equals("")) {
                System.out.println("location");
                theResult.retainAll(locationResult);
            }
            if (!name.equals("")) {
                System.out.println("name");
                theResult.retainAll(nameResult);
            }

            return theResult;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        DatabaseConnection data = new DatabaseConnection();
        AccommodationSearchController sc = new AccommodationSearchController(data);

        try {
            System.out.println(sc.search("reykjavik", 0.0, "hotel"));
        } catch(Exception e) {
            System.err.println(e);
        }

    }
}
