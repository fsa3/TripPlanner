package hotelSystem.entities;

import hotelSystem.storage.DatabaseMock;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Accommodation {
    private int id;
    private String name;
    // private ArrayList<>; // arraylist fyrir facilities
    private String location;
    private double rating;
    private ArrayList<Room> roomArrayList;
    private AccType type = AccType.Hotel;
    private ArrayList<String> managerIdArrayList;
    private ArrayList<Review> reviewArrayList;
    private String description;

    // ma baeta vid minni smidum,
    public Accommodation(int id, String name, String location, double rating, ArrayList<Room> roomArrayList, ArrayList<String> managerIdArrayList, ArrayList<Review> reviewArrayList, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.roomArrayList = roomArrayList;
        this.managerIdArrayList = managerIdArrayList;
        this.reviewArrayList = reviewArrayList;
        this.description = description;
    }

    public Accommodation(int id, String name, String location, ArrayList<Room> roomArrayList, ArrayList<String> managerIdArrayList, double rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.roomArrayList = roomArrayList;
        this.managerIdArrayList = managerIdArrayList;
        this.reviewArrayList = new ArrayList<>();
        this.description = "";
        this.rating = rating;
    }

    public Accommodation(int id, String name, String location, ArrayList<Room> roomArrayList, double rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.roomArrayList = roomArrayList;
        this.managerIdArrayList = new ArrayList<>();
        this.reviewArrayList = new ArrayList<>();
        this.description = "";
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<Room> getAllRooms() {
        return roomArrayList;
    }

    public ArrayList<Room> getAvailableRooms(Date from, Date to) {
        ArrayList<Room> availableRooms = new ArrayList<>();

        for (Room room : roomArrayList) {
            if (!isOccupied(room.getOccupancies(), from, to)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    /**
     * @param occupancies listi af occupancies herbergis
     * @param from        byrjun á tíma til skoðunar
     * @param to          lok tíma til skoðunar
     * @return true ef herbergi er upptekið á þessum tíma, false annars
     */
    private boolean isOccupied(ArrayList<Occupancy> occupancies, Date from, Date to) {
        boolean occupied = false;
        for (Occupancy occupancy : occupancies) {
            Date occFrom = occupancy.getDateFrom();
            Date occTo = occupancy.getDateTo();
            // occupied ef occFrom eða occTo er á milli from og to.
            // einnig occupied ef tímabil occupancy nær yfir from og to
            if (occFrom.compareTo(from) >= 0 && occFrom.compareTo(to) <= 0) {
                // occFrom er á milli from og to
                occupied = true;
            }
            if (occTo.compareTo(from) >= 0 && occTo.compareTo(to) <= 0) {
                // occTo er á milli from og to
                occupied = true;
            }

            if (occFrom.compareTo(from) < 0 && occTo.compareTo(to) > 0) {
                // from to er innihaldid í occupancy
                occupied = true;
            }
        }

        return occupied;
    }


    public void setRoomArrayList(ArrayList<Room> roomArrayList) {
        this.roomArrayList = roomArrayList;
    }

    public ArrayList<String> getManagerIdArrayList() {
        return managerIdArrayList;
    }

    public void setManagerIdArrayList(ArrayList<String> managerIdArrayList) {
        this.managerIdArrayList = managerIdArrayList;
    }

    public ArrayList<Review> getReviewArrayList() {
        return reviewArrayList;
    }

    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return id == that.id && Double.compare(that.rating, rating) == 0 && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(roomArrayList, that.roomArrayList) && type == that.type && Objects.equals(managerIdArrayList, that.managerIdArrayList) && Objects.equals(reviewArrayList, that.reviewArrayList) && Objects.equals(description, that.description);
    }
}
