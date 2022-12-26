package ba.etf.unsa.rpr.controller;


import ba.etf.unsa.rpr.business.CarManager;
import ba.etf.unsa.rpr.business.ReservationManager;
import ba.etf.unsa.rpr.business.UserManager;
import ba.etf.unsa.rpr.controller.alert.MyAlerts;
import ba.etf.unsa.rpr.dao.CarDaoSQLImpl;
import ba.etf.unsa.rpr.dao.ReservationDAOSQlImpl;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class adminPanelController {

    public Label labelUser;

    public TextField textName;

    public TextField textPassword;


    public Label labelId;
    public Button acceptButton;
    public Button backButton;
    public User user;
    public RadioButton radioButtonCar;
    public RadioButton radioButtonReservation;
    public Button nextButton;
    private ArrayList<User> users;

    private ArrayList<Car> cars;

    private ArrayList<Reservation> reservations;

    // managers
    private final CarManager carManager = new CarManager();
    private final ReservationManager reservationManager = new ReservationManager();
    private final UserManager userManager = new UserManager();






    /**
     * Listener for text fields
     * Sets css style if field is empty
     */
    @FXML
    public void initialize() throws Exception {
        reservations = (ArrayList<Reservation>) reservationManager.getAll();
        cars = (ArrayList<Car>) carManager.getAll();
        ToggleGroup group = new ToggleGroup();
        radioButtonCar.setToggleGroup(group);
        radioButtonReservation.setToggleGroup(group);
        users = (ArrayList<User>) userManager.getAll();
        textName.textProperty().addListener((observableValue, s, t1) -> {
            textName.getStyleClass().removeAll("fieldWrong");
            if(t1.trim().length()==0) textName.getStyleClass().add("fieldWrong");
            for(User x : users) {
                if(x.getName().equals(t1)) textName.getStyleClass().add("fieldWrong");
                break;
            }
        });
        textPassword.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.trim().length()==0) textPassword.getStyleClass().add("fieldWrong");
            else textPassword.getStyleClass().removeAll("fieldWrong");
        });
    }



    /**
     * Action for returning to main screen from admin panel
     *
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Vracanje na pocetni ekran!");
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Stage thisStage = (Stage) labelId.getScene().getWindow();
        thisStage.close();
    }

    /**
     * Action for admin to change its own credentials
     * @param actionEvent
     */
    public void acceptClicked(ActionEvent actionEvent) {
        User user = new User();
        user.setName(textName.getText());
        user.setPassword(textPassword.getText());
        try {
            userManager.update(user,Integer.parseInt(labelId.getText()));
            if(textName.getText().trim().isEmpty()||textPassword.getText().isEmpty())
                throw new UserException("Polja prazna!");
        } catch (Exception e) {
            new MyAlerts().showWrongAlert(e);
            return;
        }
        new MyAlerts().showOkAlert("Izmjena podataka","Podaci uspjesno izmjenjeni");

    }


    /**
     *  Action for going to car or reservation viewer
     * @param actionEvent
     */
    public void nextButtonClick(ActionEvent actionEvent) throws IOException {
        if(radioButtonCar.isSelected()){

            System.out.println("Izabran pregled vozila!");
            FXMLLoader loader = openScreen("/fxml/carViewer.fxml","Pregled vozila");

            //close current scene
            Stage thisStage = (Stage) labelId.getScene().getWindow();
            thisStage.close();

            //Setting values for listView in car view panel
            carViewerController carViewerController = loader.getController();
            ObservableList<Car> list = FXCollections.observableArrayList(cars);
            carViewerController.carsList.setItems(list);
            carViewerController.adminId = Integer.parseInt(labelId.getText());



        }
        else {
            System.out.println("Odabran pregled rezrvacija");
            FXMLLoader loader = openScreen("/fxml/reservationViewer.fxml","Pregled rezervacija");

            //Setting data for reservation viewer
            reservationViewController reservationViewController = loader.getController();
            ObservableList<Reservation> list = FXCollections.observableArrayList(reservations);
            reservationViewController.reservationList.setItems(list);
            reservationViewController.adminId = Integer.parseInt(labelId.getText());

            Stage thisStage = (Stage) labelId.getScene().getWindow();
            thisStage.close();
        }
    }

    private FXMLLoader openScreen(String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(title);
        return loader;
    }

}


