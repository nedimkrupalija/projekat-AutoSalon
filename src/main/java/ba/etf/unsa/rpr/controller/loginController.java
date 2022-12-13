package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.SalesmanDao;
import ba.etf.unsa.rpr.dao.SalesmanDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Salesman;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
     * Go to admin panel if salesman is chosen
     * @param actionEvent btn click
     */
    public void loginClick(ActionEvent actionEvent) throws IOException {
        if(passwordField.getText().isEmpty()){
            setWrongPass();
            System.out.println("Prazno polje za password.");
        }
        if(usernameField.getText().trim().isEmpty()) {
            setWrongUser();
            System.out.println("Prazan username");
        }
        else {
            SalesmanDao salesmanDao = new SalesmanDaoSQLImpl();
            Salesman salesman = salesmanDao.getByNamePass(usernameField.toString(),passwordField.toString());
            if(salesman==null) {
                setWrongUser();
                setWrongPass();
                System.out.println("Prijava neuspjesna!");
            }
            else{
                System.out.println("Prijava uspjesna, user: " + usernameField);
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/panel.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
                newStage.setTitle("Admin panel");
                newStage.setScene(scene);
                newStage.setResizable(false);
                newStage.show();
                Stage primaryStage = (Stage) usernameField.getScene().getWindow();
                primaryStage.close();
            }
        }
    }
}
