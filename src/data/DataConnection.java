package data;

import entities.User;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

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
}
