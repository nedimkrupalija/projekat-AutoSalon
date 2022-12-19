package ba.etf.unsa.rpr.controller;

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


    public TextField nameTextField;
    public Label nameLabel;
    public TextField passTextField;
    public Label passLabel;
    public Button registerButton;
    public Button backButton;
    private ArrayList<User> users;

    @FXML
    public void initialize(){
        try {
            users = (ArrayList<User>) new UserDaoSQLImpl().getAll();
        } catch (UserException e) {
            e.printStackTrace();
        }
        nameLabel.setText("");
        passLabel.setText("");
        nameTextField.textProperty().addListener((observableValue, s, newValue) -> {
            if(newValue.trim().length()<3||newValue.trim().length()>10){
                nameLabel.setText("3-10 karaktera!");
            }
            else {
                nameLabel.setText("");
                for(User x : users){
                    if(x.getName().equals(newValue)){
                        nameLabel.setText("User vec postoji!");
                        break;
                    }
                }
            }
        });
        passTextField.textProperty().addListener((observableValue, s, newValue) -> {
            if(newValue.trim().length()<3||newValue.trim().length()>10){
                passLabel.setText("3-10 karaktera!");
            }
            else passLabel.setText("");
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
            new UserDaoSQLImpl().insert(user);
        } catch (UserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greska!");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Ispravite podatke i probajte opet!");
            alert.showAndWait();
            System.out.println("Registracija neuspjesna!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspjesna registracija");
        alert.setHeaderText(null);
        alert.setContentText("Cestitamo, uspjesno ste registrovani. Nastavite na pocetnu stranicu!");
        alert.showAndWait();

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Stage currentStage = (Stage) nameLabel.getScene().getWindow();
        currentStage.close();
    }
}
