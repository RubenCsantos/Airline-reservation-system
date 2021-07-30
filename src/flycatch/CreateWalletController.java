/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class CreateWalletController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField number;

    @FXML
    private TextField value;

    private int userID;

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

    public void createCard(ActionEvent event) {
        Database db = new Database();
        String nameS = name.getText();
        String cn = number.getText();
        String v = number.getText();

        StringBuilder sb = new StringBuilder();

        if (nameS.equals("")) {
            sb.append("Insert your name.");
        }
        if (cn.equals("")) {
            sb.append("Inser your card number.");
        }
        if (v.equals("")) {
            sb.append("Insert the value to deposit.");
        }

        if (sb.length() > 0) {
            error("Missing Fields!", sb.toString());
            return;
        }

        int cardNumber = Integer.parseInt(number.getText());
        double depositValue = Double.parseDouble(value.getText());

        if (db.addCard(this.userID, cardNumber, depositValue) == 0) {
            Alert dg = new Alert(Alert.AlertType.CONFIRMATION);
            dg.setTitle("Success!");
            dg.setContentText("Card created with success.");
            dg.show();
        } else {
            error("Database!", "Failed to create a card.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
