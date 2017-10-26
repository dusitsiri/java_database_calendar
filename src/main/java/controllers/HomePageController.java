package controllers;

import calender.Calendar;
import database.JdbcSQLiteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.GregorianCalendar;

public class HomePageController {

    static JdbcSQLiteConnection loads = new JdbcSQLiteConnection();

    public void handleButtonRecordEvents(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../calendar.fxml"));
        stage.setScene(new Scene(loader.load(), 1300, 750));
        stage.show();
    }

    //show data that you search
    public void handleButtonSearchEvent(ActionEvent event) throws IOException{
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../events.fxml"));
        stage.setScene(new Scene(loader.load(), 1300, 750));
        stage.show();
    }
}
