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


    public Button prodavacButton;

    public void prodavacClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Izabran prodavac");
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setTitle("Login");
        // scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/css/style.css")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Stage primaryStage = (Stage) prodavacButton.getScene().getWindow();
        primaryStage.close();
    }
}
