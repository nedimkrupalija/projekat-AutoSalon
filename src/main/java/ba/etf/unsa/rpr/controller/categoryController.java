package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.CarDao;
import ba.etf.unsa.rpr.dao.CarDaoSQLImpl;
import ba.etf.unsa.rpr.dao.CategoryDAOSQlImpl;
import ba.etf.unsa.rpr.dao.CategoryDao;
import ba.etf.unsa.rpr.domain.Category;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class categoryController {


    public ArrayList<Category> categories;
    public TextField categoryName;
    public TextField categoryID;
    public int categoryIndex ;


    public Label categoryCount;
    public TextField newCategoryName;
    public Button acceptNewCategory;
    public Button changeCategory;

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
        categoryCount.setText(String.valueOf(Integer.parseInt(categoryCount.getText())+1));
    }

    /**
     * Action for deleting category through app
     * @param actionEvent
     */
    public void deleteButtonClick(ActionEvent actionEvent) {
            boolean idCheck = false, nameCheck = false;
            for(Category x : categories){
            if(x.getId()==Integer.parseInt(categoryID.getText())) idCheck=true;
            if(x.getName().equals(categoryName.getText())) nameCheck = true;
             }
            if(categoryID.getText().trim().isEmpty()||categoryName.getText().trim().isEmpty()||!idCheck||!nameCheck) {
                System.out.println("Polja za brisanje prazna!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Upisite ispravnu kategoriju za brisanje (ili izaberite putem slj/preth)!");
                alert.setContentText("Molimo ispravite podatke i probajte opet!");
                alert.showAndWait();
                return;
            }

            CarDao carDao = new CarDaoSQLImpl();
            if(carDao.countCategories(Integer.parseInt(categoryID.getText()))!=0){
                System.out.println("Postoje auta s tom kategorijom!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Zabranjeno brisati kategoriju koja je dodjeljena nekom autu!");
                alert.setContentText("Molimo ispravite podatke i probajte opet!");
                alert.showAndWait();
                return;
            }
            CategoryDao categoryDao = new CategoryDAOSQlImpl();
            categoryDao.delete(Integer.parseInt(categoryID.getText()));
            categories = (ArrayList<Category>) categoryDao.getAll();
            categoryCount.setText(String.valueOf(categories.size()));
            if(categories.size()==0){
                categoryID.setText("");
                categoryName.setText("");
            }
            else if(categoryIndex==categories.size()){
                categoryID.setText(String.valueOf(categories.get(categoryIndex).getId()));
                categoryName.setText(categories.get(categoryIndex).getName());
            }
        System.out.println("Kategorija uspjesno izbrisana!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Izbacivanje kategorije");
        alert.setHeaderText(null);
        alert.setContentText("Brisanje uspjesno!");
        alert.showAndWait();
    }

    public void changeCategoryClick(ActionEvent actionEvent) {
        boolean idCheck = false;
        for(Category x: categories){
            if(x.getId()==Integer.parseInt(categoryID.getText())) {
                idCheck=true;
            }
        }
        if(!idCheck){
            System.out.println("Nepostojeca kategorija");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Kategorija ne postoji!");
            alert.setContentText("Molimo unesite ispravan id i pokusajte opet!");
            alert.showAndWait();
            return;
        }
        CategoryDao categoryDao = new CategoryDAOSQlImpl();
        Category category = categoryDao.getById(Integer.parseInt(categoryID.getText()));
        category.setName(categoryName.getText());
        try {
            categoryDao.update(category, Integer.parseInt(categoryID.getText()));
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Izmjena kategorije");
        alert.setHeaderText(null);
        alert.setContentText("Izmjena uspjesna!");
        alert.showAndWait();

    }
}
