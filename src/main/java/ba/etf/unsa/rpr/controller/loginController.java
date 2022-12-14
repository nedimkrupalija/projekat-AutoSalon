package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.Main;
import ba.etf.unsa.rpr.dao.SalesmanDao;
import ba.etf.unsa.rpr.dao.SalesmanDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Salesman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

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
     * Set all fields for next screen
     * @param loader
     */
    private void setNextFields(FXMLLoader loader){
        panelController panelController = loader.getController();
        panelController.labelUser.setText(panelController.labelUser.getText()+" "+usernameField.getText());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault());
        panelController.dateLabel.setText("Datum: " + date.format(formatter));
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Kategorija");
        arrayList.add("Vozila");
        ObservableList<String> observableList = FXCollections.observableArrayList(arrayList);
        panelController.picker.setItems(observableList);
    }

    /**
     * Method to set data for user that logged in
     * @param loader
     * @param salesman
     */
    private void setData(FXMLLoader loader, Salesman salesman)
    {
        panelController panelController = loader.getController();
        panelController.labelId.setText(String.valueOf(salesman.getId()));
        panelController.textIme.setText(salesman.getName());
        panelController.textPrezime.setText(salesman.getSurname());
        panelController.textTelefon.setText(salesman.getNumber());
        panelController.textPassword.setText(salesman.getPassword());
    }


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
            Salesman salesman = salesmanDao.getByNamePass(usernameField.getText(),passwordField.getText());
            if(salesman==null) {
                setWrongUser();
                setWrongPass();
                System.out.println("Prijava neuspjesna!");
            }
            else{
                System.out.println("Prijava uspjesna, user: " + usernameField.getText());
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/panel.fxml"));
                Parent root = loader.load();
                setNextFields(loader);
                Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
                newStage.setTitle("Admin panel");
                newStage.setScene(scene);
                //newStage.setResizable(false);
                newStage.show();
                Stage primaryStage = (Stage) usernameField.getScene().getWindow();
                primaryStage.close();
            }
        }
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
