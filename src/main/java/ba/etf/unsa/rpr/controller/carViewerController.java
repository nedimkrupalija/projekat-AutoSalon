package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.business.CarManager;
import ba.etf.unsa.rpr.business.ReservationManager;
import ba.etf.unsa.rpr.business.UserManager;
import ba.etf.unsa.rpr.controller.alert.MyAlerts;
import ba.etf.unsa.rpr.dao.CarDaoSQLImpl;
import ba.etf.unsa.rpr.dao.DaoFactory;
import ba.etf.unsa.rpr.dao.ReservationDAOSQlImpl;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;
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
    public int adminId;
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
    public Button deleteButton;
    private boolean yearValidation;
    private boolean isValidated;

    public User user;

    private ArrayList<Reservation> reservations;

    // managers
    private final CarManager carManager = new CarManager();
    private final ReservationManager reservationManager = new ReservationManager();
    private final UserManager userManager = new UserManager();

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
        carsList.setItems(FXCollections.observableArrayList(CarDaoSQLImpl.getInstance().getAll()));
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

    private void addAllToButton(){
        colorMenu.getItems().add("Plava");
        colorMenu.getItems().add("Crvena");
        colorMenu.getItems().add("Narandzasta");
        colorMenu.getItems().add("Sucmurasta");
        colorMenu.getItems().add("Limun");
        colorMenu.getItems().add("Zelena");
        colorMenu.getItems().add("Crna");
        colorMenu.getItems().add("Bijela");
    }

    private void setLabels(){

        idLabel.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().getId()));
        nameText.setText(carsList.getSelectionModel().getSelectedItem().getName());
        yearText.setText(carsList.getSelectionModel().getSelectedItem().getYear());
        colorMenu.setValue(carsList.getSelectionModel().getSelectedItem().getColor());
        powerText.setText(String.valueOf(carsList.getSelectionModel().getSelectedItem().gethP()));
        descText.setText(carsList.getSelectionModel().getSelectedItem().getDescription());

    }

    private int setCarParms(Car car) throws CarException {

        String regex = "[0-9]+";
        if(!powerText.getText().matches(regex)){
            new MyAlerts().showWrongAlert(new CarException("Pogresni podaci unesite opet!"));
            return -1;
        }
        car.setName(nameText.getText());
        car.setColor(colorMenu.getValue());
        if(descText.getText().trim().isEmpty()) descText.setText("");
        car.setDescription(descText.getText());
        car.sethP(Integer.parseInt(powerText.getText()));
        car.setYear(yearText.getText());
        return 0;
    }


    /**
     * Initializer for text fields, field validation
     */
    @FXML
    public void initialize(){
            addAllToButton();
            removeAllCss();
            carsList.getSelectionModel().selectedItemProperty().addListener((observableValue, car, t1) -> {
                if(carsList.getSelectionModel().getSelectedItem()!=null) {
                    setLabels();
                    try {
                        reservedButton.getStyleClass().removeAll("reserved","notReserved");
                        if (reservationManager.isReserved(carsList.getSelectionModel().getSelectedItem().getId()) == 1) {
                            reservedButton.getStyleClass().add("reserved");
                        } else {
                            reservedButton.getStyleClass().add("notReserved");
                        }
                    } catch (Exception e) {
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
    public void insertButtonClick(ActionEvent actionEvent) throws CarException {

        if(!(isValidated&&yearValidation)) {
            new MyAlerts().showWrongAlert("Greska pri validaciji podataka");
            return;
        }
        Car car = new Car();
        if(setCarParms(car)==-1)
            return;

        try {
            carManager.insert(car);
        } catch (Exception e) {
            new MyAlerts().showWrongAlert(e);
        }

        new MyAlerts().showOkAlert("Uspjesno dodavanje","Auto uspjesno dodano, mozete nastaviti dalje!");
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
            new MyAlerts().showWrongAlert("Greska pri validaciji podataka!");
            return;
        }
        System.out.println(idLabel.getText());
        try {
            carManager.update(car,Integer.parseInt(idLabel.getText()));
        } catch (Exception e) {
            new MyAlerts().showWrongAlert(e);
        }


        new MyAlerts().showOkAlert("Uspjesna izmejena", "Auto uspjesno izmjenjeno, mozete nastaviti dalje!");
        updateList();

    }


    /**
     * Action for going back to main screen from car panel
     * @param actionEvent
     */
    public void backButtonClick(ActionEvent actionEvent) throws Exception {
        System.out.println("Povratak na admin panel!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPanel.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        stage.setScene(scene);
        Stage currentStage  = (Stage) idLabel.getScene().getWindow();
        stage.show();
        currentStage.close();


        //Setting fields in admin panel
        reservationViewController.setAdminFields(loader, userManager, adminId);

    }

    /**
     * Action for deleting car from database
     * @param actionEvent
     */
    public void deleteButtonClick(ActionEvent actionEvent) {
        System.out.printf("Odabrano brisanje vozila");

        try {
            carManager.delete(Integer.parseInt(idLabel.getText()));
        } catch (Exception e) {
            new MyAlerts().showWrongAlert(e);
            return;
        }

        new MyAlerts().showOkAlert("Uspjesno brisanje", "Auto uspjesno obrisanje mozete nastaviti dalje!");
        updateList();

    }
}
