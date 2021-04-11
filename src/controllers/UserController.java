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
    }

    public User loginUser(String email, String pw) {
        DataConnection dc = new DataConnection();
        return dc.getUserByEmailAndPw(email, pw);
    }

    public User getUser(String attribute, String value) {
        DataConnection dc = new DataConnection();
        return dc.getUserBy(attribute, value);
    }

    private void searchUser(String email) {
        // todo
        DataFactory dataFactory = new DataFactory();
        ArrayList<User> allUsers = dataFactory.getUsers();
        for(User u : allUsers) {
            if(email.equals(u.getEmail())) {
                user = u;
            }
        }
    }

    public boolean checkPassword(String pw) {
        return pw.equals(user.getPassword());
    }

    public void logoutUser() {
        // todo
    }

    public void deleteUser(User user) {
        // todo
    }

    public void updateUser(String attributeToUpdate, String data) {
        // todo
    }
}
