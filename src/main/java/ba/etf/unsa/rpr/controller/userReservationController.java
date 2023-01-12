package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.business.ReservationManager;
import ba.etf.unsa.rpr.controller.alert.MyAlerts;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class userReservationController {

    private final ReservationManager  reservationManager = new ReservationManager();

    @FXML
    public void initialize(){
        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, reservation, t1) -> {
            if(listView.getSelectionModel().getSelectedItem()==null){
                return;
            }
            try {
                reservationManager.delete(listView.getSelectionModel().getSelectedItem().getId());
            } catch (Exception e) {
                new MyAlerts().showWrongAlert("Greska pri brisanju");
            }
            new MyAlerts().showOkAlert("Uspjeh","Brisanje uspjesno!");
        });

    }
    public int userId;


    public ListView<Reservation> listView;
    public Button backButton;
    public Button removeButton;



    public void removeClick(ActionEvent actionEvent) {
    }
}
