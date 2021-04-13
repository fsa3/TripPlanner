package hotelSystem.storage;

import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;
import hotelSystem.entities.RoomType;

import java.sql.Date;
import java.util.ArrayList;

public class DataFactory implements Database {
    public DataFactory() {
    }
    public ArrayList<Accommodation> getAllHotels() {
        DatabaseConnection db = new DatabaseConnection();
        try {
            db.initializeDatabase();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        ArrayList<Accommodation> accommodations = new ArrayList<>();
        ArrayList<String> managerIds = new ArrayList<>();
        managerIds.add("1");
        managerIds.add("2");
        ArrayList<Room> rooms = new ArrayList<>();
        Room room1 = new Room(1, 10000, RoomType.Single, 1);
        Room room2 = new Room(2, 10000, RoomType.Single, 1);
        rooms.add(room1);
        rooms.add(room2);
        Accommodation acc1 = new Accommodation(1, "Hilton", "Reykjavik", rooms, managerIds, 5.0);
        try {
            db.createAcc(acc1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            int currId = acc1.getId();
            db.createRoom(room1, currId);
            db.createRoom(room2, currId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ArrayList<Room> rooms2 = new ArrayList<>();
        Room room3 = new Room(3, 13000, RoomType.Double, 2);
        Room room4 = new Room(4, 15000, RoomType.Triple, 3);
        rooms2.add(room3);
        rooms2.add(room4);
        Accommodation acc2 = new Accommodation(2, "Kea", "Akureyri", rooms2, managerIds, 2.5);
        try {
            db.createAcc(acc2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room3, acc2.getId());
            db.createRoom(room4, acc2.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        ArrayList<Room> rooms3 = new ArrayList<>();
        Room room5 = new Room(5, 130, RoomType.Twin, 2);
        Room room6 = new Room(6, 13243, RoomType.Quad, 4);
        rooms3.add(room5);
        rooms3.add(room6);
        Accommodation acc3 = new Accommodation(3, "Hotel Selfoss", "Selfoss", rooms3, managerIds, 3.9);

        try {
            db.createAcc(acc3);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room5, acc3.getId());
            db.createRoom(room6, acc3.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ArrayList<Room> rooms4 = new ArrayList<>();

        Room room7 = new Room(7, 13000, RoomType.King, 5);
        Room room8 = new Room(8, 15000, RoomType.Queen, 3);
        Room room9 = new Room(21, 13000, RoomType.King, 5);
        Room room10 = new Room(22, 15000, RoomType.Queen, 3);
        rooms4.add(room7);
        rooms4.add(room8);
        rooms4.add(room9);
        rooms4.add(room10);

        Accommodation acc4 = new Accommodation(4, "Grand", "Reykjavik", rooms4, managerIds, 4.6);

        try {
            db.createAcc(acc4);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room7, acc4.getId());
            db.createRoom(room8, acc4.getId());
            db.createRoom(room9, acc4.getId());
            db.createRoom(room10, acc4.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        ArrayList<Room> rooms5 = new ArrayList<>();

        Room room11 = new Room(9, 200, RoomType.Studio, 1);
        Room room12 = new Room(10, 13232, RoomType.Triple, 3);
        rooms5.add(room11);
        rooms5.add(room12);
        Accommodation acc5 = new Accommodation(5, "Kjarnalundur", "Akureyri", rooms5, managerIds, 0.1);

        try {
            db.createAcc(acc5);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room11, acc5.getId());
            db.createRoom(room12, acc5.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ArrayList<Room> rooms6 = new ArrayList<>();

        Room room13 = new Room(11, 1303400, RoomType.Single, 2);
        Room room14 = new Room(12, 150005, RoomType.King, 3);
        rooms6.add(room13);
        rooms6.add(room14);
        Accommodation acc6 = new Accommodation(6, "Hotel Cabin", "Reykjavik", rooms6, managerIds, 0.6);

        try {
            db.createAcc(acc6);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room13, acc6.getId());
            db.createRoom(room14, acc6.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ArrayList<Room> rooms7 = new ArrayList<>();

        Room room15 = new Room(13, 13000, RoomType.Double, 2);
        Room room16 = new Room(14, 123400, RoomType.Studio, 3);

        rooms7.add(room15);
        rooms7.add(room16);
        Accommodation acc7 = new Accommodation(7, "Nordica", "Reykjavik", rooms7, managerIds, 4.8);

        try {
            db.createAcc(acc7);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room15, acc7.getId());
            db.createRoom(room16, acc7.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ArrayList<Room> rooms8 = new ArrayList<>();
        Room room17 = new Room(15, 130500, RoomType.Double, 2);
        Room room18 = new Room(16, 150300, RoomType.Triple, 3);
        rooms8.add(room17);
        rooms8.add(room18);
        Accommodation acc8 = new Accommodation(8, "Midgardur", "Reykjavik", rooms8, managerIds, 2.8);

        try {
            db.createAcc(acc8);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room17, acc8.getId());
            db.createRoom(room18, acc8.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ArrayList<Room> rooms9 = new ArrayList<>();

        Room room19 = new Room(17, 100, RoomType.Single, 1);
        Room room20 = new Room(18, 100, RoomType.Single, 1);
        rooms9.add(room19);
        rooms9.add(room20);
        Accommodation acc9 = new Accommodation(9, "201 Hotel", "Reykjavik", rooms9, managerIds, 4.6);

        try {
            db.createAcc(acc9);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room19, acc9.getId());
            db.createRoom(room20, acc9.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ArrayList<Room> rooms10 = new ArrayList<>();
        Room room21 = new Room(19, 1300, RoomType.Double, 2);
        Room room22 = new Room(20, 150500, RoomType.Triple, 3);
        rooms10.add(room21);
        rooms10.add(room22);
        Accommodation acc10 = new Accommodation(10, "Alda", "Reykjavik", rooms10, managerIds, 4.3);

        try {
            db.createAcc(acc10);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room21, acc10.getId());
            db.createRoom(room22, acc10.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        accommodations.add(acc1);
        accommodations.add(acc2);
        accommodations.add(acc3);
        accommodations.add(acc4);
        accommodations.add(acc5);
        accommodations.add(acc6);
        accommodations.add(acc7);
        accommodations.add(acc8);
        accommodations.add(acc9);
        accommodations.add(acc10);
        return accommodations;
    }

    public ArrayList<Accommodation> getHotelsByLocation(String location) {
        System.out.println("bara til að vera legal");
        return new ArrayList<Accommodation>();
    }

    @Override
    public ArrayList<Accommodation> getHotelsByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Accommodation> getHotelsByRating(double minRating) {
        return null;
    }

    @Override
    public ArrayList<Accommodation> getHotelsByTimePeriod(Date from, Date to) {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        DataFactory df = new DataFactory();
        df.getAllHotels();
    }
}
