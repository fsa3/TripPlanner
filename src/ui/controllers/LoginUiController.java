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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginUiController implements Initializable {

    @FXML
    public Label notMemberLabel;
    @FXML
    private BorderPane sceneRoot;
    @FXML
    public TextField emailInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public Label errorLabel;

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
        errorLabel.setVisible(false);
        UserController userController = new UserController();
        user = userController.loginUser(emailInput.getText(), passwordInput.getText());
        if(user == null) {
            errorLabel.setVisible(true);
        }
        else {
            owningController.setUser(user);
            ((Stage) sceneRoot.getScene().getWindow()).close();
        }
    }

    public void cancelWhenClicked(ActionEvent actionEvent) {
        ((Stage) sceneRoot.getScene().getWindow()).close();
    }

    public void signUpUserLabelClicked(MouseEvent mouseEvent) {
        try {
            BorderPane signUpRoot = FXMLLoader.load(getClass().getResource("../views/signupUI.fxml"));
            sceneRoot.getChildren().setAll(signUpRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void notMemberMouseEnter(MouseEvent mouseEvent) {
        notMemberLabel.setUnderline(true);
    }

    public void notMemberMouseExit(MouseEvent mouseEvent) {
        notMemberLabel.setUnderline(false);
    }
}
