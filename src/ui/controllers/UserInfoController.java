package ui.controllers;

import controllers.UserController;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserInfoController {
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField newPassword2;
    @FXML private Label passwordLabel;
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

    public void changePassword(ActionEvent actionEvent) {
        String old = oldPassword.getText();
        String new1 = newPassword.getText();
        String new2 = newPassword2.getText();
        UserController uc = new UserController(user);
        if(uc.checkPassword(old)) {
            if(new1.equals(new2)) {
                //fff
            }
            else {
                passwordLabel.setText("Passwords do not match");
            }
        }
        else {
            passwordLabel.setText("Incorrect password");
        }
    }
}
