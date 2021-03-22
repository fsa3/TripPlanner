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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class searchUiController implements Initializable {
    private User user;

    @FXML
    private Label loginLabel;
    @FXML
    private TextField originInput;
    @FXML
    private TextField destinationInput;
    @FXML
    private DatePicker departureInput;
    @FXML
    private DatePicker returnInput;
    @FXML
    private TextField adultsInput;
    @FXML
    private TextField childrenInput;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<entities.Flight> allFlightsListView;
    @FXML
    private ListView<entities.Hotel> allHotelsListView;
    @FXML
    public ListView<entities.DayTrip> allDayTripsListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserController userController = new UserController();
        user = userController.loginUser("ahs33@hi.is", "nanna");
        loginLabel.setText(user.getFirstName());
        System.out.println(user + " er loggaður inn");
    }

    public void openLogin(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/loginUI.fxml"));
        Stage loginStage = new Stage();
        loginStage.setTitle("BookMaster");
        loginStage.setScene(new Scene(root, 600, 400));
        loginStage.show();
    }

    public void searchButtonClicked(ActionEvent actionEvent) {
        SearchResult sResult = new SearchResult(departureInput.getValue(), returnInput.getValue(), originInput.getText(), destinationInput.getText(), Integer.parseInt(adultsInput.getText()), Integer.parseInt(childrenInput.getText()));
        sResult.search();
        allFlightsListView.setItems(sResult.getFlightsObservable());
        allHotelsListView.setItems(sResult.getHotelsObservable());
        allDayTripsListView.setItems(sResult.getDayTripsObservable());
    }
}
