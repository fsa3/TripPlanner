package data;

import entities.DayTripBooking;
import entities.HotelBooking;
import entities.User;
import hotelSystem.entities.Accommodation;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DataConnection {
    private Connection connection;

    public DataConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:bookMasterUserData.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public User getUserByEmailAndPw(String email, String pw) {
        try {
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement getUser = connection.prepareStatement(query);
            getUser.setString(1, email);
            getUser.setString(2, pw);
            return getUserFromStatement(getUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            databaseError();
        }
        return null;
    }

    private User getUserFromStatement(PreparedStatement getUser) throws SQLException {
        ResultSet rs = getUser.executeQuery();
        if(rs.isClosed()) return null;
        String userEmail = rs.getString(1);
        String userPw = rs.getString(2);
        String firstName = rs.getString(3);
        String lastName = rs.getString(4);
        String phoneNum = rs.getString(5);
        String ssNum = rs.getString(6);
        getUser.close();
        return new User(userEmail, firstName, lastName, userPw, phoneNum, ssNum);
    }

    private void databaseError() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Error Connecting to database");
        a.show();
    }

    public User getUserBy(String attribute, String value) {
        try {
            String query = "SELECT * FROM Users WHERE "+attribute+" = ?";
            PreparedStatement getUser = connection.prepareStatement(query);
            getUser.setString(1, value);
            return getUserFromStatement(getUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            databaseError();
        }
        return null;
    }

    public void createUser(ArrayList<String> userInfo) {
        String query = "INSERT INTO Users(email, password, firstName, lastName, phoneNumber, ssNum) values(?,?,?,?,?,?)";
        try {
            PreparedStatement createUser = connection.prepareStatement(query);
            for(int i = 0; i < 6; i++) {
                createUser.setString(i+1, userInfo.get(i));
            }
            createUser.executeUpdate();
            createUser.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            databaseError();
        }
    }

    public void updateUser(String email, String attribute, String value) {
        try {
            String query = "UPDATE Users SET "+attribute+" = ? WHERE email = ?";
            PreparedStatement updateUserStm = connection.prepareStatement(query);
            updateUserStm.setString(1, value);
            updateUserStm.setString(2, email);
            updateUserStm.executeUpdate();
            updateUserStm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            databaseError();
        }
    }

    public void closeConnection() {
        try {
            if(connection != null) connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createDayTripBooking(DayTripBooking dtBooking) {
        String query = "INSERT INTO DayTripBookings(dayTripName, city, date, bookingId, user) values(?,?,?,?,?)";
        try {
            PreparedStatement createDayTripBooking = connection.prepareStatement(query);
            createDayTripBooking.setString(1, dtBooking.getDayTripName());
            createDayTripBooking.setString(2, dtBooking.getCity());
            createDayTripBooking.setDate(3, localDateToDate(dtBooking.getDate()));
            createDayTripBooking.setInt(4, dtBooking.getBookingId());
            createDayTripBooking.setString(5, dtBooking.getBookingUser().getEmail());
            createDayTripBooking.executeUpdate();
            createDayTripBooking.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static LocalDate dateToLocalDate(java.sql.Date dateToConvert) {
        return dateToConvert.toLocalDate();
    }

    public static Date localDateToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public static LocalDate utilDateToLocalDate(java.util.Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void createBooking(User user, int bookingId, double price, int numAdults, int numChildren) {
        String query = "INSERT INTO Booking(bookingUser, bookingId, price, numAdults, numChildren) values(?,?,?,?,?)";
        try {
            PreparedStatement createBooking = connection.prepareStatement(query);
            createBooking.setString(1, user.getEmail());
            createBooking.setInt(2, bookingId);
            createBooking.setDouble(3, price);
            createBooking.setInt(4, numAdults);
            createBooking.setInt(5, numChildren);
            createBooking.executeUpdate();
            createBooking.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Integer> getUserBookings(User user) {
        String query = "SELECT bookingId FROM Booking WHERE bookingUser = ?";
        ArrayList<Integer> bookingIds = new ArrayList<>();
        try {
            PreparedStatement getUserBookings = connection.prepareStatement(query);
            getUserBookings.setString(1, user.getEmail());
            ResultSet rs = getUserBookings.executeQuery();
            while (rs.next()) {
                bookingIds.add(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookingIds;
    }

    public ArrayList<DayTripBooking> getDayTripBookings(User user) {
        ArrayList<DayTripBooking> dayTripBookings = new ArrayList<>();
        String query = "SELECT * FROM DayTripBookings WHERE user = ?";
        try {
            PreparedStatement getDayTripBookings = connection.prepareStatement(query);
            getDayTripBookings.setString(1, user.getEmail());
            ResultSet rs = getDayTripBookings.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String city = rs.getString(2);
                LocalDate date = dateToLocalDate(rs.getDate(3));
                int id = rs.getInt(4);
                User bookingUser = getUserBy("email", rs.getString(5));
                DayTripBooking dtB = new DayTripBooking(name, city, date, id, bookingUser);
                dayTripBookings.add(dtB);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dayTripBookings;
    }

    public void createHotelBooking(HotelBooking hotelBooking) {
        String query = "INSERT INTO HotelBookings(hotel, hotelName, city, checkInDate, roomType, roomTypeId, bookingId, bookingUser, checkOutDate) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement createHotelBooking = connection.prepareStatement(query);
            createHotelBooking.setInt(1, -1); // todo setja hotelID
            createHotelBooking.setString(2, hotelBooking.getHotelName());
            createHotelBooking.setString(3, hotelBooking.getCity());
            createHotelBooking.setDate(4, localDateToDate(hotelBooking.getCheckInDate()));
            createHotelBooking.setString(5, hotelBooking.getRoom());
            createHotelBooking.setInt(6, -1); // todo setja room id
            createHotelBooking.setInt(7, hotelBooking.getBookingId());
            createHotelBooking.setString(8, hotelBooking.getBookingUser().getEmail());
            createHotelBooking.setDate(9, localDateToDate(hotelBooking.getCheckOutDate()));
            createHotelBooking.executeUpdate();
            createHotelBooking.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<HotelBooking> getHotelBookings(User user) {
        ArrayList<HotelBooking> hotelBookings = new ArrayList<>();
        String query = "SELECT * FROM HotelBookings WHERE bookingUser = ?";
        try {
            PreparedStatement getHotelBookings = connection.prepareStatement(query);
            getHotelBookings.setString(1, user.getEmail());
            ResultSet rs = getHotelBookings.executeQuery();
            while(rs.next()) {
                int hotelId = rs.getInt(1);
                String hotelName = rs.getString(2);
                String city = rs.getString(3);
                LocalDate checkInDate = dateToLocalDate(rs.getDate(4));
                String room = rs.getString(5);
                int roomId = rs.getInt(6);
                int bookingId = rs.getInt(7);
                LocalDate checkOutDate = dateToLocalDate(rs.getDate(9));
                HotelBooking hb = new HotelBooking(hotelName, checkInDate, checkOutDate, bookingId, user, room, city);
                hotelBookings.add(hb);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelBookings;
    }

    public Double getBookingPrice(int id) {
        String query = "SELECT price FROM Booking WHERE bookingId = ?";
        Double price = null;
        try {
            PreparedStatement getBookingPrice = connection.prepareStatement(query);
            getBookingPrice.setInt(1, id);
            ResultSet rs = getBookingPrice.executeQuery();
            if(rs.next()) price = rs.getDouble(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return price;
    }
}
