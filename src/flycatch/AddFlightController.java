/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class AddFlightController implements Initializable {

    @FXML
    private TextField origin;

    @FXML
    private TextField destiny;

    @FXML
    private TextField duration;

    @FXML
    private TextField price;

    @FXML
    private TextField capacity;

    @FXML
    private DatePicker date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void error(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO!");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    public void addFlightToDatabase(ActionEvent event) {
        Database db = new Database();

        StringBuilder sb = new StringBuilder();

        String from = origin.getText();
        String to = destiny.getText();
        String time = duration.getText();
        String cost = price.getText();
        String people = capacity.getText();
        String when = String.valueOf(date.getValue());

        if (from.equals("")) {
            sb.append("Insert the origin of the flight.");
        }

        if (to.equals("")) {
            sb.append("Insert the destiny of the flight.");
        }

        if (time.equals("")) {
            sb.append("Insert the duration of the flight.");
        }

        if (when.equals("")) {
            sb.append("Insert the date of the flight.");
        }

        if (cost.equals("")) {
            sb.append("Insert the price of the flight.");
        }

        if (people.equals("")) {
            sb.append("Insert the capacity of the airplane.");
        }

        if (sb.length() > 0) {
            error("Missing fields!", sb.toString());
        }

        double costX = Double.parseDouble(cost);
        int peopleIn = Integer.parseInt(people);

        if (db.addFlight(from, to, time, when, 0, costX, peopleIn) == 0) {
            Alert dg = new Alert(Alert.AlertType.CONFIRMATION);
            dg.setTitle("Success!");
            dg.setContentText("Flight add with success.");
            dg.show();

            origin.clear();
            destiny.clear();
            duration.clear();
            price.clear();
            capacity.clear();
            date.getEditor().clear();

        } else {
            error("Database!", "Failed to add flight.");
        }
    }

    public void cancelAdd(ActionEvent event) {
        origin.clear();
        destiny.clear();
        duration.clear();
        price.clear();
        capacity.clear();
        date.getEditor().clear();
    }

}
