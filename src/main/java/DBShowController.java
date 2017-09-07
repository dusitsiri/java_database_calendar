import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DBShowController implements Initializable{

    @FXML TableView<DBShow> tableView;

    JdbcSQLiteConnection loads = new JdbcSQLiteConnection();
    ObservableList<DBShow> lists = loads.loadDB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<DBShow> data = FXCollections.observableArrayList();
        data = lists;
        tableView.setItems(data);

    }

    @FXML
    public void back(ActionEvent event){
        Button back = (Button) event.getSource();
        Stage stage = (Stage) back.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
        try{
            stage.setScene(new Scene(loader.load(), 600, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private TextField deleteID;

    @FXML
    public void deleteData(ActionEvent event){
       if (deleteID.getText().length() > 0){
           int del = Integer.parseInt(deleteID.getText());
           loads.deleteDB(del);
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
}
