import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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


}
