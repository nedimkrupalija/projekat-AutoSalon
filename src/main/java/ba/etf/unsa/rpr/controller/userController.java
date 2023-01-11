package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.domain.Car;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class userController {

    public ListView<Car> carListView;
    public Button showReservations;
    public Button reservationButton;
    public Button backButton;
    public TextArea textArea;

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

    public void showResClick(ActionEvent actionEvent) {
    }

    public void reservateButtonClick(ActionEvent actionEvent) {

    }
}
