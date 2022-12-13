package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.SalesmanDao;
import ba.etf.unsa.rpr.dao.SalesmanDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Salesman;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;



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
        usernameField.getStyleClass().add("fieldWrong");
    }

    public TextField usernameField;
    public PasswordField passwordField;

    /**
     * Go to admin panel if salesman is chosen
     * @param actionEvent btn click
     */
    public void loginClick(ActionEvent actionEvent) {
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

            }
        }
    }
}
