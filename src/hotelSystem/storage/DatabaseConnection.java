package hotelSystem.storage;

import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Occupancy;
import hotelSystem.entities.Room;
import hotelSystem.entities.RoomType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseConnection implements Database {

    private Connection conn = null;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");

            //initializeDatabase();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");
            Statement stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createNewDatabase(String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //https://docs.oracle.com/cd/E35521_01/doc.111230/e24475/amxdatabase.htm#ADFMF1263
    public void initializeDatabase() throws Exception {
        InputStream scriptStream = null;
        Connection conn = null;
        //Class.forName("org.sqlite.JDBC");
        try {
            String currDir = System.getProperty("user.dir");
            // til að þetta sé óhátt stýrikerfi
            String fileName = "hotel.db"; // viljum kannski breyta seinna
            String dbDir = currDir + File.separator + "src" + File.separator + "hotelSystem" + File.separator + "storage" + File.separator;
            //String dbDir2 = dbDir.replace(File.separator, "/");
            //String dbName = dbDir2 + fileName;
            String dbName = currDir + File.separator + fileName;
            System.out.println(dbName);

            String url = "jdbc:sqlite:" + fileName;

            File dbFile = new File(dbName);
            if (dbFile.exists()) {
                System.out.println("File exists");
                //Files.deleteIfExists(dbFile.toPath());
                //System.out.println("File " + dbFile.toPath() + " was deleted.");
                //createNewDatabase(url);
                //    return;
            } else {
                System.out.println("not exists");
                //createNewDatabase(url);
            }

            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

            String hailmary = (dbDir + "schema.sql").replace(File.separator, "/");

            BufferedReader scriptReader = new BufferedReader(new FileReader(hailmary));

            String nextLine;
            StringBuffer nextStatement = new StringBuffer();
            Statement stmt = conn.createStatement();
            while ((nextLine = scriptReader.readLine()) != null) {
                if (nextLine.startsWith("REM") ||
                        nextLine.startsWith("COMMIT") ||
                        nextLine.length() < 1)
                    continue;
                nextStatement.append(nextLine + "\n");
                if (nextLine.endsWith(";")) {
                    stmt.execute(nextStatement.toString());
                    nextStatement = new StringBuffer();
                }
            }
            conn.commit();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void createAcc(Accommodation acc) throws Exception {
        getConnection();
        String q = "INSERT INTO Accommodation "
                + "(name, location, rating, description)"
                + "VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setString(1, acc.getName());
        ps.setString(2, acc.getLocation());
        ps.setDouble(3, acc.getRating());
        ps.setString(4, acc.getDescription());
        ps.executeUpdate();
        ps.close();
        closeConnection();
    }

    public ArrayList<Accommodation> getAllHotels() throws Exception {
        getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Accommodation");
        System.out.print(rs);
        ArrayList<Accommodation> res = new ArrayList<>();
        while (rs.next()) {
            ArrayList<Room> rooms = getRoomsByAccId(rs.getInt("id"));
            Accommodation acc = new Accommodation(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rooms,
                    rs.getDouble("rating")
            );
            res.add(acc);
        }
        rs.close();
        closeConnection();
        return res;
    }


    public ArrayList<Accommodation> getHotelsByLocation(String location) throws Exception {
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Accommodation WHERE location LIKE \"%" + location + "%\"");
        //pstmt.setString(1, location);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Accommodation> res = new ArrayList<Accommodation>();
        while (rs.next()) {
            ArrayList<Room> rooms = getRoomsByAccId(rs.getInt("id"));
            res.add(
                    new Accommodation(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("location"),
                            rooms,
                            rs.getDouble("rating")
                    )
            );
        }
        rs.close();
        closeConnection();
        return res;
    }


    public ArrayList<Accommodation> getHotelsByRating(double minRating) throws Exception {
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Accommodation WHERE rating >= ?");
        pstmt.setDouble(1, minRating);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Accommodation> res = new ArrayList<Accommodation>();
        while (rs.next()) {
            ArrayList<Room> rooms = getRoomsByAccId(rs.getInt("id"));
            res.add(
                    new Accommodation(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("location"),
                            rooms,
                            rs.getDouble("rating")
                    )
            );
        }
        rs.close();
        closeConnection();
        return res;
    }

    public ArrayList<Accommodation> getHotelsByName(String name) throws Exception {
        getConnection();

        // hello code injection
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM Accommodation " +
                        "WHERE name LIKE " +
                        "\"%" +
                        name +
                        "%\"");
        //name = "%" + name + "%";
        //pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Accommodation> res = new ArrayList<>();
        while (rs.next()) {
            ArrayList<Room> rooms = getRoomsByAccId(rs.getInt("id"));
            res.add(
                    new Accommodation(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("location"),
                            rooms,
                            rs.getDouble("rating")
                    )
            );
        }
        rs.close();
        closeConnection();
        return res;
    }


    public void createRoom(Room room, int accId) throws Exception {
        getConnection();
        String q = "INSERT INTO Room "
                + "(roomType, price, cap, accId )"
                + "VALUES (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(q);
        pstmt.setString(1, room.getRoomType().name());
        pstmt.setDouble(2, room.getPrice());
        pstmt.setInt(3, room.getCap());
        pstmt.setInt(4, accId);
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }

    public void getAllRooms() throws Exception {

    }

    public ArrayList<Room> getRoomsByAccId(int accId) throws Exception {
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Room WHERE accId = ?");
        ArrayList<Room> res = new ArrayList<Room>();
        pstmt.setInt(1, accId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Room room = new Room(
                    rs.getInt("id"),
                    rs.getDouble("price"),
                    RoomType.valueOf(rs.getString("roomType")),
                    rs.getInt("cap")
            );
            res.add(room);
        }
        pstmt.close();
        rs.close();
        closeConnection();
        return res;
    }

    // following fuction an addition by T-group
    public Room getRoomByRoomId(int roomId) throws Exception {
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Room WHERE id = ?");
        pstmt.setInt(1, roomId);
        ResultSet rs = pstmt.executeQuery();
        Room room = null;
        if (rs.next()) {
            room = new Room(
                    rs.getInt("id"),
                    rs.getDouble("price"),
                    RoomType.valueOf(rs.getString("roomType")),
                    rs.getInt("cap")
            );
        }
        pstmt.close();
        rs.close();
        closeConnection();
        return room;
    }

    // following function an addition by T-group
    public Accommodation getHotelById(int id) throws Exception {
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Accommodation WHERE id = ?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        Accommodation hotel = null;
        if (rs.next()) {
            ArrayList<Room> rooms = getRoomsByAccId(rs.getInt("id"));
            hotel = new Accommodation(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rooms,
                    rs.getDouble("rating")
            );
        }
        rs.close();
        closeConnection();
        return hotel;
    }

    public void createOccupancy(int roomId, Date from, Date to) throws Exception {
        getConnection();
        String q = "INSERT INTO Occupancy "
                + "(roomId, dateFrom, dateTo)"
                + "VALUES (?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(q);
        pstmt.setInt(1, roomId);
        pstmt.setDate(2, from);
        pstmt.setDate(3, to);
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }

    public ArrayList<Occupancy> getAllOccupanciesByRoomId(int roomId) throws Exception {
        getConnection();
        String q = "SELECT * FROM Occupancy WHERE roomId = ?";
        PreparedStatement pstmt = conn.prepareStatement(q);
        pstmt.setInt(1, roomId);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Occupancy> res = new ArrayList<>();
        while (rs.next()) {
            Occupancy occ = new Occupancy(
                    rs.getDate("dateFrom"),
                    rs.getDate("dateTo")
            );
            res.add(occ);
        }
        rs.close();
        closeConnection();
        return res;
    }

    public static void main(String[] args) throws Exception {
        DatabaseConnection connection = new DatabaseConnection();
        connection.initializeDatabase();

        try {
            ArrayList<Accommodation> gotten = connection.getAllHotels();
            System.out.println(gotten.size());
            for (Accommodation hotel : gotten) {
                System.out.println(hotel.toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Test2");


        try {
            ArrayList<Accommodation> gotten1 = connection.getHotelsByName("hote");
            System.out.println("1 " + gotten1.size());
            for (Accommodation hotel : gotten1) {
                System.out.println(hotel.toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Test3");
        try {
            ArrayList<Accommodation> gotten2 = connection.getHotelsByLocation("rey");
            System.out.println(gotten2.size());
            for (Accommodation hotel : gotten2) {
                System.out.println(hotel.toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Test4");
        try {
            ArrayList<Accommodation> garg = connection.getHotelsByRating(5.0);
            for (Accommodation hotel : garg) {
                System.out.println(hotel.toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
