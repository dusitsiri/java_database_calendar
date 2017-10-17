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
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
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

        tableView.setItems(HomePageController.lists);
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
    private MenuItem item1;
    @FXML
    private MenuItem item2;
    @FXML
    private MenuItem item3;
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

    @FXML
    private Button delete;

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

    @FXML
    private Button edit;
    Stage newStage = new Stage();

    //edit event
    public void handleEditEvent() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Button buttonScene2 = new Button("Click to go back to First Scene");
            Label labelScene2 = new Label("Scene 2");
            FlowPane pane2 = new FlowPane();
            buttonScene2.setOnAction(e -> ButtonClicked(e));
            pane2.setVgap(10);
            pane2.setStyle("-fx-background-color:tan;-fx-padding:10px;");
            pane2.getChildren().addAll(buttonScene2, labelScene2);
            Scene scene2 = new Scene(pane2, 400, 100);
            newStage.setScene(scene2);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Edit your event");
            newStage.show();
        }
    }

    public void ButtonClicked(ActionEvent e) {
        if (e.getSource() == edit)
            newStage.showAndWait();
        else {
            newStage.close();
        }
    }

    static ObservableList eventSearch = FXCollections.observableArrayList();
    @FXML private TextField searchTextField;
    public void handleSearchEvent(ActionEvent event) throws IOException{
        eventSearch = HomePageController.loads.searchEvent(searchTextField.getText());
        this.handleGotoSearchEvent(event);
    }

    //refresh calendar
    public void handleGoToCalendar(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../calendar.fxml"));
        stage.setScene(new Scene(loader.load(), 1300, 750));
        stage.show();
    }


    //back to home page
    public void handleHomePage(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../homepage.fxml"));
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