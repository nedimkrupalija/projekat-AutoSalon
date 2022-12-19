package ba.etf.unsa.rpr.controller;


import ba.etf.unsa.rpr.domain.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class adminPanelController {

    public Label labelUser;

    public TextField textName;

    public TextField textPassword;


    public Label labelId;
    public Button categoryButton;
    public Button acceptButton;
    public Button backButton;



    /**
     * Listener for text fields
     * Sets css style if field is empty
     */
    @FXML
    public void initialize() {

    }



    /**
     * Action for returning to main screen from admin panel
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Vracanje na pocetni ekran!");
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setTitle("Login");
        // scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/css/style.css")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Stage thisStage = (Stage) labelId.getScene().getWindow();
        thisStage.close();
    }


