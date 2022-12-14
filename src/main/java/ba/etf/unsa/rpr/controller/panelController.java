package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.SalesmanDao;
import ba.etf.unsa.rpr.dao.SalesmanDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Salesman;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML
    public void initialize(){
        textIme.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(textIme.getText().trim().isEmpty()){
                    textIme.getStyleClass().add("fieldWrong");
                }
                else textIme.getStyleClass().removeAll("fieldWrong");
            }
        });
        textPrezime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(textPrezime.getText().trim().isEmpty()){
                    textPrezime.getStyleClass().add("fieldWrong");
                }
                else textPrezime.getStyleClass().removeAll("fieldWrong");
            }
        });
        textTelefon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(textTelefon.getText().trim().isEmpty()){
                    textTelefon.getStyleClass().add("fieldWrong");
                }
                else textTelefon.getStyleClass().removeAll("fieldWrong");
            }
        });

        textPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(textPassword.getText().trim().isEmpty()){
                    textPassword.getStyleClass().add("fieldWrong");
                }
                else textPassword.getStyleClass().removeAll("fieldWrong");
            }
        });

    }

    /**
     * Setting changed data for user
     * @param salesman
     */
    private void setData(Salesman salesman)
    {
        textIme.setText(salesman.getName());
        textPrezime.setText(salesman.getName());
        textPassword.setText(salesman.getPassword());
        textTelefon.setText(salesman.getNumber());
    }




}
