package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.UserDao;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

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
                nameLabel.setText("Ime treba biti duzine 3-10 karaktera!");
            }
            else {
                nameLabel.setText("");
                for(User x : users){
                    if(x.getName().equals(nameLabel.getText())){
                        nameLabel.setText("Korisnik s tim imenom vec postoji!");
                        break;
                    }
                }
            }
        });
        passTextField.textProperty().addListener((observableValue, s, newValue) -> {
            if(newValue.trim().length()<3||newValue.trim().length()>10){
                passLabel.setText("Ime treba biti duzine 3-10 karaktera!");
            }
            else passLabel.setText("");
        });
    }

    public void registerButtonClick(ActionEvent actionEvent) {
    }

    public void backButtonClick(ActionEvent actionEvent) {
    }
}
