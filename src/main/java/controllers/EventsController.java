package controllers;

import calender.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class EventsController {


    @FXML private TableView tableViewEvents;

    public void initialize(){
        tableViewEvents.setItems(CalendarController.eventSearch);
    }
    public void handleButtonBack(ActionEvent event) throws IOException{
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../calendar.fxml"));
        stage.setScene(new Scene(loader.load(), 1300, 750));
        stage.show();
    }
}
