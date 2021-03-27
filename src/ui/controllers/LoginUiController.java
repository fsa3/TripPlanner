package ui.controllers;

import controllers.UserController;
import entities.SearchResult;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginUiController implements Initializable {

    @FXML
    public Label notMemberLabel;
    @FXML
    public TextField firstNameTextField;
    @FXML
    public TextField lastNameTextField;
    @FXML
    public TextField phoneTextField;
    @FXML
    public TextField ssNumberTextField;
    @FXML
    public VBox inputs;
    @FXML
    private BorderPane sceneRoot;
    @FXML
    public TextField emailInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public Label errorLabel;
    @FXML
    private Label signUpErrorLabel;

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

    public void createUser(ActionEvent actionEvent) {
        UserController userController = new UserController();
        ArrayList<String> userInfo = new ArrayList<>();
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(regex);
        regex = "[0-9]+";
        Pattern phonePattern = Pattern.compile(regex);
        signUpErrorLabel.setText("");
        StringBuilder errorMsg = new StringBuilder("Please enter ");
        for(Node tf : inputs.getChildren()) {
            String nodeText = ((TextField)tf).getText();
            if(nodeText.isEmpty()) {
                errorMsg.append(tf.getAccessibleText()).append(", ");
            }
            userInfo.add(nodeText);
        }
        if(errorMsg.length() > 13) signUpErrorLabel.setText(errorMsg.toString().substring(0, errorMsg.length()-2));
        else if(!emailPattern.matcher(userInfo.get(0)).matches()) {
            signUpErrorLabel.setText("Invalid email address");
        }
        else if(userController.getUser(userInfo.get(0)) != null) {
            signUpErrorLabel.setText("An account with this email already exists");
        }
        else if(!phonePattern.matcher(userInfo.get(4)).matches()) {
            signUpErrorLabel.setText("Invalid phone number");
        }
        else {
            userController.createUser(userInfo);
            signUpErrorLabel.setText("User successfully created, please log in");
        }
    }
}
