/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class BuyTicketController implements Initializable {

    @FXML
    private Label idLabel;
    @FXML
    private Label labelN;
    @FXML
    private Label message;
    @FXML
    private Label origin;
    @FXML
    private Label destiny;
    @FXML
    private Label duration;
    @FXML
    private Label date;
    @FXML
    private Label price;
    @FXML
    private Label capacity;

    @FXML
    private TextField id;
    @FXML
    private TextField n;

    @FXML
    private Button searchButton;
    @FXML
    private Button orderButton;

    private int userID;

    ObservableList<flights> listM;

    public void getUser(int userId) {
        this.userID = userId;

        Database db = new Database();
    }

    private void error(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO!");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    public void showDetails(ActionEvent event) {
        int idFlight = Integer.parseInt(id.getText());

        Database db = new Database();

        listM = db.getFlightsDetails(idFlight);

        origin.setText(listM.get(0).origin);
        destiny.setText(listM.get(0).destiny);
        duration.setText(listM.get(0).duration);
        date.setText(listM.get(0).date);
        price.setText(String.valueOf(listM.get(0).price) + "$");
        capacity.setText(String.valueOf(listM.get(0).capacity));

        labelN.setVisible(true);
        n.setVisible(true);
        orderButton.setVisible(true);
    }

    public void orderTickets(ActionEvent event) {
        Database db = new Database();
        int quantity = Integer.parseInt(n.getText());
        int idFlight = Integer.parseInt(id.getText());
        int available = db.getAvailableCapacity(idFlight);
        double money = db.getCardValue(this.userID);
        double price = listM.get(0).price;

        if (quantity > available) {
            error("Capacity unvailable.", "Just " + available + " tickets available.");
        } else {
            if (money < price * quantity) {
                double needed = (price * quantity) - money;
                error("Insufficient Money.", "You need to deposit at least " + needed + "$");
            } else {
                if (db.addTicket(quantity, (price * quantity), 1, this.userID, idFlight) == 0) {
                    if (db.updateQuantity(idFlight, quantity) == 0) {
                        
                        db.payment(this.userID, (price * quantity));
                        
                        Alert dg = new Alert(Alert.AlertType.CONFIRMATION);
                        dg.setTitle("Success!");
                        dg.setContentText("Card created with success.");
                        dg.show();
                        
                    }
                }
                else{
                    error("Database failled", "Error ordering a ticket");
                }
                
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
