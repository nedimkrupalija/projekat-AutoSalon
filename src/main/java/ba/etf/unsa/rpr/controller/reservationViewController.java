package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.CarDaoSQLImpl;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

    /**
     * Initialization of data
     * Listeners for validation
     * @throws CarException
     * @throws UserException
     */
    @FXML
    public void initialize() throws CarException, UserException {
        idLabel.setText(String.valueOf(reservationList.getSelectionModel().getSelectedItem().getId()));
        labelCar.setText(new CarDaoSQLImpl().getById(reservationList.getSelectionModel().getSelectedItem().getId()).getName());
        labelUser.setText(new UserDaoSQLImpl().getById(reservationList.getSelectionModel().getSelectedItem().getId()).getName());
        datePickerReservation.setValue(reservationList.getSelectionModel().getSelectedItem().getReservationDate().toLocalDate());
        pickerArrivalDate.setValue(reservationList.getSelectionModel().getSelectedItem().getArrivalDate().toLocalDate());

    }

    /**
     * Action for going back to main admin panel
     * @param actionEvent
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Nazad na admin panel!");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPanel.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setScene(scene);
        Stage currentStage = (Stage) idLabel.getScene().getWindow();
        stage.show();
        currentStage.close();
    }

    /**
     * Private method for validating car
     * @param date
     */
    private boolean validateDate(Date date) {
        return date.compareTo(dateOfArrival) > 0;
    }


    /**
     * Action for changing date of arrival of car
     * @param actionEvent
     */
    public void changeDateButtonClick(ActionEvent actionEvent) {

    }
}
