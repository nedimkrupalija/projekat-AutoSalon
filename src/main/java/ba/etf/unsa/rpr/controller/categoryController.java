package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.CategoryDAOSQlImpl;
import ba.etf.unsa.rpr.dao.CategoryDao;
import ba.etf.unsa.rpr.domain.Category;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
                    newCategoryName.getStyleClass().add("fieldWrong");
                }
                else newCategoryName.getStyleClass().removeAll("fieldWrong");
            }
        });
        categoryID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(categoryID.getText().trim().isEmpty()){
                    categoryID.getStyleClass().add("fieldWrong");
                }
                else categoryID.getStyleClass().removeAll("fieldWrong");
            }
        });
        categoryName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(categoryName.getText().trim().isEmpty()){
                    categoryName.getStyleClass().add("fieldWrong");
                }
                else categoryName.getStyleClass().removeAll("fieldWrong");
            }
        });
    }

    /**
     * Event for inserting new category into db from application
     * @param actionEvent
     */
    public void acceptNewCategoryClick(ActionEvent actionEvent) {
        if(newCategoryName.getText().trim().isEmpty()) return;
        CategoryDao categoryDao = new CategoryDAOSQlImpl();
        if(categoryDao.getNumberWithName(newCategoryName.getText())!=0){
            System.out.println("Vec postoji ta kategorija!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ta kategorija vec postoji!");
            alert.setContentText("Molimo ispravite podatke i probajte opet!");
            alert.showAndWait();
            return;
        }
        Category category = new Category();
        category.setName(newCategoryName.getText());
        try{
            categoryDao.insert(category);
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        System.out.println("Kategorija uspjesno ubacena!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dodavanje nove kategorije");
        alert.setHeaderText(null);
        alert.setContentText("Ubacivanje uspjesno!");
        alert.showAndWait();
        if(categoryIndex==-1) categoryIndex = categoryIndex+1;
        categoryCount.setText(String.valueOf(Integer.valueOf(categoryCount.getText())+1));
    }
}
