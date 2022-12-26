package ba.etf.unsa.rpr.dao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {


    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
       Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
       stage.setTitle("Pocetni zaslon");
       // scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/css/style.css")));
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}