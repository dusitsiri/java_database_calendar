package database; /**
 * Created by 708 on 9/1/2017.
 */

import calender.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JdbcSQLiteConnection {


    public ObservableList loadDB(){
        ObservableList<Calendar> data = FXCollections.observableArrayList();
        try{
            //setup
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:bookstore.db";
            Connection conn = DriverManager.getConnection(dbURL);
            if(conn != null){
                System.out.println("Connected to the database....");
                // display database information
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                //execute SQL statements
                System.out.println("----- Data in Book table -----");
                String query = "select * from event";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    String date = resultSet.getString(2);
                    String events = resultSet.getString(3);
                    data.add(new Calendar(id, date, events));
                }
                //close connection
                conn.close();
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return data;
    }

    public void saveDB(int id, String dateString, String textEvent){
        try{
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:bookstore.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null){
                String query = "insert into event(id, date, events) values (\'" +id+ "\', \'" +dateString+ "\', \'" +textEvent+ "')";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editDB(int id, String dateString, String textEvent){
        try{
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:bookstore.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null){
                String query = "update event set date=\'" +dateString+ "\' ,events=\'" +textEvent+ "\' where id == \'" +id+ "\'";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDB(int id){
        try{
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:bookstore.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null){
                String query = "Delete from event where id == \'" +id+ "\'";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleartableDB(){
        try{
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:bookstore.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null){
                String query = "DELETE FROM event";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCreateID(){
        int minID = 0;
        try{
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:bookstore.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null){
                String query = "Select max(id) from event";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                minID = resultSet.getInt(1);
                connection.close();
                return  minID+1;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return minID;
    }

}
