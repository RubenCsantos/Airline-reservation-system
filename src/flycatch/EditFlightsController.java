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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class EditFlightsController implements Initializable {

    @FXML
    private TextField tf;

    @FXML
    private Label message;

    @FXML
    private TextField or;
    @FXML
    private TextField de;
    @FXML
    private TextField du;
    @FXML
    private DatePicker da;
    @FXML
    private TextField pr;
    @FXML
    private TextField ca;

    ObservableList<flights> listM;

    public void searchDetails(ActionEvent event) {
        int id = Integer.parseInt(tf.getText());

        Database db = new Database();

        listM = db.getFlightsDetails(id);

        or.setText(listM.get(0).origin);
        de.setText(listM.get(0).destiny);
        du.setText(listM.get(0).duration);
        //date
        pr.setText(String.valueOf(listM.get(0).price));
        ca.setText(String.valueOf(listM.get(0).capacity));
    }

    public void editedDetails(ActionEvent event) {
        int id = Integer.parseInt(tf.getText());

        Database db = new Database();

        listM = db.getFlightsDetails(id);

        String ori = or.getText();
        String dst = de.getText();
        String drt = du.getText();
        String dt = String.valueOf(da.getValue());
        double pri = Double.parseDouble(pr.getText());
        int cp = Integer.parseInt(ca.getText());

        if (db.updateFlight(id, ori, dst, drt, dt, 0, pri, cp) == 0) {
            message.setText("Flight Updated!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
