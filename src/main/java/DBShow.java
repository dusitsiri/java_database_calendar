import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Vector;

public class DBShow {

    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleStringProperty events = new SimpleStringProperty("");

    public DBShow(int id, String date, String events) {
        setId(id);
        setDate(date);
        setEvents(events);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getEvents() {
        return events.get();
    }

    public void setEvents(String events) {
        this.events.set(events);
    }

    int[][] month = new int[][]{{31}, {29, 28}, {31}, {30}, {31}, {30}, {31}, {31}, {30}, {31}, {30}, {31}};
    JdbcSQLiteConnection add = new JdbcSQLiteConnection();

    public void submit(ArrayList array, String valuechoice, Label notice, TextField massage) {

        if (!array.isEmpty() && valuechoice.equals("")) {
            String date = (String) array.get(0);
            String text = new String(massage.getText());
            array.add(text);
            int id = add.getCreateID();
            System.out.println(array.get(0));
            add.saveDB(id, date, text);
            notice.setText("Insert your data success");


        } else if (valuechoice.equals("Daily")) {

        } else if (valuechoice.equals("Week Daily")) {
            int day = Integer.parseInt((String) array.get(3)); //day
            int Month = Integer.parseInt((String) array.get(2)); //month
            int year = Integer.parseInt((String) array.get(1)); // year
            int countmonth;
            for (int i = Month; i <= 12; i++) { //count month
                if (i != 2) {
                    countmonth = month[i - 1][0];
                } else {
                    if ((year % 4 == 0 && (!(year % 100 == 0))) || year % 400 == 0) {
                        countmonth = month[1][0];
                    } else {
                        countmonth = month[1][1];
                    }
                }
                for (int j = day; j <= countmonth; j+=7) {  //week
                    if (j + 7 > countmonth){
                        day = j + 7 - countmonth;
                        String date = array.get(1) + "-" + i + "-" + j;
                        int id = add.getCreateID();
                        String text = new String(massage.getText());
                        add.saveDB(id, date, text);
                        break;
                    }
                    else if (j + 7 < countmonth){
                        String date = array.get(1) + "-" + i + "-" + j;
                        int id = add.getCreateID();
                        String text = new String(massage.getText());
                        add.saveDB(id, date, text);
                    }
                }
                notice.setText("Insert your event week daily success");
            }
        } else if (valuechoice.equals("Month Daily")) {

        }
    }
}
