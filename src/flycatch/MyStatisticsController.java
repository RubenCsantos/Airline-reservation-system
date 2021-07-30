/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class MyStatisticsController implements Initializable {

    private int userID;
    
    @FXML
    private Label nFlights;

    @FXML
    private Label ammount;

    public void getUser(int userId) {
        this.userID = userId;

        Database db = new Database();
        
        String x = db.getMyFlightsStatistics(this.userID);

        nFlights.setText(x);

        double total = 0;

        double y[] = new double[30];
        y = db.getAmmount(this.userID);
        for (int i = 0; i < 30; i++) {
            total = total + y[i];
        }
        String ammountX = String.valueOf(total);
        ammount.setText(ammountX);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
