package ba.etf.unsa.rpr.controller.alert;


public class MyAlerts {
    public MyAlerts(){};
    /**
     * Methods for showing alerts
     * @param e - exception
     * @param string - message
     */
    public void showWrongAlert(Exception e)
    {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Greska!");
        alert.setHeaderText(e.getMessage());
        alert.setContentText("Ispravite podatke i pokusajte opet!");
        alert.showAndWait();
    }

    public void showWrongAlert( String message)
    {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Greska!");
        alert.setHeaderText(message);
        alert.setContentText("Ispravite podatke i pokusajte opet!");
        alert.showAndWait();
    }



    public void showOkAlert(String title, String message){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
