package ba.etf.unsa.rpr.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;



public class loginController {
    public TextField usernameField;
    public void loginClick(ActionEvent actionEvent) {
        if(usernameField.getText().isEmpty()){
            usernameField.getStyleClass().add("fieldWrong");
            System.out.println("prazan username");
        }
        else usernameField.getStyleClass().add("fieldOk");
    }
}
