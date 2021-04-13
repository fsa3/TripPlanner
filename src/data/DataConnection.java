package data;

import entities.DayTripBooking;
import entities.User;
import javafx.scene.control.Alert;

import java.sql.*;
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
        String query = "INSERT INTO DayTripBookings(dayTripName, city, numHours, date, bookingId) values(?,?,?,?,?)";
        try {
            PreparedStatement createDayTripBooking = connection.prepareStatement(query);
            createDayTripBooking.setString(1, dtBooking.getDayTripName());
            createDayTripBooking.setString(2, dtBooking.getCity());
            createDayTripBooking.setInt(3, dtBooking.getNumHours());
            createDayTripBooking.setDate(4, localDateToDate(dtBooking.getDate()));
            createDayTripBooking.setInt(5, dtBooking.getBookingId());
            createDayTripBooking.executeUpdate();
            createDayTripBooking.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private Date localDateToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
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

    public String getUserBookings(User user) {
        String query = "SELECT bookingUser, bookingId, price, numAdults, numChildren FROM Booking WHERE bookingUser = ?";
        try {
            PreparedStatement getUserBookings = connection.prepareStatement(query);
            getUserBookings.setString(1, user.getEmail());
            ResultSet rs = getUserBookings.executeQuery();
            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append(rs.getString(1)).append(" ");
                result.append(rs.getInt(2)).append(" ");
                result.append(rs.getDouble(3)).append(" ");
                result.append(rs.getInt(4)).append(" ");
                result.append(rs.getInt(5)).append(" ");
                result.append(";;;");
            }
            return result.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Book something";
        }
    }

    public ArrayList<DayTripBooking> getDayTripBookings(int bookingId) {
        ArrayList<DayTripBooking> dayTripBookings = new ArrayList<>();
        String query = "SELECT * FROM Booking WHERE bookingId = ?";
        //PreparedStatement getDayTripBookings = connection.prepareStatement(query);
        return null;
    }
}
