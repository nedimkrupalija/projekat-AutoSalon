package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.CarDaoSQLImpl;
import ba.etf.unsa.rpr.dao.ReservationDAOSQlImpl;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class reservationViewController {

    public ListView<Reservation> reservationList;
    public Label idLabel;
    public Label labelDate;

    public Date dateOfArrival;
    public DatePicker pickerArrivalDate;
    public Button changeDateButton;
    public Label labelCar;
    public Label labelUser;
    public Button deleteButton;
    public Button backButton;
    public Label labelDateError;
    public DatePicker datePickerReservation;

    public int adminId;

    /**
     * Initialization of data
     * Listeners for validation
     */
    @FXML
    public void initialize() {
        reservationList.getSelectionModel().selectedItemProperty().addListener((observableValue, reservation, t1) -> {
            if(reservationList.getSelectionModel().getSelectedItem()!=null){

                idLabel.setText(String.valueOf(reservationList.getSelectionModel().getSelectedItem().getId()));
                labelCar.setText(reservationList.getSelectionModel().getSelectedItem().getCar().getName());
                labelUser.setText(reservationList.getSelectionModel().getSelectedItem().getUser().getName());
                datePickerReservation.setValue(reservationList.getSelectionModel().getSelectedItem().getReservationDate().toLocalDate());
                pickerArrivalDate.setValue(reservationList.getSelectionModel().getSelectedItem().getArrivalDate().toLocalDate());
            }
        });

        pickerArrivalDate.pressedProperty().addListener((observableValue, eventHandler, t1) -> {
            labelDateError.setText("");
            if(!validateDate()) labelDateError.setText("Datum mora biti veci od dat. rezervacije!");
        });




    }

    /**
     * Action for going back to main admin panel
     * @param actionEvent
     */
    public void backButtonClick(ActionEvent actionEvent) throws Exception {
        System.out.println("Nazad na admin panel!");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPanel.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setScene(scene);
        Stage currentStage = (Stage) idLabel.getScene().getWindow();
        stage.show();

        //Setting field in admin panel
        User admin = new UserDaoSQLImpl().getById(adminId);
        adminPanelController adminPanelController = loader.getController();
        adminPanelController.labelId.setText(String.valueOf(admin.getId()));
        adminPanelController.textName.setText(admin.getName());
        adminPanelController.textPassword.setText(admin.getPassword());
        adminPanelController.labelUser.setText(admin.getName());


        currentStage.close();
    }

    /**
     * Private method for validating date
     */
    private boolean validateDate() {
        if(datePickerReservation.getValue()!=null&&pickerArrivalDate.getValue()!=null) {
            return pickerArrivalDate.getValue().compareTo(datePickerReservation.getValue()) > 0;
        }
        return false;
    }


    /**
     * Action for changing date of arrival of car
     * @param actionEvent
     */
    public void changeDateButtonClick(ActionEvent actionEvent) {
        Date date = Date.valueOf(pickerArrivalDate.getValue());
        System.out.println("Izmjena datuma!");
        try {
            new ReservationDAOSQlImpl().updateArrivalDate(date, reservationList.getSelectionModel().getSelectedItem().getId());
        } catch (ReservationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greska!");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Ispravite podatke i pokusajte opet!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspjesna izmjena");
        alert.setHeaderText(null);
        alert.setContentText("Podaci uspjesno izmjenjeni, mozete nastaviti dalje!");
        alert.showAndWait();
    }
}
