package ui.controllers;

import controllers.UserController;
import data.DataConnection;
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
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchUiController implements Initializable {

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
    @FXML
    public AnchorPane sceneRoot;

    private Stage searchStage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void openLogin(MouseEvent mouseEvent) throws IOException {
        searchStage = (Stage) sceneRoot.getScene().getWindow();

        Stage loginStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/loginUI.fxml"));
        Region root = loader.load();

        Scene scene = new Scene(root);
        loginStage.setScene(scene);

        LoginUiController loginUiController = loader.getController();
        loginUiController.setOwningController(this);
        if(user != null) loginUiController.setUser(user);

        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.initOwner(searchStage);
        loginStage.show();
    }

    public void searchButtonClicked(ActionEvent actionEvent) {
        SearchResult sResult = new SearchResult(departureInput.getValue(), returnInput.getValue(), originInput.getText(), destinationInput.getText(), Integer.parseInt(adultsInput.getText()), Integer.parseInt(childrenInput.getText()));
        sResult.search();
        allFlightsListView.setItems(sResult.getFlightsObservable());
        allHotelsListView.setItems(sResult.getHotelsObservable());
        allDayTripsListView.setItems(sResult.getDayTripsObservable());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        loginLabel.setText(this.user.getFirstName());
    }

    public void mouseOnLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(true);
    }

    public void mouseOffLoginLabel(MouseEvent mouseEvent) {
        loginLabel.setUnderline(false);
    }
}
