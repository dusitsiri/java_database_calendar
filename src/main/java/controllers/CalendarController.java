package controllers;

import calender.AlertDaily;
import calender.Calendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class CalendarController implements Initializable {

    ArrayList<String> array = new ArrayList<>();
    @FXML
    TableView<Calendar> tableView;
    @FXML
    private TableColumn id,date,events;

    private IntegerProperty index = new SimpleIntegerProperty();
    //choose date of event
    @FXML
    private DatePicker datePicker;

    ObservableList<Calendar> lists = HomePageController.loads.loadDB();
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        datePicker.setOnAction((ActionEvent event) -> {
            LocalDate value = datePicker.getValue();
            array.add(String.valueOf(value));
            array.add(String.valueOf(value).substring(0, 4));
            array.add(String.valueOf(value).substring(5, 7));
            array.add(String.valueOf(value).substring(8, 10));
        });
        tableView.setItems(lists);


    }


    @FXML
    private TextField massage;
    //Submit Button
    public void handleSubmit(ActionEvent event) throws IOException {
        if (!array.isEmpty() && !massage.getText().isEmpty() && !valuemenu.equals("")) {
            AlertDaily daily = new AlertDaily();
            daily.submit(array, valuemenu, massage);
            this.handleGoToCalendar(event);
            array.clear();
        }
    }


    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem item1,item2,item3;
    @FXML
    private String valuemenu = "";

    //choose alert
    public void menuList(ActionEvent event) {
        if (event.getTarget().equals(item1)) {
            valuemenu = item1.getText();
            menuButton.setText(valuemenu);
        } else if (event.getTarget().equals(item2)) {
            valuemenu = item2.getText();
            menuButton.setText(valuemenu);
        } else {
            valuemenu = item3.getText();
            menuButton.setText(valuemenu);
        }
    }


    //delete event
    public void deleteData(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem().getDate() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete event on " + tableView.getSelectionModel().getSelectedItem().getDate() + "?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK) {
                HomePageController.loads.deleteDB(tableView.getSelectionModel().getSelectedItem().getId());
                this.handleGoToCalendar(event);
            }
        }
    }

    //clear event
    @FXML
    private void handleClearTable(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete all events?",
                ButtonType.OK, ButtonType.CANCEL);
        Optional optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            HomePageController.loads.cleartableDB();
        }
        this.handleGoToCalendar(event);
    }



    //edit event
    public void handleEditEvent() {

    }

    @FXML private TextField searchTextField;
    static ObservableList eventSearch = FXCollections.observableArrayList();
    public void handleSearchEvent(ActionEvent event) throws IOException{
        eventSearch = HomePageController.loads.searchEvent(searchTextField.getText());
        this.handleGotoSearchEvent(event);
    }

    //back to home page
    public void handleHomePage(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../homepage.fxml"));
        stage.setScene(new Scene(loader.load(), 1300, 750));
        stage.show();
    }

    //refresh calendar
    public void handleGoToCalendar(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../calendar.fxml"));
        stage.setScene(new Scene(loader.load(), 1300, 750));
        stage.show();
    }

    public void handleGotoSearchEvent(ActionEvent event) throws IOException{
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../events.fxml"));
        stage.setScene(new Scene(loader.load(), 1300, 750));
        stage.show();
    }

}