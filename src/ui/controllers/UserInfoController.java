package ui.controllers;

import controllers.UserController;
import entities.User;
import flightSystem.flightplanner.controllers.FlightUserController;
import flightSystem.flightplanner.data.FlDataConnection;
import flightSystem.flightplanner.entities.Passenger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML private ComboBox<String> activityChooser;
    @FXML private Slider ratingSlider;
    @FXML private Label ratingLabel;

    private User user;
    private SearchUiController owningSearchController;
    private BookingUiController owningBookingController;

    public void initialize() {
        String[] flokkar = {"Hiking", "Sailing", "Skiing", "Biking", "City Tour"};
        activityChooser.setItems(FXCollections.observableArrayList(flokkar));
        ratingSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                ratingLabel.setText(String.valueOf(Math.round((double) t1)));
            }
        });
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
        activityChooser.setValue(user.getFavoriteActivity());
        ratingSlider.setValue(user.getMinHotelRating());
        ratingLabel.setText(String.valueOf(user.getMinHotelRating()));
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
        if(activityChooser.getValue() != null) uc.updateUser("favoriteActivity", activityChooser.getValue());
        uc.updateUser("minHotelRating", ratingLabel.getText());
        changeSuccessful.setText("Changes saved successfully!");
        user = uc.getUser("email",emailInput.getText());

        if(owningSearchController != null) owningSearchController.setUser(user);
        if(owningBookingController != null) owningBookingController.setUser(user);

        // updates user in flight database
        FlDataConnection flightData = FlDataConnection.getInstance();
        try {
            Passenger p = flightData.getPassenger(user.getId());
            p.setEmail(user.getEmail());
            p.setFirstName(user.getFirstName());
            p.setLastName(user.getLastName());
            p.setPhoneNumber(user.getPhoneNumber());
            flightData.updatePassenger(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logoutClicked(ActionEvent actionEvent) {
        user = null;
        if(owningSearchController != null) owningSearchController.setUser(null);
        if(owningBookingController != null) owningBookingController.setUser(null);
        closeWindow();
    }
}

