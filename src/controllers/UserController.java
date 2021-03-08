package controllers;

import entities.User;

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

    public void loginUser(String email, String pw) {
        searchUser(email);
        checkPassword(pw);
        // todo
    }

    private void searchUser(String email) {
        // todo
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
