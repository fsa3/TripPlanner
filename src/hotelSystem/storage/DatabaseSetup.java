package hotelSystem.storage;


import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;
import hotelSystem.entities.RoomType;

import java.util.ArrayList;

public class DatabaseSetup {
    public DatabaseSetup() {
    }
    public ArrayList<Accommodation> pumpIntoDatabase() {
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
        Room room1 = new Room(40, RoomType.Single, 1);
        Room room2 = new Room(40, RoomType.Single, 1);
        rooms.add(room1);
        rooms.add(room2);
        Accommodation acc1 = new Accommodation(1, "Hilton", "Reykjavík", rooms, managerIds, 5.0);
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
        Room room3 = new Room(100, RoomType.Double, 2);
        Room room4 = new Room(120, RoomType.Triple, 3);
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
        Room room5 = new Room(130, RoomType.Twin, 2);
        Room room6 = new Room(140, RoomType.Quad, 4);
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

        Room room7 = new Room(180, RoomType.King, 3);
        Room room8 = new Room(160, RoomType.Queen, 3);
        Room room9 = new Room(230, RoomType.Quad, 5);
        Room room10 = new Room(160, RoomType.Queen, 3);
        rooms4.add(room7);
        rooms4.add(room8);
        rooms4.add(room9);
        rooms4.add(room10);

        Accommodation acc4 = new Accommodation(4, "Grand", "Reykjavík", rooms4, managerIds, 4.6);

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

        Room room11 = new Room(200, RoomType.Studio, 1);
        Room room12 = new Room(139, RoomType.Triple, 3);
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

        Room room13 = new Room(5, RoomType.Single, 2);
        Room room14 = new Room(20, RoomType.King, 3);
        rooms6.add(room13);
        rooms6.add(room14);
        Accommodation acc6 = new Accommodation(6, "Hotel Cabin", "Reykjavík", rooms6, managerIds, 0.6);

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

        Room room15 = new Room(200, RoomType.Double, 2);
        Room room16 = new Room(420, RoomType.Studio, 3);

        rooms7.add(room15);
        rooms7.add(room16);
        Accommodation acc7 = new Accommodation(7, "Nordica", "Reykjavík", rooms7, managerIds, 4.8);

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
        Room room17 = new Room(99, RoomType.Double, 2);
        Room room18 = new Room(120, RoomType.Triple, 3);
        rooms8.add(room17);
        rooms8.add(room18);
        Accommodation acc8 = new Accommodation(8, "Midgardur", "Reykjavík", rooms8, managerIds, 2.8);

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

        Room room19 = new Room(100, RoomType.Single, 1);
        Room room20 = new Room(100, RoomType.Single, 1);
        rooms9.add(room19);
        rooms9.add(room20);
        Accommodation acc9 = new Accommodation(9, "201 Hotel", "Reykjavík", rooms9, managerIds, 4.6);

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
        Room room21 = new Room(200, RoomType.Double, 2);
        Room room22 = new Room(300, RoomType.Triple, 3);
        rooms10.add(room21);
        rooms10.add(room22);
        Accommodation acc10 = new Accommodation(10, "Alda", "Reykjavík", rooms10, managerIds, 4.5);

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

        /////////////////
        ArrayList<Room> rooms11 = new ArrayList<>();
        Room room23 = new Room(85, RoomType.Single, 1);
        Room room24 = new Room(121, RoomType.Triple, 3);
        rooms11.add(room23);
        rooms11.add(room24);
        Accommodation acc11 = new Accommodation(11, "Hótel Ísafjörður", "Ísafjörður", rooms11, managerIds, 3.1);

        try {
            db.createAcc(acc11);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room23, acc11.getId());
            db.createRoom(room24, acc11.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        ////////////////

        /////////////////
        ArrayList<Room> rooms12 = new ArrayList<>();
        Room room25 = new Room(130, RoomType.Double, 2);
        Room room26 = new Room(200, RoomType.Triple, 3);
        Room room27 = new Room(190, RoomType.Queen, 2);
        Room room28 = new Room(200, RoomType.Studio, 3);
        rooms12.add(room25);
        rooms12.add(room26);
        rooms12.add(room27);
        rooms12.add(room28);
        Accommodation acc12 = new Accommodation(12, "Fisherman Hotel Westfjords", "Ísafjörður", rooms12, managerIds, 4.0);

        try {
            db.createAcc(acc12);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room25, acc12.getId());
            db.createRoom(room26, acc12.getId());
            db.createRoom(room27, acc12.getId());
            db.createRoom(room28, acc12.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        ////////////////
        ArrayList<Room> rooms13 = new ArrayList<>();
        Room room29 = new Room(90, RoomType.Single, 1);
        Room room30 = new Room(150, RoomType.Triple, 3);
        Room room31 = new Room(140, RoomType.Queen, 2);
        Room room32 = new Room(130, RoomType.Twin, 3);
        rooms13.add(room29);
        rooms13.add(room30);
        rooms13.add(room31);
        rooms13.add(room32);
        Accommodation acc13 = new Accommodation(13, "Hotel Hérað", "Egilsstaðir", rooms13, managerIds, 3.0);

        try {
            db.createAcc(acc13);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room29, acc13.getId());
            db.createRoom(room30, acc13.getId());
            db.createRoom(room31, acc13.getId());
            db.createRoom(room32, acc13.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        ////////////////
        ArrayList<Room> rooms14 = new ArrayList<>();
        Room room34 = new Room(89, RoomType.Single, 1);
        Room room35 = new Room(140, RoomType.Triple, 3);
        Room room36 = new Room(130, RoomType.Queen, 2);
        Room room37 = new Room(120, RoomType.Twin, 3);
        Room room38 = new Room(210, RoomType.Studio, 4);
        rooms14.add(room34);
        rooms14.add(room35);
        rooms14.add(room36);
        rooms14.add(room37);
        rooms14.add(room38);
        Accommodation acc14 = new Accommodation(14, "Hotel Egilsstaðir", "Egilsstaðir", rooms14, managerIds, 4.2);

        try {
            db.createAcc(acc14);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            db.createRoom(room34, acc14.getId());
            db.createRoom(room35, acc14.getId());
            db.createRoom(room36, acc14.getId());
            db.createRoom(room37, acc14.getId());
            db.createRoom(room38, acc14.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        ////////////////


        

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
        accommodations.add(acc11);
        accommodations.add(acc12);
        accommodations.add(acc13);
        accommodations.add(acc14);
        return accommodations;
    }

    public static void main(String[] args) {
        DatabaseSetup df = new DatabaseSetup();
        df.pumpIntoDatabase();
    }
}
