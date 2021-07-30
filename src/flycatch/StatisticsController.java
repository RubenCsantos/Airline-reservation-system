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
public class StatisticsController implements Initializable {

    @FXML
    private Label nFlights;

    @FXML
    private Label ammount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database db = new Database();

        String total = db.getFlightsStatistics();
        
        

        nFlights.setText(total);
        
        double total2 = 0;

        double y[] = new double[30];
        y = db.getAmmountFlyCatch();
        System.out.println(y[0]);
        System.out.println(y[1]);
        for (int i = 0; i < 30; i++) {
            total2 = total2 + y[i];
        }
        System.out.println(total);
        String ammountX = String.valueOf(total2);
        ammount.setText(ammountX);
    }

}
