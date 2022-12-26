package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.business.CarManager;
import ba.etf.unsa.rpr.business.ReservationManager;
import ba.etf.unsa.rpr.business.UserManager;
import ba.etf.unsa.rpr.controller.alert.MyAlerts;
import ba.etf.unsa.rpr.dao.UserDao;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class registrationController {

    // managers
    private final CarManager carManager = new CarManager();
    private final UserManager userManager = new UserManager();
    private final ReservationManager reservationManager = new ReservationManager();

    public TextField nameTextField;
    public Label nameLabel;
    public TextField passTextField;
    public Label passLabel;
    public Button registerButton;
    public Button backButton;
    private ArrayList<User> users;

    @FXML
    public void initialize(){
        users = (ArrayList<User>) new UserDaoSQLImpl().getAll();
        nameLabel.setText("");
        passLabel.setText("");
        nameTextField.textProperty().addListener((observableValue, s, newValue) -> {
                nameTextField.getStyleClass().removeAll("fieldWrong");
            if(newValue.trim().length()<3||newValue.trim().length()>10){
                nameLabel.setText("3-10 karaktera!");
                nameTextField.getStyleClass().add("fieldWrong");
            }
            else {
                nameLabel.setText("");
                for(User x : users){
                    if(x.getName().equals(newValue)){
                        nameLabel.setText("User vec postoji!");
                        nameTextField.getStyleClass().add("fieldWrong");
                        break;
                    }
                }
            }
        });
        passTextField.textProperty().addListener((observableValue, s, newValue) -> {
            if(newValue.trim().length()<3||newValue.trim().length()>10){
                passLabel.setText("3-10 karaktera!");
                passTextField.getStyleClass().add("fieldWrong");
            }
            else{
                passTextField.getStyleClass().removeAll("fieldWrong");
                passLabel.setText("");
            }
        });
    }

    /**
     * Action for registering user
     * Shows alert if data cannot be validated
     * @param actionEvent
     */
    public void registerButtonClick(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setName(nameTextField.getText());
        user.setPassword(passTextField.getText());
        try {
            userManager.insert(user);
            if(nameTextField.getText().length()<3 || nameTextField.getText().length()>10) throw new UserException("Prekrato ime!");
            if(passTextField.getText().length()<3 || passTextField.getText().length()>10) throw new UserException("Prekratka sifra!");
        } catch (Exception e) {

            new MyAlerts().showWrongAlert(e);

            System.out.println("Registracija neuspjesna!");
            return;
        }
        new MyAlerts().showOkAlert("Uspjesna registracija", "Cestitamo, uspjesno ste registrovani. Nastavite na pocetnu stranicu!");
        setMainScene();
        Stage currentStage = (Stage) nameLabel.getScene().getWindow();
        currentStage.close();
        System.out.println("Registracija uspjesna!");
    }

    /**
     * Action for returning back to main screen
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Nazad na pocetni ekran");
        setMainScene();
        Stage currentStage = (Stage) nameLabel.getScene().getWindow();
        currentStage.close();
    }

    private void setMainScene() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



}
