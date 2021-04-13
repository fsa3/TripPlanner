package hotelSystem.entities;

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

    public Room(int roomId, double price, RoomType roomType, int cap) {
        this.roomId = roomId;
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
        occupancies.add(occupancy);
    }

    public ArrayList<Occupancy> getOccupancies() {
        return occupancies;
    }

    public String toString() {
        return roomType.toString() +", kr. " +getPrice();
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
