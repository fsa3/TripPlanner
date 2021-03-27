package data;

import entities.User;
import javafx.scene.control.Alert;

import java.sql.*;

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
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement getUser = connection.prepareStatement(query);
            getUser.setString(1, email);
            getUser.setString(2, pw);
            ResultSet rs = getUser.executeQuery();
            if(rs.isClosed()) return null;
            String userEmail = rs.getString(1);
            String userPw = rs.getString(2);
            String firstName = rs.getString(3);
            String lastName = rs.getString(4);
            String phoneNum = rs.getString(5);
            String ssNum = rs.getString(6);
            return new User(userEmail, firstName, lastName, userPw, phoneNum, ssNum);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            databaseError();
        }
        return null;
    }

    private void databaseError() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Error Connecting to database");
        a.show();
    }
}
