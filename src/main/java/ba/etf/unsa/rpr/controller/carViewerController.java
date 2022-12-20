package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.ReservationDAOSQlImpl;
import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.ReservationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class carViewerController {
    public Button reservedButton;
    public Label idLabel;
    public ListView<Car> carsList;
    public TextField colorText;
    public TextField nameText;
    public TextField yearText;
    public TextField powerText;
    public TextArea descText;
    public Button insertButton;
    public Button updateButton;

    private ArrayList<Reservation> reservations;


    /**
     * Initializer for text fields, field validation
     */
    @FXML
    public void initialize(){
        carsList.getSelectionModel().selectedItemProperty().addListener((observableValue, car, t1) -> {
            idLabel.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().getId()));
            nameText.setText(carsList.getSelectionModel().getSelectedItem().getName());
            yearText.setText(carsList.getSelectionModel().getSelectedItem().getYear());
            colorText.setText(carsList.getSelectionModel().getSelectedItem().getColor());
            powerText.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().gethP()));
            descText.setText(carsList.getSelectionModel().getSelectedItem().getDesc());
            try {
                if(new ReservationDAOSQlImpl().isReserved(carsList.getSelectionModel().getSelectedItem().getId())==1) {
                    reservedButton.getStyleClass().add("reserved");
                }
                else {
                    reservedButton.getStyleClass().add("notReserved");
                }
            } catch (ReservationException e) {
                e.printStackTrace();
            }
        });
    }


    public void insertButtonClick(ActionEvent actionEvent) {
        System.out.println(carsList.getSelectionModel().getSelectedItems());
    }

    public void updateButtonClick(ActionEvent actionEvent) {
    }
}
