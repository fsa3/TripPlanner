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
    @FXML private Label changeSuccessful;
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
    private SearchUiController owningSearchController;
    private BookingUiController owningBookingController;

    public void initialize() {

    }

    public void setUser(User user) {
        this.user = user;
        updateFields();
    }

    public void setOwningSearchController(SearchUiController owningSearchController) {
        this.owningSearchController = owningSearchController;
    }

    public void setOwningBookingController(BookingUiController owningBookingController) {
        this.owningBookingController = owningBookingController;
    }

    private void updateFields() {
        emailInput.setText(user.getEmail());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        phoneTextField.setText(user.getPhoneNumber());
    }

    public void closeWindow() {
        ((Stage) userInfo.getScene().getWindow()).close();
    }

    public void changePassword(ActionEvent actionEvent) {
        String old = oldPassword.getText();
        String new1 = newPassword.getText();
        String new2 = newPassword2.getText();
        UserController uc = new UserController(user);
        if(uc.checkPassword(old)) {
            if(new1.equals(new2)) {
                UserController userController = new UserController(this.user);
                userController.updateUser("password", new1);
                passwordLabel.setText("Password successfully updated");
            }
            else {
                passwordLabel.setText("Passwords do not match");
            }
        }
        else {
            passwordLabel.setText("Incorrect password");
        }
    }

    public void confirmChanges(ActionEvent actionEvent) {
        UserController uc = new UserController(user);
        uc.updateUser("email",emailInput.getText());
        uc.updateUser("firstName",firstNameTextField.getText());
        uc.updateUser("lastName",lastNameTextField.getText());
        uc.updateUser("phoneNumber",phoneTextField.getText());
        changeSuccessful.setText("Changes saved successfully!");
        user = uc.getUser("email",emailInput.getText());

        //todo

    }

    public void logoutClicked(ActionEvent actionEvent) {
        user = null;
        if(owningSearchController != null) owningSearchController.setUser(null);
        if(owningBookingController != null) owningBookingController.setUser(null);
        closeWindow();
    }
}

