package ba.etf.unsa.rpr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class MainController {

    private void setNewScene(String path, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("path"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public Button loginButton;

    public void loginButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Izabran login");
        setNewScene("/fxml/login.fxml","Login");

        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
        primaryStage.hide();
    }


    /**
     * Event for going to registration screen from main screen
     * @param actionEvent
     */
    public void registrationButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Izabrana registracija!");
        setNewScene("/fxml/registration.fxml","Registracija");
        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();
    }
}
