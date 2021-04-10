package ui.controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserUIController implements Initializable {

    @FXML private UserInfoController userInfoController;
    @FXML private UserBookingsController userBookingsController;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUser(User user) {
        this.user = user;
        userInfoController.setUser(user);
        userBookingsController.setUser(user);
    }
}
