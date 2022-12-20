package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.ReservationDAOSQlImpl;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.ReservationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class carViewerController {
    public ListView carsList;
    public VBox colorText;
    public TextField nameText;
    public TextField yearText;
    public TextField powerText;
    public TextArea descText;
    public Button insertButton;
    public Button updateButton;

    private ArrayList<Reservation> reservations;

    @FXML
    public void intialize(){
        try {
            reservations = (ArrayList<Reservation>) new ReservationDAOSQlImpl().getAll();
        } catch (ReservationException e) {
            e.printStackTrace();
    }
    }


    public void insertButtonClick(ActionEvent actionEvent) {
    }

    public void updateButtonClick(ActionEvent actionEvent) {
    }
}
