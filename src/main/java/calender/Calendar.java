package calender;

public class Calendar{

    private int id = 0;
    private String date = "";
    private String events = "";


    public Calendar(int id, String date, String events) {
        this.id = id;
        this.date = date;
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String event) {
        this.events = events;
    }
}
