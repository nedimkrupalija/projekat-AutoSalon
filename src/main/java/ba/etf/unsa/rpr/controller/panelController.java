package ba.etf.unsa.rpr.controller;

import ba.etf.unsa.rpr.dao.CategoryDAOSQlImpl;
import ba.etf.unsa.rpr.dao.CategoryDao;
import ba.etf.unsa.rpr.dao.SalesmanDao;
import ba.etf.unsa.rpr.dao.SalesmanDaoSQLImpl;
import ba.etf.unsa.rpr.domain.Category;
import ba.etf.unsa.rpr.domain.Salesman;
import com.mysql.cj.xdevapi.Table;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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
    public Button backButton;

    /**
     * Event on category pick
     * @param mouseEvent
     */
    public void pickerClick(MouseEvent mouseEvent) {
        if(picker.getSelectionModel().getSelectedItem()!=null)
        categoryButton.setText("Idi na izmjenu: "+picker.getSelectionModel().getSelectedItem());
    }


    /**
     * Listener for text fields
     * Sets css style if field is empty
     */
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

    /**
     * Action for changing data of salesman/user
     * @param actionEvent
     */
    public void acceptClicked(ActionEvent actionEvent) {
        if(textIme.getText().isEmpty()||textPrezime.getText().isEmpty()||textTelefon.getText().isEmpty()||textPassword.getText().isEmpty()){
            System.out.println("Pogresni podaci!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Pogresni podaci!");
            alert.setContentText("Molimo ispravite podatke i probajte opet!");
            alert.showAndWait();
        }
        else {
            System.out.println("Izmjena potvrdena");
            SalesmanDao salesmanDao = new SalesmanDaoSQLImpl();
            Salesman salesman = new Salesman();
            salesman.setId(Integer.parseInt(labelId.getText()));
            salesman.setName(textIme.getText());
            salesman.setSurname(textPrezime.getText());
            salesman.setNumber(textTelefon.getText());
            salesman.setPassword(textPassword.getText());
            salesmanDao.update(salesman,Integer.parseInt(labelId.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Izmjena podataka");
            alert.setHeaderText(null);
            alert.setContentText("Izmjena uspjesna!");
            alert.showAndWait();
        }

    }

    /**
     * Action for returning to main screen from admin panel
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Vracanje na pocetni ekran!");
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
        stage.setTitle("Login");
        // scene.getStylesheets().add(String.valueOf(this.getClass().getResource("/css/style.css")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Stage thisStage = (Stage) labelId.getScene().getWindow();
        thisStage.close();
    }

    /**
     * private method for setting table in category changer/updater
     * @param loader
     */
    private void setCategoryChanger(FXMLLoader loader){
        categoryController categoryController = loader.getController();
        CategoryDao categoryDao = new CategoryDAOSQlImpl();
        ArrayList<Category> categories = (ArrayList<Category>) categoryDao.getAll();
        if(categories.isEmpty()) {
            System.out.println("Kategorije prazne!");
            categoryController.categoryIndex = -1;

        }
        else{
            categoryController.categoryID.setText(String.valueOf(categories.get(0).getId()));
            categoryController.categoryName.setText(categories.get(0).getName());
            categoryController.categoryIndex=0;
        }
        categoryController.categoryCount.setText(categoryController.categoryCount.getText() + String.valueOf(categoryController.categoryIndex));

    }

    /**
     * public action for going to category/car changer/updater
     * @param actionEvent
     * @throws IOException
     */
    public void categoryButtonClick(ActionEvent actionEvent) throws IOException {
        if(picker.getSelectionModel().getSelectedItem().equals("Kategorija")){
            System.out.println("Izmjena kategorije!");
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kategorija.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);
            stage.setTitle("Kategorija");
            stage.setScene(scene);
            stage.setResizable(false);
            setCategoryChanger(loader);
            stage.showAndWait();
        }
        else if (picker.getSelectionModel().getSelectedItem().equals("Vozila")){

        }
        else return;
    }
}
