package ui.controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserInfoController {
    @FXML private BorderPane userInfo;
    @FXML private TextField emailInput;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField phoneTextField;
    private User user;

    public void initialize() {

    }

    public void setUser(User user) {
        this.user = user;
        updateFields();
    }

    private void updateFields() {
        emailInput.setText(user.getEmail());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        phoneTextField.setText(user.getPhoneNumber());
    }

    public void closeWindow(ActionEvent actionEvent) {
        ((Stage) userInfo.getScene().getWindow()).close();
    }
}
