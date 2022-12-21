package ba.etf.unsa.rpr.controller;

import javafx.event.ActionEvent;
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

    public ListView reservationList;
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
