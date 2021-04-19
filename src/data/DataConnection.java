package data;

import entities.DayTripBooking;
import entities.FlightBooking;
import entities.HotelBooking;
import entities.User;
import flightSystem.flightplanner.data.FlDataConnection;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;
import hotelSystem.storage.DatabaseConnection;
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
        int id = rs.getInt(7);
        getUser.close();
        System.out.println("user id "+id);
        return new User(userEmail, firstName, lastName, userPw, phoneNum, ssNum, id);
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
        int id = createFlightUser(userInfo);
        String query = "INSERT INTO Users(email, password, firstName, lastName, phoneNumber, ssNum, id) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement createUser = connection.prepareStatement(query);
            for(int i = 0; i < 6; i++) {
                createUser.setString(i+1, userInfo.get(i));
            }
            createUser.setInt(7, id);
            createUser.executeUpdate();
            createUser.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            databaseError();
        }
    }

    private int createFlightUser(ArrayList<String> userInfo) {
        flightSystem.flightplanner.entities.User flightUser = new flightSystem.flightplanner.entities.User("user", -1, userInfo.get(2), userInfo.get(3), userInfo.get(5), userInfo.get(0), userInfo.get(4));
        flightUser.setPassword(userInfo.get(1));
        FlDataConnection flightData = FlDataConnection.getInstance();
        try {
            flightData.createUser(flightUser);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                flightUser = flightData.getUser(userInfo.get(0), userInfo.get(1));
            } catch (Exception e) {
                e.printStackTrace();
            };
        }
        return flightUser.getID();
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

    public static java.util.Date localDateToUtilDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
            createHotelBooking.setInt(1, hotelBooking.getHotel().getId());
            createHotelBooking.setString(2, hotelBooking.getHotelName());
            createHotelBooking.setString(3, hotelBooking.getCity());
            createHotelBooking.setDate(4, localDateToDate(hotelBooking.getCheckInDate()));
            createHotelBooking.setString(5, hotelBooking.getRoom().toString());
            createHotelBooking.setInt(6, hotelBooking.getRoom().getRoomId());
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
            DatabaseConnection hotelDataConnection = new DatabaseConnection();
            while(rs.next()) {
                int hotelId = rs.getInt(1);
                Accommodation hotel = null;
                try {
                    hotel = hotelDataConnection.getHotelById(hotelId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String hotelName = rs.getString(2);
                String city = rs.getString(3);
                LocalDate checkInDate = dateToLocalDate(rs.getDate(4));
                int roomId = rs.getInt(6);
                Room room = null;
                try {
                    room = hotelDataConnection.getRoomByRoomId(roomId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int bookingId = rs.getInt(7);
                LocalDate checkOutDate = dateToLocalDate(rs.getDate(9));
                HotelBooking hb;
                if(hotel != null) hb = new HotelBooking(hotel, checkInDate, checkOutDate, bookingId, user, room);
                else hb = new HotelBooking(hotelName, checkInDate, checkOutDate, bookingId, user, room, city);
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

    public Flight getFlightByBookingId(Integer id, String flightType) {
        String query = "SELECT "+flightType+" FROM Booking WHERE bookingId = ?";
        int flightId = -1;
        Flight flight = null;
        try {
            PreparedStatement getFlight = connection.prepareStatement(query);
            getFlight.setInt(1, id);
            ResultSet rs = getFlight.executeQuery();
            if(rs.next()) flightId = rs.getInt(1);
            FlDataConnection flightData = FlDataConnection.getInstance();
            flight = flightData.getFlightById(flightId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flight;
    }

    public void createFlightBooking(FlightBooking flightBooking) {
        String query = "INSERT INTO FlightBookings(bookingId, flightBookingId) VALUES(?,?)";
        try {
            PreparedStatement createFlightBooking = connection.prepareStatement(query);
            createFlightBooking.setInt(1, flightBooking.getTipBookingId());
            createFlightBooking.setInt(2, flightBooking.getFlightBookingId());
            createFlightBooking.executeUpdate();
            createFlightBooking.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Integer> getFlightBookinIds(Integer id) {
        String query = "SELECT flightBookingId FROM FlightBookings WHERE bookingId = ?";
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            PreparedStatement getFlightBookingIds = connection.prepareStatement(query);
            getFlightBookingIds.setInt(1, id);
            ResultSet rs = getFlightBookingIds.executeQuery();
            while(rs.next()) ids.add(rs.getInt(1));
            rs.close();
            getFlightBookingIds.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ids;
    }

    public void cancelFlightBooking(FlightBooking flightBooking) {
        String query = "DELETE FROM FlightBookings WHERE bookingId = ? AND flightBookingId = ?";
        try {
            PreparedStatement deleteFlightBooking = connection.prepareStatement(query);
            deleteFlightBooking.setInt(1, flightBooking.getTipBookingId());
            deleteFlightBooking.setInt(2, flightBooking.getFlightBookingId());
            deleteFlightBooking.executeUpdate();
            deleteFlightBooking.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteBooking(Integer id) {
        String query = "DELETE FROM Booking WHERE bookingId = ?";
        try {
            PreparedStatement deleteBooking = connection.prepareStatement(query);
            deleteBooking.setInt(1, id);
            deleteBooking.executeUpdate();
            deleteBooking.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
