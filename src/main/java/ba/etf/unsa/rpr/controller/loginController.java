package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.business.UserManager;
import ba.etf.unsa.rpr.controller.alert.MyAlerts;
import ba.etf.unsa.rpr.dao.UserDao;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class loginController {


    // manager
    private final UserManager userManager = new UserManager();


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
     * Action that takes user to admin/user panel if credentials are correct (username,password)
     * @param actionEvent btn click
     */
    public void loginClick(ActionEvent actionEvent) throws Exception {
        User user = null;
        try {
            user = userManager.getByUserPass(usernameField.getText(),passwordField.getText());
        } catch (UserException e) {
            new MyAlerts().showWrongAlert(e);
            return;
        }
        Stage stage = new Stage();
        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        if(user.getAdmin()==1){
            System.out.println("Login administratora: "+ user.getName());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPanel.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
            stage.setTitle("Admin panel");
            //stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            currentStage.close();

            //Set fields for admin panel
            adminPanelController adminPanelController = loader.getController();
            adminPanelController.labelId.setText(String.valueOf(user.getId()));
            adminPanelController.textName.setText(user.getName());
            adminPanelController.textPassword.setText(user.getPassword());
            adminPanelController.labelUser.setText(user.getName());



        }
        else{
            System.out.println("Login korisnika: " + user.getName());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userPanel.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
            stage.setTitle("Korisnicki panel");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            currentStage.close();
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
