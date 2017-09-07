import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


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
        });
    }

    //Clear Button
    @FXML
    public void handleClearButton(ActionEvent event) {
        massage.setText("");
    }



    //Submit Button
    @FXML
    private TextField massage;
    @FXML
    private Label notice;
    @FXML
    public void handleSubmit(ActionEvent event){
        if (!array.isEmpty()){
            String date = array.get(0);
            String text = new String(massage.getText());
            array.add(text);
            JdbcSQLiteConnection add = new JdbcSQLiteConnection();
            int id = add.getCreateID();
            add.saveDB(id, date, text);
            notice.setText("Insert your data success");
            array.remove(1);
        }

    }


    //Show information Button
    @FXML
    public void handleShowData(ActionEvent event){
        Button showdata = (Button) event.getSource();
        Stage stage = (Stage) showdata.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBShow.fxml"));
        try{
            stage.setScene(new Scene(loader.load(), 600, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
