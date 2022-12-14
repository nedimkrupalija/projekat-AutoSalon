package ba.etf.unsa.rpr.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class panelController {

    public Label labelUser;
    public Label dateLabel;
    public ChoiceBox picker;


    public MenuButton menuButton;


    public TextField textIme;
    public TextField textTelefon;
    public TextField textPassword;
    public TextField textPrezime;

    public Label labelId;
    public Button categoryButton;
    public Button acceptButton;

    /**
     * Event on category pick
     * @param mouseEvent
     */
    public void pickerClick(MouseEvent mouseEvent) {
        if(picker.getSelectionModel().getSelectedItem()!=null)
        categoryButton.setText("Idi na izmjenu: "+picker.getSelectionModel().getSelectedItem());
    }

    public void acceptClicked(ActionEvent actionEvent) {
    }
}
