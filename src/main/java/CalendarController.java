import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;


public class CalendarController implements Initializable {
    ArrayList<String> array = new ArrayList<>();

    //choose date
    @FXML
    private DatePicker datePicker;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate t) {
                if (t != null) {
                    return formatter.format(t);
                }
                return null;
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.trim().isEmpty()) {
                    return LocalDate.parse(string, formatter);
                }
                return null;
            }
        });

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

    @FXML
    public void handleSubmit(ActionEvent event) {
        if (array.isEmpty()) {
            notice.setText("Please insert your information");
        } else {
            AlertDaily daily = new AlertDaily();
            daily.submit(array, valuemenu, massage);
            Button showdata = (Button) event.getSource();
            Stage stage = (Stage) showdata.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
            try {
                stage.setScene(new Scene(loader.load(), 900, 700));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            array.clear();
        }
    }

    //MenuButton
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

    @FXML
    TableView<DBShow> tableView;
    JdbcSQLiteConnection loads = new JdbcSQLiteConnection();
    ObservableList<DBShow> lists = loads.loadDB();
    ObservableList<DBShow> data = FXCollections.observableArrayList();
    @FXML
    private TextField deleteID;

    @FXML
    public void deleteData(ActionEvent event) {
        if (deleteID.getText().contains(",") || deleteID.getText().contains("-")) {
            String[] newdeleteId = new String[0];
            if (deleteID.getText().contains(",")) {
                newdeleteId = deleteID.getText().split(",");
                for (String i : newdeleteId) {
                    int del = Integer.parseInt(i);
                    loads.deleteDB(del);
                }
                Button showdata = (Button) event.getSource();
                Stage stage = (Stage) showdata.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
                try {
                    stage.setScene(new Scene(loader.load(), 900, 700));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            else if (deleteID.getText().contains("-")){
//                newdeleteId = deleteID.getText().split("-");
//                for (int i = 0; i < Integer.parseInt(newdeleteId[newdeleteId.length-1]); i++){
//                   loads.deleteDB(Integer.parseInt(newdeleteId[i]));
//                }
//            }
        } else if (deleteID.getText().length() == 1) {
            int del = Integer.parseInt(deleteID.getText());
            loads.deleteDB(del);
            Button showdata = (Button) event.getSource();
            Stage stage = (Stage) showdata.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
            try {
                stage.setScene(new Scene(loader.load(), 900, 700));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //clear table
    @FXML
    private void cleartable(ActionEvent event) {
        loads.cleartableDB();
        Button showdata = (Button) event.getSource();
        Stage stage = (Stage) showdata.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 900, 700));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}