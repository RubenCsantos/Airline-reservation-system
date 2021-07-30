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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class WalletController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private Label value;

    @FXML
    private TextField deposit;

    private int userID;

    private void error(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO!");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    public void getUser(int userId) {
        this.userID = userId;

        Database db = new Database();

        name.setText(db.getUserName(this.userID));

        String infos[] = db.getCard(this.userID);

        number.setText(infos[0]);
        value.setText(infos[1]);
    }

    public void addAmmount(ActionEvent event) {
        Database db = new Database();

        String quantidade = deposit.getText();
        double quantity = Double.parseDouble(quantidade);

        StringBuilder sb = new StringBuilder();

        if (quantidade.equals("")) {
            sb.append("Insert the deposit ammount.");
        }

        if (db.deposit(this.userID, quantity) == 0) {
            Alert dg = new Alert(Alert.AlertType.CONFIRMATION);
            dg.setTitle("Success!");
            dg.setContentText("Ammount deposit with success.");
            dg.show();
        } else {
            error("Field missing", sb.toString());
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database db = new Database();

    }

}
