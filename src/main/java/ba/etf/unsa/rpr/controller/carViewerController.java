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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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
     * Private method for setting css to text fields
     * @param textField
     * @param value
     */
    private void setTextFieldCss(TextField textField, String value){
        textField.getStyleClass().removeAll("textFieldClass");
        if (value.trim().isEmpty() || value.trim().length() > 45) {
            textField.getStyleClass().add("textFieldClass");
        }
    }

    /**
     * Private method that removes all css from text fields
     */
    private void removeAllCss(){
        nameText.getStyleClass().removeAll("textFieldClass");
        yearText.getStyleClass().removeAll("textFieldClass");
        colorText.getStyleClass().removeAll("textFieldClass");
        powerText.getStyleClass().removeAll("textFieldClass");

    }

    /**
     * Initializer for text fields, field validation
     */
    @FXML
    public void initialize(){

            removeAllCss();
        carsList.getSelectionModel().selectedItemProperty().addListener((observableValue, car, t1) -> {



                    idLabel.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().getId()));
                    nameText.setText(carsList.getSelectionModel().getSelectedItem().getName());
                    yearText.setText(carsList.getSelectionModel().getSelectedItem().getYear());
                    colorText.setText(carsList.getSelectionModel().getSelectedItem().getColor());
                    powerText.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().gethP()));
                    descText.setText(carsList.getSelectionModel().getSelectedItem().getDesc());
                    try {
                        if (new ReservationDAOSQlImpl().isReserved(carsList.getSelectionModel().getSelectedItem().getId()) == 1) {
                            reservedButton.getStyleClass().add("reserved");
                        } else {
                            reservedButton.getStyleClass().add("notReserved");
                        }
                    } catch (ReservationException e) {
                        e.printStackTrace();
                    }
                });
            nameText.textProperty().addListener((observableValue1, s, t11) ->
                setTextFieldCss(nameText,t11));
        yearText.textProperty().addListener((observableValue1, s, t11) ->
                setTextFieldCss(yearText  ,t11));
        colorText.textProperty().addListener((observableValue1, s, t11) ->
                setTextFieldCss(colorText,t11));
        powerText.textProperty().addListener((observableValue1, s, t11) ->
                setTextFieldCss(powerText,t11));

        nameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

            }
        });

    }


    public void insertButtonClick(ActionEvent actionEvent) {
        System.out.println(carsList.getSelectionModel().getSelectedItems());
    }


    /**
     * Action for going back to main screen from car panel
     * @param actionEvent
     */
    public void updateButtonClick(ActionEvent actionEvent) {
    }
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Povratak na admin panel!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPanel.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        stage.setScene(scene);
        Stage currentStage  = (Stage) idLabel.getScene().getWindow();
        stage.show();
        currentStage.close();
    }

}
