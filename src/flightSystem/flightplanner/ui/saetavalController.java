package flightSystem.flightplanner.ui;

import flightSystem.flightplanner.data.FlDataConnection;
import flightSystem.flightplanner.entities.Info;
import flightSystem.flightplanner.entities.Seat;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class saetavalController implements Initializable {
    private FlDataConnection connection;
    private Info information;
    @FXML
    private GridPane gridPane;

    public saetavalController() {
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }

    //-----------------------------------------------------------------------------
    // Inserted by T-team to integrate the seat picker UI
    public void goBack(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        information.getSeatButton().setText("Seat");
        information.setSeat(null);
        window.close();
    }

    public void confirmSelection(ActionEvent event) {
        if(information.getSeat() == null) return;
        information.getSeatButton().setText(information.getSeat().toString());
        information.addSelectedSeat(information.getSeat());
        information.setSeat(null);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
    //-----------------------------------------------------------------------------

    public void changeBookButtonPushed(ActionEvent event) throws IOException {
        Parent upplParent = FXMLLoader.load(getClass().getResource("upplysingar.fxml"));
        Scene clickUpplScene = new Scene(upplParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickUpplScene);
        window.show();
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent saetaParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene clickSaetaScene = new Scene(saetaParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickSaetaScene);
        window.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<Seat> seats = connection.getSeatsForFlight(information.getFlight().getID());
            for (Seat e : seats) {
                String seatNum = e.getSeatNumber();
                int[] colRow = getPlacementFromText(seatNum);
                Button btn = new Button(seatNum);
                btn.setDisable(e.isBooked() || information.isSeatSelected(e));
                if (e.isBooked()) {
                    btn.setStyle("-fx-background-color: Red");
                } else if(information.isSeatSelected(e)) {
                    btn.getStyleClass().add("prev-selected-seat");
                } else {
                    btn.setStyle("-fx-background-color: #0D7377");
                }
                btn.setOnAction(event -> {
                    seatClicked(btn);
                });
                if(e.getSeatNumber().equals(information.getSeatButton().getText())) {
                    seatClicked(btn);
                    information.removeSelectedSeat(e);
                    btn.setDisable(false);
                }

                GridPane.setHalignment(btn, HPos.CENTER);
                btn.setPrefWidth(50.0);
                gridPane.add(btn, colRow[0], colRow[1]);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void seatClicked(Button btn) {
        if ("-fx-background-color: Green".equals(btn.getStyle())) {
            btn.setStyle("-fx-background-color: #0D7377");
            information.setSeat(null);
        } else {
            Seat oldSeat = information.getSeat();
            btn.setStyle("-fx-background-color: Green");
            if (oldSeat != null) {
                String oldseatNum = information.getSeat().getSeatNumber();
                int oldcol = oldseatNum.charAt(oldseatNum.length() - 1);
                oldcol = oldcol - 65;
                int oldrow = Integer.parseInt(oldseatNum.substring(0, oldseatNum.length() - 1)) - 1;
                Node node = getNodeFromGridPane(gridPane, oldcol, oldrow);
                if (node != null) node.setStyle("-fx-background-color: #0D7377");
            }
            try {
                information.setSeat(connection.getSeat(information.getFlight().getID(), btn.getText()));
            } catch (Exception f) {
                System.err.println(f.getMessage());
            }
        }
    }

    private int[] getPlacementFromText(String seatNum) {
        int col = seatNum.charAt(seatNum.length() - 1);
        col = col - 65;
        int row = Integer.parseInt(seatNum.substring(0, seatNum.length() - 1)) - 1;
        int[] retVal = {col, row};
        return retVal;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
