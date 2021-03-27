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

    public User createUser(String email, String firstName, String lastName, String password, String phoneNumber, String ssNum) {
        user = new User(email, firstName, lastName, password, phoneNumber, ssNum);
        return user;
    }

    public User loginUser(String email, String pw) {
        DataConnection dc = new DataConnection();
        return dc.getUserByEmailAndPw(email, pw);
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

    private boolean checkPassword(String pw) {
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
