/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static javafx.scene.Cursor.cursor;

/**
 *
 * @author ruben.santos
 */
public class Database {

    private Connection con;

    public Database() {

        String url = "jdbc:sqlite:src/flycatch/FlyCatchData.db";

        try {

            con = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Add user to database    Type = 1 -> Admin ; = 0 -> NormalUser
    public int addUser(String email, String pass, int type, double budget) {

        try {

            Statement stmt = con.createStatement();

            String query = "INSERT INTO Users(Email,Password, Type, Budget) VALUES ('"
                    + email + "', '" + pass + "','" + type + "','" + budget + "');";

            stmt.execute(query);
            stmt.close();

            return 0;
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

            return 1;
        }
    }

    //Check if email already exist in database
    public int checkEmailValidation(String email) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT Email FROM Users WHERE Email = '" + email + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (!rs.next()) {
                stmt.close();
                return 0;
            } else {
                stmt.close();
                return 1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //check is password is strong
    public int checkPasswordStrength(String password) {
        if (password.length() < 6) {
            return -1;
        }
        return 0;

    }

    //get userID to pass between scennes
    public int getUserID(String email) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT UserID FROM Users WHERE Email = '" + email + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int i = rs.getInt(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return -1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    public int checkCredentials(String email, String password) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT UserID FROM Users WHERE Email = '" + email + "' AND Password = '" + password + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int i = rs.getInt(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //get User Type to know if is admin ou not
    public int getUserType(int id) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT Type FROM Users WHERE UserID = '" + id + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int i = rs.getInt(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return -1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //get User Name
    public String getUserName(int id) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT Email FROM Users WHERE UserID = '" + id + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String i = rs.getString(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return "";
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }

    //Add flights to the database
    public int addFlight(String origin, String destiny, String duration, String date, int state, double price, int capacity) {
        try {
            Statement stmt = con.createStatement();

            String query = "INSERT INTO Flyghts (Origin, Destiny, Duration, Date, State, Price, Npassengers) VALUES('" + origin + "','" + destiny + "','" + duration + "','"
                    + date + "','" + state + "','" + price + "', '" + capacity + "');";
            stmt.executeUpdate(query); //executeUpdate is better with INSERT, DELETE and UPDATE
            stmt.close();

            return 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //Add flights to the database
    public int updateFlight(int id, String origin, String destiny, String duration, String date, int state, double price, int capacity) {
        try {
            Statement stmt = con.createStatement();

            String query = "UPDATE Flyghts SET FlyghtsID ='" + id + "', Origin='" + origin + "', Destiny='" + destiny + "', Duration='" + duration + "', Date='" + date + "', Price='" + price + "', Npassengers='" + capacity + "' WHERE FlyghtsID = '" + id + "';";
            stmt.executeUpdate(query); //executeUpdate is better with INSERT, DELETE and UPDATE
            stmt.close();

            return 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //Data about flights
    public ObservableList<flights> getData() {

        ObservableList<flights> list = FXCollections.observableArrayList();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM Flyghts";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(new flights(rs.getInt("FlyghtsID"), rs.getString("Origin"), rs.getString("Destiny"), rs.getString("Duration"), rs.getString("Date"), Integer.parseInt(rs.getString("State")), Double.parseDouble(rs.getString("Price")), Integer.parseInt(rs.getString("Npassengers"))));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
        //Data about tickets
    public ObservableList<tickets> getTicketData(int id) {

        ObservableList<tickets> list = FXCollections.observableArrayList();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM TicketsOrder WHERE UserID='" +id +"';";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(new tickets(rs.getInt("TicketID"), rs.getInt("Ntickets"), rs.getDouble("TotalPrice"),rs.getInt("Paid"),rs.getInt("UserID"),rs.getInt("FlyghtsID")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    //Data about user flights
    public ObservableList<flights> getMyFlights(int id) {

        ObservableList<flights> list = FXCollections.observableArrayList();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM Flyghts WHERE FlyghtsID = '" +id +"';";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(new flights(rs.getInt("FlyghtsID"), rs.getString("Origin"), rs.getString("Destiny"), rs.getString("Duration"), rs.getString("Date"), Integer.parseInt(rs.getString("State")), Double.parseDouble(rs.getString("Price")), Integer.parseInt(rs.getString("Npassengers"))));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public int deleteFlight(int x) {
        try {
            Statement stmt = con.createStatement();

            String query = "DELETE FROM Flyghts WHERE FlyghtsID = '" + x + "';";
            stmt.executeUpdate(query); //executeUpdate is better with INSERT, DELETE and UPDATE
            stmt.close();

            return 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    public ObservableList<flights> getFlightsDetails(int id) {

        ObservableList<flights> list = FXCollections.observableArrayList();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM Flyghts WHERE FlyghtsID='" + id + "';";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(new flights(rs.getInt("FlyghtsID"), rs.getString("Origin"), rs.getString("Destiny"), rs.getString("Duration"), rs.getString("Date"), Integer.parseInt(rs.getString("State")), Double.parseDouble(rs.getString("Price")), Integer.parseInt(rs.getString("Npassengers"))));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    //get flight statistics
    public String getFlightsStatistics() {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT COUNT(FlyghtsID) FROM Flyghts";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String i = rs.getString(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return "";
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }
    
        //get my flight statistics
    public String getMyFlightsStatistics(int id) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT COUNT(UserID) FROM TicketsOrder WHERE UserID='" +id +"';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String i = rs.getString(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return "";
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }

    //get Card value
    public double getCardValue(int x) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT Value FROM BankCard WHERE UserID = '" + x + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                double i = rs.getDouble(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return 0;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    // See if user have already a bankcard
    public int haveCard(int x) {
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT CardID FROM BankCard WHERE UserID = '" + x + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (!rs.next()) {
                stmt.close();

                return 0;
            } else {
                stmt.close();
                return 1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //Add bankcard's to the database
    public int addCard(int x, int number, double value) {
        try {
            Statement stmt = con.createStatement();

            String query = "INSERT INTO BankCard (CardNumber, Value, UserID) VALUES ('" + number + "', '" + value + "','" + x + "');";
            stmt.executeUpdate(query); //executeUpdate is better with INSERT, DELETE and UPDATE
            stmt.close();

            return 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //information of bankcard
    public String[] getCard(int id) {
        try {
            Statement stmt = con.createStatement();

            String infos[] = new String[2];

            String query = "SELECT * FROM BankCard WHERE UserID = '" + id + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                String number = String.valueOf(rs.getInt("CardNumber"));
                String value = String.valueOf(rs.getDouble("Value"));

                infos[0] = number;
                infos[1] = value;

                stmt.close();
                return infos;
            } else {
                stmt.close();
                return infos;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //deposit money to bankcard
    public int deposit(int id, double quantity) {
        try {
            Statement stmt = con.createStatement();
            String query = "UPDATE BankCard SET Value = Value + '" + quantity + "' WHERE UserID= '" + id + "';";
            stmt.executeUpdate(query);
            stmt.close();

            return 0;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }
    
        //deposit money to bankcard
    public int payment(int id, double quantity) {
        try {
            Statement stmt = con.createStatement();
            String query = "UPDATE BankCard SET Value = Value - '" + quantity + "' WHERE UserID= '" + id + "';";
            stmt.executeUpdate(query);
            stmt.close();

            return 0;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //get Flight available capacity
    public int getAvailableCapacity(int id) {
        try {

            Statement stmt = con.createStatement();

            String query = "SELECT Npassengers FROM Flyghts WHERE FlyghtsID = '" + id + "';";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int i = rs.getInt(1);
                stmt.close();
                return i;
            } else {
                stmt.close();
                return -1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //Add new ticket to the database
    public int addTicket(int nTickets, double totalPrice, int paid, int userID, int flightid) {
        try {
            Statement stmt = con.createStatement();

            String query = "INSERT INTO TicketsOrder(Ntickets, TotalPrice, Paid, UserID, FlyghtsID) VALUES ('" + nTickets + "', '" + totalPrice + "', '" + paid + "', '" + userID + "', '" + flightid + "');";
            stmt.executeUpdate(query); //executeUpdate is better with INSERT, DELETE and UPDATE
            stmt.close();

            return 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //deposit money to bankcard
    public int updateQuantity(int id, double quantity) {
        try {
            Statement stmt = con.createStatement();
            String query = "UPDATE Flyghts SET Npassengers = Npassengers - '" + quantity + "' WHERE FlyghtsID= '" + id + "';";
            stmt.executeUpdate(query);
            stmt.close();

            return 0;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }
    
    
    //information of bankcard
    public int[] getMyflightsId(int id) {
        try {
            Statement stmt = con.createStatement();

            int infos[] = new int[30];

            String query = "SELECT FlyghtsID FROM TicketsOrder WHERE UserID = '" + id + "';";

            ResultSet rs = stmt.executeQuery(query);

            for(int i=0;i<30;i++){
                if(rs.next()){
                    int x = rs.getInt("FlyghtsID");
                    
                    infos[i] = x;
                }
                else{
                    stmt.close();
                    return infos;
                }
            }     
            return infos;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    
        //information about how much user paid
    public double[] getAmmount(int id) {
        try {
            Statement stmt = con.createStatement();

            double infos[] = new double[30];

            String query = "SELECT TotalPrice FROM TicketsOrder WHERE UserID = '" + id + "';";

            ResultSet rs = stmt.executeQuery(query);

            for(int i=0;i<30;i++){
                if(rs.next()){
                    double x = rs.getDouble("TotalPrice");
                    System.out.println(x);
                    infos[i] = x;
                }
                else{
                    stmt.close();
                    return infos;
                }
            }     
            return infos;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    
    public double[] getAmmountFlyCatch() {
        try {
            Statement stmt = con.createStatement();

            double infos[] = new double[30];

            String query = "SELECT TotalPrice FROM TicketsOrder;";

            ResultSet rs = stmt.executeQuery(query);

            for(int i=0;i<100;i++){
                if(rs.next()){
                    double x = rs.getDouble("TotalPrice");
                    System.out.println(x);
                    infos[i] = x;
                }
                else{
                    stmt.close();
                    return infos;
                }
            }     
            return infos;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
