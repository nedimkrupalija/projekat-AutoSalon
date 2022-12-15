package ba.etf.unsa.rpr.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class categoryController {

    public TextField categoryName;
    public TextField categoryID;
    public int categoryIndex ;


    public Label categoryCount;
    public TextField newCategoryName;
    public Button acceptNewCategory;

    @FXML
    public void initialize(){
        newCategoryName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(newCategoryName.getText().trim().isEmpty()){
                    newCategoryName.getStyleClass().add("wrongField");
                }
                else newCategoryName.getStyleClass().removeAll("wrongField");
            }
        });
        categoryID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(categoryID.getText().trim().isEmpty()){
                    categoryID.getStyleClass().add("wrongField");
                }
                else categoryID.getStyleClass().removeAll("wrongField");
            }
        });
    }

}
