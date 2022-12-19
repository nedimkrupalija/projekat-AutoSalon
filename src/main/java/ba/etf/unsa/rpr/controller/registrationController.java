package ba.etf.unsa.rpr.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class registrationController {


    public TextField nameTextField;
    public Label nameLabel;
    public TextField passTextField;
    public Label passLabel;
    public Button registerButton;
    public Button backButton;

    @FXML
    public void initialize(){
        nameLabel.setText("");
        passLabel.setText("");
        nameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                if(newValue.trim().length()<3||newValue.trim().length()>10){
                    nameLabel.setText("Ime treba biti duzine 3-10 karaktera!");
                }
                else nameLabel.setText("");
            }
        });
        passTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                if(newValue.trim().length()<3||newValue.trim().length()>10){
                    passLabel.setText("Ime treba biti duzine 3-10 karaktera!");
                }
                else passLabel.setText("");
            }
        });
    }

    public void registerButtonClick(ActionEvent actionEvent) {
    }

    public void backButtonClick(ActionEvent actionEvent) {
    }
}
