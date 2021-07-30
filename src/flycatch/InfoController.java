/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class InfoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label info;

    @FXML
    private TextField orn;

    @FXML
    private TextField dty;

    @FXML
    private TextField dtn;

    @FXML
    private TextField prc;

    @FXML
    private TextField cap;

    @FXML
    private TextField dt;


    public void getFlight(int id, String origin, String destiny, String duration, String date, double price, int capacity) {
        info.setText("FLIGHT NUMBER "+id);
        orn.setText(origin);
        dty.setText(destiny);
        dtn.setText(duration);
        dt.setText(date);
        prc.setText(String.valueOf(price));
        cap.setText(String.valueOf(capacity));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

 

}
