package controllers;

import data.DataConnection;
import entities.User;
import data.DataFactory;

import java.util.ArrayList;

public class UserController {
    private User user;

    public UserController() {
    }

    public UserController(User user) {
        this.user = user;
    }

    public void createUser(ArrayList<String> userInfo) {
        DataConnection dc = new DataConnection();
        dc.createUser(userInfo);
        dc.closeConnection();
    }

    public User loginUser(String email, String pw) {
        DataConnection dc = new DataConnection();
        User returnUser = dc.getUserByEmailAndPw(email, pw);
        dc.closeConnection();
        return returnUser;
    }

    public User getUser(String attribute, String value) {
        DataConnection dc = new DataConnection();
        User returnUser = dc.getUserBy(attribute, value);
        dc.closeConnection();
        return returnUser;
    }

    public boolean checkPassword(String pw) {
        return pw.equals(user.getPassword());
    }


    public void updateUser(String attributeToUpdate, String data) {
        DataConnection dc = new DataConnection();
        dc.updateUser(user.getEmail(), attributeToUpdate, data);
        dc.closeConnection();
    }
}
