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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class MyFlightsInfoController implements Initializable {

    @FXML
    private TextField tf;

    @FXML
    private TextField tf2;

    public void getFlight(int id, int idf) {
        tf.setText(String.valueOf(id));
        tf2.setText(String.valueOf(idf));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
