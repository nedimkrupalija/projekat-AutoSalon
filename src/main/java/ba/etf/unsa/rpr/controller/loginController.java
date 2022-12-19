package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.UserDao;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class loginController {

    /**
     * Private method to set css style of user field
     */

    private void setWrongUser(){
        usernameField.getStyleClass().removeAll("fieldWrong");
        usernameField.getStyleClass().add("fieldWrong");
    }

    /**
     * Private method to set css style of pass field
     */

    private void setWrongPass(){
        passwordField.getStyleClass().removeAll("fieldWrong");
        passwordField.getStyleClass().add("fieldWrong");
    }

    public TextField usernameField;
    public PasswordField passwordField;

    /**
     * Initialize method / sets user and pass name red if they are empty
     */
    @FXML
    public void initialize(){
        usernameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue.trim().isEmpty()){
                    setWrongUser();
                }
                else usernameField.getStyleClass().removeAll("fieldWrong");
            }
        });
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.trim().isEmpty()) setWrongPass();
                else passwordField.getStyleClass().removeAll("fieldWrong");
            }
        });
    }


    /**
     * Go to admin panel if id and pass match
     * @param actionEvent btn click
     */
    public void loginClick(ActionEvent actionEvent) throws IOException {

    }

    /**
     * Method to go back to main screen in case of misslick or forgotten password etc.
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        mainStage.setTitle("Pocetni zaslon");
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();
        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        currentStage.close();
        System.out.println("Korisnik se vratio na pocetak");
    }
}
