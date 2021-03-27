package ui.controllers;

import controllers.UserController;
import entities.SearchResult;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginUiController implements Initializable {

    @FXML
    public TextField emailInput;
    @FXML
    public PasswordField passwordInput;

    private User user;
    private SearchUiController owningController;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUser(User user) {
        this.user = user;
        emailInput.setText(this.user.getEmail());
    }

    public void setOwningController(SearchUiController controller) {
        owningController = controller;
    }

    public void loginOnClick(ActionEvent actionEvent) {
        UserController userController = new UserController();
        user = userController.loginUser(emailInput.getText(), passwordInput.getText());
        owningController.setUser(user);
    }
}
