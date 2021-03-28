package ui.controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
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
    public AnchorPane sceneRoot;
    @FXML
    public VBox packagesVBox;

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
        try {
            AnchorPane resultRoot = FXMLLoader.load(getClass().getResource("../views/searchresultsUI.fxml"));
            sceneRoot.getChildren().setAll(resultRoot);
            displayPackages((VBox) sceneRoot.lookup("#packagesVBox"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    private void displayPackages(VBox packagesVBox) {
        AnchorPane packageAnchor = new AnchorPane();
        packageAnchor.getStyleClass().add("packageContainer");
        GridPane gp = new GridPane();
        gp.add(new Label("Package"), 0,0);
        gp.add(new Label("price"), 2, 0);

        VBox flights = new VBox();
        ImageView fImg = new ImageView();
        fImg.setImage(new Image("@../../img/landing.png"));
        fImg.setFitHeight(28);
        fImg.setPreserveRatio(true);
        fImg.setRotate(-45);
        Label flight = new Label("Reykjavík - Akureyri");
        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().add(fImg);
        hb.getChildren().add(flight);
        flights.getChildren().add(hb);
        // annað flug
        fImg = new ImageView();
        fImg.setImage(new Image("@../../img/landing.png"));
        fImg.setFitHeight(28);
        fImg.setPreserveRatio(true);
        flight = new Label("Reykjavík - Akureyri");
        hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().add(fImg);
        hb.getChildren().add(flight);
        flights.getChildren().add(hb);
        flights.setPrefWidth(300);

        gp.add(flights, 0, 1);

        VBox dayTrips = new VBox();
        HBox dt1 = new HBox();
        dt1.getChildren().add(new Label("Dagsferð"));
        HBox dt2 = new HBox();
        dt2.getChildren().add(new Label("Dagsferð"));
        dayTrips.getChildren().add(dt1);
        dayTrips.getChildren().add(dt2);
        gp.add(dayTrips, 1, 1, 2, 1);

        VBox hotel = new VBox();
        hotel.getChildren().add(new Label("Hotel OLV - 3 nætur - deluxe herbergi"));
        hotel.setAlignment(Pos.CENTER_LEFT);
        hotel.setPrefWidth(500);
        gp.add(hotel, 0, 2, 2, 1);

        HBox buttons = new HBox();
        buttons.getChildren().add(new Button("See more"));
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(20, 1);
        buttons.getChildren().add(spacer);
        buttons.getChildren().add(new Button("Book package"));
        gp.add(buttons, 2, 2);

        packagesVBox.setSpacing(10);
        packageAnchor.getChildren().add(gp);
        packagesVBox.getChildren().add(packageAnchor);

    }
}
