package hotelSystem.entities;

import hotelSystem.storage.DatabaseConnection;

import java.sql.Date;
import java.util.ArrayList;

public class Room {
    private int roomId;
    private RoomType roomType;
    private double price;
    private int cap;
    private ArrayList<Occupancy> occupancies;

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public void setOccupancies(ArrayList<Occupancy> occupancies) {
        this.occupancies = occupancies;
    }

    public Room(int roomId, double price, RoomType roomType, int cap) {
        this.roomId = roomId;
        this.price = price;
        this.cap = cap;
        this.roomType = roomType;
        occupancies = new ArrayList<>();
    }
    public Room(double price, RoomType roomType, int cap) {
      this.price = price;
      this.cap = cap;
      this.roomType = roomType;
      occupancies = new ArrayList<>();
  }

    public boolean isAvailable(Date date) {
        for (Occupancy occupancy : occupancies) {
            if (occupancy.isOccupied(date)) {
                return false;
            }
        }
        return true;
    }

    public void addOccupancy(Occupancy occupancy) {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            connection.createOccupancy(this.roomId, occupancy.getDateFrom(), occupancy.getDateTo());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        occupancies.add(occupancy);
    }

    public void removeOccupancy(int roomId, Date from, Date to) {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            connection.deleteOccupancy(roomId, from, to);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        getOccupancies();
    }

    public ArrayList<Occupancy> getOccupancies() {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<Occupancy> dbList = new ArrayList<>();
        try {
            dbList = connection.getAllOccupanciesByRoomId(this.roomId);
            setOccupancies(dbList);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return occupancies;
    }

    public String toString() {
        return roomType.toString() + ", $USD " + getPrice() + ", " + cap + " pers.";
    }

    public static void main(String[] args) {
        Room room = new Room(1, 10000, RoomType.Single, 1);

        long now = System.currentTimeMillis();
        Date sqlDateFrom = new Date(now);
        Date sqlDateTo = new Date(now + (1000 * 60 * 60 * 24 * 7));

        Date sqlDateFrom2 = new Date(now + (1000 * 60 * 60 * 24 * 11));
        Date sqlDateTo2 = new Date(now + (1000 * 60 * 60 * 24 * 12));

        Occupancy test = new Occupancy(sqlDateFrom, sqlDateTo);
        Occupancy test2 = new Occupancy(sqlDateFrom2, sqlDateTo2);

        room.addOccupancy(test);
        room.addOccupancy(test2);

        Date occtest1 = new Date(now - (1000 * 60 * 60 * 24 * 7));
        Date occtest2 = new Date(now + (1000 * 60 * 60 * 24 * 4));
        Date occtest3 = new Date(now + (1000 * 60 * 60 * 24 * 10));
        Date occtest4 = new Date(now + (1000 * 60 * 60 * 24 * 11));
        Date occtest5 = new Date(now + (1000 * 60 * 60 * 24 * 13));


        System.out.println("Should be true: " + room.isAvailable(occtest1) + " date " + occtest1);
        System.out.println("Should be false: " + room.isAvailable(occtest2) + " date " + occtest2);
        System.out.println("Should be true: " + room.isAvailable(occtest3) + " date " + occtest3);
        System.out.println("Should be false: " + room.isAvailable(occtest4) + " date " + occtest4);
        System.out.println("Should be true: " + room.isAvailable(occtest5) + " date " + occtest5);
    }
}
//siggigauti/video-leiga-support
