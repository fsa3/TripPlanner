package hotelSystem.storage;


import hotelSystem.entities.Accommodation;

import java.sql.Date;
import java.util.ArrayList;

public class DatabaseMock implements Database {
    ArrayList<Accommodation> accommodations;

    public DatabaseMock(ArrayList<Accommodation> mockData) {
        this.accommodations = mockData;
    }

    @Override
    public ArrayList<Accommodation> getAllHotels() {
        return accommodations;
    }
    @Override
    public ArrayList<Accommodation> getHotelsByLocation(String location) {
        // sql mun sjá um þetta þegar database er implementað
        ArrayList<Accommodation> result = new ArrayList<>();
        if (location.equals("")) { return result; };
        for (Accommodation acc : accommodations) {
            // contains er case sensitive, gerum leitina það ekki
            if(acc.getLocation().toLowerCase().contains(location.toLowerCase())) {
                result.add(acc);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Accommodation> getHotelsByName(String name) {
        // sql mun sjá um þetta þegar database er implementað
        ArrayList<Accommodation> result = new ArrayList<>();
        if (name.equals("")) { return result; };
        for (Accommodation acc : accommodations) {
            // contains er case sensitive, gerum leitina það ekki
            if(acc.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(acc);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Accommodation> getHotelsByRating(double minRating) {
        // where price > $1 I guess

        ArrayList<Accommodation> result = new ArrayList<>();
        for (Accommodation acc : accommodations) {
            System.out.println(acc.getRating());
            if(acc.getRating() >= minRating) {
                result.add(acc);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Accommodation> getHotelsByTimePeriod(Date from, Date to) {
        return accommodations;
    }


}
