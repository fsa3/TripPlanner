package ui.controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserUIController implements Initializable {

    @FXML private UserInfoController userInfoController;
    @FXML private UserBookingsController userBookingsController;
    @FXML private TabPane userTabPane;
    @FXML private Tab bookingsTab;

    private User user;
    private SearchUiController owningSearchController;
    private BookingUiController owningBookingController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setOwningController(SearchUiController owningSearchController) {
        this.owningSearchController = owningSearchController;
    }

    public void setOwningController(BookingUiController owningBookingController) {
        this.owningBookingController = owningBookingController;
    }

    public void setUser(User user) {
        this.user = user;
        userInfoController.setUser(user);
        if(owningSearchController != null) userInfoController.setOwningSearchController(owningSearchController);
        if(owningBookingController != null) userInfoController.setOwningBookingController(owningBookingController);
        userBookingsController.setUser(user);
    }

    public void openBookinsTab() {
        userTabPane.getSelectionModel().select(bookingsTab);
    }
}
