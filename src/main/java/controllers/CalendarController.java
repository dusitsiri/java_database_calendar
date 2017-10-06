package controllers;

import calender.AlertDaily;
import calender.Calendar;
import database.JdbcSQLiteConnection;
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

    JdbcSQLiteConnection loads = new JdbcSQLiteConnection();
    ObservableList<Calendar> lists = loads.loadDB();
    ObservableList<Calendar> data = FXCollections.observableArrayList();

    ArrayList<String> array = new ArrayList<>();
    @FXML TableView<Calendar> tableView;


    //choose date of event
    @FXML
    private DatePicker datePicker;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker.setOnAction((ActionEvent event) -> {
            LocalDate value = datePicker.getValue();
            array.add(String.valueOf(value));
            array.add(String.valueOf(value).substring(0, 4));
            array.add(String.valueOf(value).substring(5, 7));
            array.add(String.valueOf(value).substring(8, 10));
        });

        data = lists;
        tableView.setItems(data);
    }

    //Submit Button
    @FXML
    private TextField massage;
    @FXML
    private Label notice;

    public void handleSubmit(ActionEvent event) throws IOException {
        if (array.isEmpty() && massage.getText().isEmpty()) {
            notice.setText("Please insert your information");
        }
        else if (!array.isEmpty() && massage.getText().isEmpty()){
            notice.setText("Please add your event");
        }
        else if (array.isEmpty() && !massage.getText().isEmpty()){
            notice.setText("Please choose your date");
        }
        else{
            AlertDaily daily = new AlertDaily();
            daily.submit(array, valuemenu, massage);
            notice.setText("Your data is "+array.get(0)+" "+massage+" "+valuemenu);
            this.handleGoToCalendar(event);
            array.clear();
        }
    }

    //choose alert
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem item1;
    @FXML
    private MenuItem item2;
    @FXML
    private MenuItem item3;
    @FXML
    private String valuemenu = "";

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
        if (tableView.getSelectionModel().getSelectedItem().getDate() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + tableView.getSelectionModel().getSelectedItem().getId() + "?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK){
                loads.deleteDB(tableView.getSelectionModel().getSelectedItem().getId());
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
        if (optional.get() == ButtonType.OK){
            loads.cleartableDB();
        }
        this.handleGoToCalendar(event);
    }

    //refresh calendar
    public void handleGoToCalendar(ActionEvent event) throws IOException{
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../calendar.fxml"));
        stage.setScene(new Scene(loader.load(), 1350, 850));
        stage.show();
    }


    public void handleSearchYourEvent(ActionEvent event) throws IOException{
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../allevents.fxml"));
        stage.setScene(new Scene(loader.load(), 1350, 850));
        stage.show();
    }

}