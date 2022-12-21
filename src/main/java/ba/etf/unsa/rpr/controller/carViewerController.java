package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.CarDaoSQLImpl;
import ba.etf.unsa.rpr.dao.ReservationDAOSQlImpl;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class carViewerController {
    public Button reservedButton;
    public Label idLabel;
    public ListView<Car> carsList;
    public TextField nameText;
    public TextField yearText;
    public TextField powerText;
    public TextArea descText;
    public Button insertButton;
    public Button updateButton;
    public Label nameError;
    public Label yearError;
    public Label colorError;
    public Label powerError;
    public ChoiceBox<String> colorMenu;
    private boolean yearValidation;
    private boolean isValidated;

    public User user;

    private ArrayList<Reservation> reservations;

    /**
     * Private method for setting css to text fields
     * @param textField
     * @param value
     */
    private void setTextFieldCss(TextField textField,Label label, String value){
        isValidated = true;
        textField.getStyleClass().removeAll("textFieldClass");
        label.setText("");
        if (value.trim().isEmpty() || value.trim().length() > 45) {
            textField.getStyleClass().add("textFieldClass");
            label.setText("1 do 45 karaktera!");
            isValidated = false;
        }
    }

    /**
     * Private method for updating list of cars in db
     */
    private void updateList(){
        carsList.setItems(FXCollections.observableArrayList(new CarDaoSQLImpl().getAll()));
    }

    /**
     * Private method that removes all css from text fields
     */
    private void removeAllCss(){
        nameText.getStyleClass().removeAll("textFieldClass");
        yearText.getStyleClass().removeAll("textFieldClass");
        colorMenu.getStyleClass().removeAll("textFieldClass");
        powerText.getStyleClass().removeAll("textFieldClass");

    }

    /**
     * Initializer for text fields, field validation
     */
    @FXML
    public void initialize(){
        colorMenu.getItems().add("Plava");
        colorMenu.getItems().add("Crvena");
        colorMenu.getItems().add("Narandzasta");
        colorMenu.getItems().add("Sucmurasta");
        colorMenu.getItems().add("Limun");
        colorMenu.getItems().add("Zelena");
        colorMenu.getItems().add("Crna");
        colorMenu.getItems().add("Bijela");
            removeAllCss();
            carsList.getSelectionModel().selectedItemProperty().addListener((observableValue, car, t1) -> {
                if(carsList.getSelectionModel().getSelectedItem()!=null) {
                    idLabel.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().getId()));
                    nameText.setText(carsList.getSelectionModel().getSelectedItem().getName());
                    yearText.setText(carsList.getSelectionModel().getSelectedItem().getYear());
                    colorMenu.setValue(carsList.getSelectionModel().getSelectedItem().getColor());
                    powerText.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().gethP()));
                    descText.setText(carsList.getSelectionModel().getSelectedItem().getDescription());
                    try {
                        if (new ReservationDAOSQlImpl().isReserved(carsList.getSelectionModel().getSelectedItem().getId()) == 1) {
                            reservedButton.getStyleClass().add("reserved");
                        } else {
                            reservedButton.getStyleClass().add("notReserved");
                        }
                    } catch (ReservationException e) {
                        e.printStackTrace();
                    }
                }
            });

            nameText.textProperty().addListener((observableValue1, s, t11) ->
                setTextFieldCss(nameText,nameError,t11));
        yearText.textProperty().addListener((observableValue1, s, t11) ->
        {
            yearError.setText("");
            yearText.getStyleClass().removeAll("textFieldClass");
            yearValidation = true;
            try{
                Integer.parseInt(yearText.getText());
            }
            catch(Exception e){
                yearError.setText("Pogresna godina!");
                yearText.getStyleClass().add("textFieldClass");
                yearValidation = false;
            }
            if(yearText.getText().trim().length()!=4||Integer.parseInt(yearText.getText())>(Integer.parseInt(String.valueOf(Year.now())))) {
                yearError.setText("Pogresna godina!");
                yearText.getStyleClass().add("textFieldClass");
                yearValidation = false;
            }
        });
        colorMenu.selectionModelProperty().addListener((observableValue1, s, t11) -> {
            isValidated = colorMenu.getValue() != null;
                });
        powerText.textProperty().addListener((observableValue1, s, t11) ->
                setTextFieldCss(powerText,powerError,t11));
    }


    /**
     * Action for inserting car into db
     * @param actionEvent
     */
    public void insertButtonClick(ActionEvent actionEvent) {

        if(!(isValidated&&yearValidation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greska!");
            alert.setHeaderText("Greska pri validaciji podataka!");
            alert.setContentText("Ispravite podatke i pokusajte opet!");
            alert.showAndWait();
            return;
        }
        Car car = new Car();
        car.setName(nameText.getText());
        car.setColor(colorMenu.getValue());
        if(descText.getText().trim().isEmpty()) descText.setText("");
        car.setDescription(descText.getText());
        car.sethP(Integer.parseInt(powerText.getText()));
        car.setYear(yearText.getText());
        try {
            new CarDaoSQLImpl().insert(car);

        } catch (CarException e) {
            e.printStackTrace();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspjesno dodavanje");
        alert.setHeaderText(null);
        alert.setContentText("Auto uspjesno dodano, mozete nastaviti dalje!");
        alert.showAndWait();
        updateList();
    }


    /**
     * Action for updating car
     * @param actionEvent
     */
    public void updateButtonClick(ActionEvent actionEvent) {
        Car car = new Car();
        car.setColor(colorMenu.getValue());
        car.setYear(yearText.getText());
        if(descText.getText().trim().isEmpty()) descText.setText("");
        car.setDescription(descText.getText());
        car.sethP(Integer.parseInt(powerText.getText()));
        car.setName(nameText.getText());

        if(!(isValidated&&yearValidation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greska!");
            alert.setHeaderText("Greska pri validaciji podataka!");
            alert.setContentText("Ispravite podatke i pokusajte opet!");
            alert.showAndWait();
            return;
        }
        try {
            System.out.println(idLabel.getText());
            new CarDaoSQLImpl().update(car,Integer.parseInt(idLabel.getText()));
        } catch (CarException e) {
            e.printStackTrace();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspjesno dodavanje");
        alert.setHeaderText(null);
        alert.setContentText("Auto uspjesno dodano, mozete nastaviti dalje!");
        alert.showAndWait();
        updateList();

    }


    /**
     * Action for going back to main screen from car panel
     * @param actionEvent
     */
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

        /*//Set fields in admin panel
        adminPanelController adminPanelController = loader.getController();
        adminPanelController.labelId.setText(String.valueOf(user.getId()));
        adminPanelController.textName.setText(user.getName());
        adminPanelController.textPassword.setText(user.getPassword());
        adminPanelController.labelUser.setText(user.getName());*/

    }

}
