package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.business.CarManager;
import ba.etf.unsa.rpr.business.ReservationManager;
import ba.etf.unsa.rpr.business.UserManager;
import ba.etf.unsa.rpr.controller.alert.MyAlerts;
import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class userController {

    public ListView<Car> carListView;
    public Button showReservations;
    public Button reservationButton;
    public Button backButton;
    public TextArea textArea;
    public int userId;
    //Managers
    public final ReservationManager reservationManager = new ReservationManager();
    public final CarManager carManager = new CarManager();
    public final UserManager userManager = new UserManager();

    public void updateList() throws ReservationException, CarException {
        carListView.setItems(FXCollections.observableArrayList(carManager.getNotReservated()));
    }

    @FXML
    public void initialize(){
        carListView.getSelectionModel().selectedItemProperty().addListener((observableValue, car, t1) -> {
            if(carListView.getSelectionModel().getSelectedItem()!=null){
                textArea.setText(carListView.getSelectionModel().getSelectedItem().getDescription());
            }

        });
    }

    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        mainStage.setTitle("Pocetni zaslon");
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();
        Stage currentStage = (Stage) showReservations.getScene().getWindow();
        currentStage.close();
        System.out.println("Korisnik se vratio na pocetak");
    }

    /**
     * Show reservations for current user
     * @param actionEvent
     * @throws IOException
     */
    public void showResClick(ActionEvent actionEvent) throws IOException, ReservationException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userReservation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setResizable(false);
        stage.setScene(scene);
        //Remember userID
        userReservationController uRC = loader.getController();
        uRC.userId = userId;
        uRC.listView.setItems(FXCollections.observableArrayList(reservationManager.getUserReservations(userId)));
        stage.show();
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();


    }

    /**
     * Action that reserves car for certain user
     * @param actionEvent
     * @throws Exception
     */
    public void reservateButtonClick(ActionEvent actionEvent) throws Exception {
        if(carListView.getSelectionModel().getSelectedItem()==null) return;
        Reservation reservation = new Reservation();
        Date date = new Date(System.currentTimeMillis());
        Car car = new Car();
        car.setId(carListView.getSelectionModel().getSelectedItem().getId());
        User user = new User();
        user.setId(userId);
        reservation.setCar(car);
        reservation.setUser(user);
        reservation.setReservationDate(date);
        LocalDate ld = date.toLocalDate();
        ld = ld.plusMonths(1);
        reservation.setArrivalDate(Date.valueOf(ld));
        reservationManager.insert(reservation);
        updateList();

        new MyAlerts().showOkAlert("Uspjeh","Cestitamo na rezervaciji vozila, ocekujte ga za 30 dana!");
    }
}
