package entities;

import data.DataConnection;
import dayTripSystem.Trip;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class TripPackage extends SearchResult{
    private String name;
    private double price;
    private SearchResult masterSearch;
    private boolean customPackage = true;
    private User user;

    public TripPackage(String name, SearchResult search, User user) {
        super(search.getStartDate(), search.getEndDate(), search.getDepCity(), search.getDestCity(), search.getNumAdults(), search.getNumChildren());
        this.name = name;
        masterSearch = search;
        this.user = user;
        outFlights = new ArrayList<>();
        inFlights = new ArrayList<>();
        hotels = new ArrayList<>();
        dayTrips = new ArrayList<>();
        rooms = new ArrayList<>();
    }

    public void addInFlight(Flight f) {
        inFlights.add(f);
    }

    public void removeInFlight(Flight f) {
        inFlights.remove(f);
    }

    public void addOutFlight(Flight f) {
        outFlights.add(f);
    }

    public void removeOutFlight(Flight f) {
        outFlights.remove(f);
    }

    public void addHotel(Accommodation h) {
        hotels.add(h);
    }

    public void removeHotel(Accommodation h) {
        hotels.remove(h);
    }

    public void addRoom(Room r) {
        rooms.add(r);
    }

    public void removeRoom(Room r) {
        rooms.remove(r);
    }

    public void addDayTrip(Trip dt) {
        dayTrips.add(dt);
    }

    public void removeDayTrip(Trip dt) {
        dayTrips.remove(dt);
    }

    public void removeAllOutFlights() {
        outFlights.clear();
    }

    public void removeAllInFlights() {
        inFlights.clear();
    }

    public void removeAllHotels() {
        hotels.clear();
    }

    public void removeAllDayTrips() {
        dayTrips.clear();
    }

    public boolean isCustom() {
        return customPackage;
    }

    public void changeToCustom() {
        if(!customPackage) customPackage = true;
    }

    public void bestValue() {
        if(startDate.equals(endDate)) findNonOverlappingFlights();
        else {
            if (!masterSearch.getOutFlights().isEmpty()) {
                Flight outFlight = masterSearch.getOutFlights().get(0);
                for (Flight f : masterSearch.getOutFlights()) {
                    if (f.getDepartureTime().compareTo(outFlight.getDepartureTime()) > 0) {
                        outFlight = f;
                    }
                }
                outFlights.add(outFlight);
            }
            if (!masterSearch.getInFlights().isEmpty()) {
                Flight inFlight = masterSearch.getInFlights().get(0);
                for (Flight f : masterSearch.getInFlights()) {
                    if (f.getDepartureTime().compareTo(inFlight.getDepartureTime()) > 0) {
                        inFlight = f;
                    }
                }
                inFlights.add(inFlight);
            }
        }

        getCheapestHotelRooms();

        if(!masterSearch.getDayTrips().isEmpty()) {
            ArrayList<String> tripNames = new ArrayList<>();
            System.out.println(user);
            if(user != null && user.getFavoriteActivity() != null) {
                Trip cheapestFav = null;
                int lowestFavPrice = (int) Double.POSITIVE_INFINITY;
                for(Trip t : masterSearch.getDayTrips()) {
                    if(t.getCategory().equals(user.getFavoriteActivity()) && t.getPrice() < lowestFavPrice) {
                        cheapestFav = t;
                        lowestFavPrice = t.getPrice();
                    }
                }
                tripNames.add(cheapestFav.getCategory());
                dayTrips.add(cheapestFav);
            }
            for (int i = 0; i < 2; i++) {
                Trip cheapestTrip = null;
                int lowestPrice = (int) Double.POSITIVE_INFINITY;
                for (Trip t : masterSearch.getDayTrips()) {
                    if (t.getPrice() < lowestPrice && !tripNames.contains(t.getCategory())) {
                        cheapestTrip = t;
                        lowestPrice = t.getPrice();
                    }
                }
                if(cheapestTrip != null) {
                    dayTrips.add(cheapestTrip);
                    tripNames.add(cheapestTrip.getCategory());
                }
            }
        }
        customPackage = false;
        calculatePrice();
    }

    private void findNonOverlappingFlights() {
        if(!masterSearch.getOutFlights().isEmpty()) {
            Flight outFlight = masterSearch.getOutFlights().get(0);
            for (Flight f : masterSearch.getOutFlights()) {
                if (f.getDepartureTime().compareTo(outFlight.getDepartureTime()) < 0) {
                    outFlight = f;
                }
            }
            outFlights.add(outFlight);
        }
        if(!masterSearch.getInFlights().isEmpty()) {
            Flight inFlight = masterSearch.getInFlights().get(0);
            for (Flight f : masterSearch.getInFlights()) {
                if (f.getDepartureTime().compareTo(inFlight.getDepartureTime()) > 0) {
                    inFlight = f;
                }
            }
            inFlights.add(inFlight);
        }
    }

    private void getCheapestHotelRooms() {
        if(!masterSearch.getHotels().isEmpty()) {
            TreeMap<Accommodation, ArrayList<Room>> hotelsCheapestRoomComb = new TreeMap<>();
            for (Accommodation h : masterSearch.getHotels()) {
                if(user != null) {
                    if(h.getRating() < user.getMinHotelRating()) continue;
                }
                hotelsCheapestRoomComb.put(h, new ArrayList<Room>());
                ArrayList<Room> availableRooms = h.getAvailableRooms(DataConnection.localDateToDate(startDate), DataConnection.localDateToDate(endDate));
                Room cheapestRoomInHotel = availableRooms.get(0);
                for (Room r : availableRooms) {
                    if (r.getCap() >= numAdults + numChildren && r.getPrice() < cheapestRoomInHotel.getPrice()) {
                        cheapestRoomInHotel = r;
                    }
                }
                if (cheapestRoomInHotel.getCap() < numChildren + numAdults) {
                    int currentCap = 0;
                    while (currentCap < numAdults + numChildren) {
                        Room currentCheapestRoom = null;
                        double currentCheapestRoomPrice = Double.POSITIVE_INFINITY;
                        for (Room r : availableRooms) {
                            if (r.getPrice() < currentCheapestRoomPrice && !hotelsCheapestRoomComb.get(h).contains(r)) {
                                currentCheapestRoom = r;
                                currentCheapestRoomPrice = r.getPrice();
                            }
                        }
                        if(currentCheapestRoom == null) continue;
                        ArrayList<Room> hotelCurrentRoomList = hotelsCheapestRoomComb.get(h);
                        hotelCurrentRoomList.add(currentCheapestRoom);
                        hotelsCheapestRoomComb.put(h, hotelCurrentRoomList);
                        currentCap += currentCheapestRoom.getCap();
                    }
                } else {
                    ArrayList<Room> hotelCurrentRoomList = hotelsCheapestRoomComb.get(h);
                    hotelCurrentRoomList.add(cheapestRoomInHotel);
                    hotelsCheapestRoomComb.put(h, hotelCurrentRoomList);
                }
            }
            Accommodation cheapestHotel = null;
            double lowestPrice = Double.POSITIVE_INFINITY;
            for (Accommodation h : masterSearch.getHotels()) {
                if(user != null) {
                    if(h.getRating() < user.getMinHotelRating()) continue;
                }
                ArrayList<Room> rooms = hotelsCheapestRoomComb.get(h);
                int roomsTotalCap = totalCap(rooms);
                if(roomsTotalCap > numChildren+numAdults) {
                    Room smallestRoom = smallestRoom(rooms);
                    if(roomsTotalCap-smallestRoom.getCap() >= numAdults+numChildren) {
                        rooms.remove(smallestRoom);
                    }
                }

                double roomsPrice = 0;
                for (Room r : rooms) {
                    roomsPrice += r.getPrice();
                }
                if (roomsPrice < lowestPrice) {
                    lowestPrice = roomsPrice;
                    cheapestHotel = h;
                }
            }
            hotels.add(cheapestHotel);
            rooms.addAll(hotelsCheapestRoomComb.get(cheapestHotel));
        }
    }

    public void highEnd() {
        if(startDate.equals(endDate)) findNonOverlappingFlights();
        else {
            if (!masterSearch.getOutFlights().isEmpty()) {
                Flight outFlight = masterSearch.getOutFlights().get(0);
                for (Flight f : masterSearch.getOutFlights()) {
                    if (f.getDepartureTime().compareTo(outFlight.getDepartureTime()) < 0) {
                        outFlight = f;
                    }
                }
                outFlights.add(outFlight);
            }
            if (!masterSearch.getInFlights().isEmpty()) {
                Flight inFlight = masterSearch.getInFlights().get(0);
                for (Flight f : masterSearch.getInFlights()) {
                    if (f.getDepartureTime().compareTo(inFlight.getDepartureTime()) < 0) {
                        inFlight = f;
                    }
                }
                inFlights.add(inFlight);
            }
        }

        getMostExpensiveRooms();

        if(!masterSearch.getDayTrips().isEmpty()) {
            ArrayList<String> tripNames = new ArrayList<>();
            System.out.println(user);
            if(user != null && user.getFavoriteActivity() != null) {
                Trip mostExpensiveFav = null;
                int highestFavPrice = (int) Double.NEGATIVE_INFINITY;
                for(Trip t : masterSearch.getDayTrips()) {
                    if(t.getCategory().equals(user.getFavoriteActivity()) && t.getPrice() > highestFavPrice) {
                        mostExpensiveFav = t;
                        highestFavPrice = t.getPrice();
                    }
                }
                tripNames.add(mostExpensiveFav.getCategory());
                dayTrips.add(mostExpensiveFav);
            }
            for (int i = 0; i < 2; i++) {
                Trip mostExpensiveTrip = null;
                int highestPrice = (int) Double.NEGATIVE_INFINITY;
                for (Trip t : masterSearch.getDayTrips()) {
                    if (t.getPrice() > highestPrice && !tripNames.contains(t.getCategory())) {
                        mostExpensiveTrip = t;
                        highestPrice = t.getPrice();
                    }
                }
                if(mostExpensiveTrip != null) {
                    dayTrips.add(mostExpensiveTrip);
                    tripNames.add(mostExpensiveTrip.getCategory());
                }
            }
        }
        customPackage = false;
        calculatePrice();
    }

    private void getMostExpensiveRooms() {
        if(!masterSearch.getHotels().isEmpty()) {
            TreeMap<Accommodation, ArrayList<Room>> hotelsMostExpensiveRoomComb = new TreeMap<>();
            for (Accommodation h : masterSearch.getHotels()) {
                if(user != null) {
                    if(h.getRating() < user.getMinHotelRating()) continue;
                }
                hotelsMostExpensiveRoomComb.put(h, new ArrayList<Room>());
                ArrayList<Room> availableRooms = h.getAvailableRooms(DataConnection.localDateToDate(startDate), DataConnection.localDateToDate(endDate));
                Room mostExpensiveRoomInHotel = availableRooms.get(0);
                for (Room r : availableRooms) {
                    if (r.getCap() >= numAdults + numChildren && r.getPrice() > mostExpensiveRoomInHotel.getPrice()) {
                        mostExpensiveRoomInHotel = r;
                    }
                }
                if (mostExpensiveRoomInHotel.getCap() < numChildren + numAdults) {
                    int currentCap = 0;
                    while (currentCap < numAdults + numChildren) {
                        Room currentMostExpensiveRoom = null;
                        double currentMostExpensiveRoomPrice = Double.NEGATIVE_INFINITY;
                        for (Room r : availableRooms) {
                            if (r.getPrice() > currentMostExpensiveRoomPrice && !hotelsMostExpensiveRoomComb.get(h).contains(r)) {
                                currentMostExpensiveRoom = r;
                                currentMostExpensiveRoomPrice = r.getPrice();
                            }
                        }
                        if(currentMostExpensiveRoom == null) continue;
                        ArrayList<Room> hotelCurrentRoomList = hotelsMostExpensiveRoomComb.get(h);
                        hotelCurrentRoomList.add(currentMostExpensiveRoom);
                        hotelsMostExpensiveRoomComb.put(h, hotelCurrentRoomList);
                        currentCap += currentMostExpensiveRoom.getCap();
                    }
                } else {
                    ArrayList<Room> hotelCurrentRoomList = hotelsMostExpensiveRoomComb.get(h);
                    hotelCurrentRoomList.add(mostExpensiveRoomInHotel);
                    hotelsMostExpensiveRoomComb.put(h, hotelCurrentRoomList);
                }
            }
            Accommodation mostExpensiveHotel = null;
            double highestPrice = Double.NEGATIVE_INFINITY;
            for (Accommodation h : masterSearch.getHotels()) {
                if(user != null) {
                    if(h.getRating() < user.getMinHotelRating()) continue;
                }
                ArrayList<Room> rooms = hotelsMostExpensiveRoomComb.get(h);
                int roomsTotalCap = totalCap(rooms);
                if(roomsTotalCap > numChildren+numAdults) {
                    Room smallestRoom = smallestRoom(rooms);
                    if(roomsTotalCap-smallestRoom.getCap() >= numAdults+numChildren) {
                        rooms.remove(smallestRoom);
                    }
                }

                double roomsPrice = 0;
                for (Room r : rooms) {
                    roomsPrice += r.getPrice();
                }
                if (roomsPrice > highestPrice) {
                    highestPrice = roomsPrice;
                    mostExpensiveHotel = h;
                }
            }
            hotels.add(mostExpensiveHotel);
            rooms.addAll(hotelsMostExpensiveRoomComb.get(mostExpensiveHotel));
        }
    }

    public void allIn() {
        findNonOverlappingFlights();

        getCheapestHotelRooms();

        ArrayList<String> tripNames = new ArrayList<>();
        ArrayList<Trip> allTrips = new ArrayList<>(masterSearch.getDayTrips());
        Collections.shuffle(allTrips);
        for(Trip t : allTrips) {
            if(tripNames.size() < 5 && !tripNames.contains(t.getCategory())) {
                dayTrips.add(t);
                tripNames.add(t.getCategory());
            }
        }
        customPackage = false;
        calculatePrice();
    }

    public void relaxation() {
        if(startDate.equals(endDate)) findNonOverlappingFlights();
        else {
            if (!masterSearch.getOutFlights().isEmpty()) {
                Flight outFlight = masterSearch.getOutFlights().get(0);
                for (Flight f : masterSearch.getOutFlights()) {
                    if (f.getDepartureTime().compareTo(outFlight.getDepartureTime()) > 0) {
                        outFlight = f;
                    }
                }
                outFlights.add(outFlight);
            }
            if (!masterSearch.getInFlights().isEmpty()) {
                Flight inFlight = masterSearch.getInFlights().get(0);
                for (Flight f : masterSearch.getInFlights()) {
                    if (f.getDepartureTime().compareTo(inFlight.getDepartureTime()) < 0) {
                        inFlight = f;
                    }
                }
                inFlights.add(inFlight);
            }
        }

        getMostExpensiveRooms();

        customPackage = false;
        calculatePrice();
    }

    private Room smallestRoom(ArrayList<Room> rooms) {
        if(rooms.isEmpty()) return null;
        Room smallestRoom = rooms.get(0);
        for(Room r : rooms) {
            if(r.getCap() < smallestRoom.getCap()) {
                smallestRoom = r;
            }
        }
        return smallestRoom;
    }

    private int totalCap(ArrayList<Room> rooms) {
        int totalCap = 0;
        for(Room r : rooms) {
            totalCap += r.getCap();
        }
        return totalCap;
    }

    public void testPackage() {
        if(!masterSearch.getOutFlights().isEmpty()) outFlights.add(masterSearch.getOutFlights().get(0));
        if(!masterSearch.getInFlights().isEmpty()) inFlights.add(masterSearch.getInFlights().get(0));
        if(!masterSearch.getHotels().isEmpty()) {
            hotels.add(masterSearch.getHotels().get(0));
            ArrayList<Room> availableRooms = hotels.get(0).getAvailableRooms(DataConnection.localDateToDate(startDate), DataConnection.localDateToDate(endDate));
            if(!availableRooms.isEmpty()) rooms.add(availableRooms.get(0));
        }
        dayTrips.addAll(masterSearch.getDayTrips());
        calculatePrice();
    }

    public void emptyPackage() {
        outFlights.clear();
        inFlights.clear();
        hotels.clear();
        dayTrips.clear();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void clearRooms() {
        rooms.clear();
    }

    public void calculatePrice() {
        int p = 0;
        for(Flight f : outFlights) {
            p += f.getPrice();
        }
        for(Flight f : inFlights) {
            p += f.getPrice();
        }
        for(Room r : rooms) {
            p += r.getPrice();
        }
        for(Trip t : dayTrips) {
            p += t.getPrice();
        }
        price = p;
    }

    @Override
    public String toString() {
        return "TripPackage{" +
                "outFlights=" + outFlights +
                ", inFlights=" + inFlights +
                ", hotels=" + hotels +
                ", rooms=" + rooms +
                ", dayTrips=" + dayTrips +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", customPackage=" + customPackage +
                '}';
    }
}
