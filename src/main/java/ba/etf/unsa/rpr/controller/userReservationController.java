package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.business.CarManager;
import ba.etf.unsa.rpr.business.ReservationManager;
import ba.etf.unsa.rpr.controller.alert.MyAlerts;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
    public int userId;




    private final ReservationManager  reservationManager = new ReservationManager();
    private final CarManager carManager = new CarManager();
    private void updateList() throws ReservationException {
        listView.setItems(FXCollections.observableArrayList(reservationManager.getUserReservations(userId)));
    }





    public ListView<Reservation> listView;
    public Button backButton;
    public Button removeButton;


    /**
     * Action that removes reservation for user
     * @param actionEvent
     * @throws ReservationException
     */
    public void removeClick(ActionEvent actionEvent) throws ReservationException {
        try {
            reservationManager.delete(listView.getSelectionModel().getSelectedItem().getId());
        } catch (Exception e) {
            new MyAlerts().showWrongAlert("Greska pri brisanju");
        }
        new MyAlerts().showOkAlert("Uspjeh","Brisanje uspjesno!");
        updateList();
    }

    /**
     * Go back to previous screen
     * @param actionEvent
     * @throws ReservationException
     * @throws CarException
     */
    public void backButtonClick(ActionEvent actionEvent) throws ReservationException, CarException, IOException {
        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userPanel.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();

        userController userController = loader.getController();
        userController.carListView.setItems(FXCollections.observableArrayList(carManager.getNotReservated()));


        Stage currentStage = (Stage) listView.getScene().getWindow();
        currentStage.close();
        System.out.println("Korisnik se vratio na user panel");
    }
}
