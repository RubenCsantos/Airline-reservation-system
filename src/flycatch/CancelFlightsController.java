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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class CancelFlightsController implements Initializable {


    @FXML
    private TextField tf;
    
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
    
    ObservableList<flights> listM;
    
    public void showDetails(ActionEvent event){
        int id = Integer.parseInt(tf.getText());
        
        Database db = new Database();
        
        listM = db.getFlightsDetails(id);
        
        origin.setText(listM.get(0).origin);
        destiny.setText(listM.get(0).destiny);
        duration.setText(listM.get(0).duration);
        date.setText(listM.get(0).date);
        price.setText(String.valueOf(listM.get(0).price));
        capacity.setText(String.valueOf(listM.get(0).capacity));
        
    }
    
    
    public void deleteFlight(ActionEvent event){
        int id = Integer.parseInt(tf.getText());
        
        Database db = new Database();
        
        listM = db.getFlightsDetails(id);
        
        origin.setText(listM.get(0).origin);
        destiny.setText(listM.get(0).destiny);
        duration.setText(listM.get(0).duration);
        date.setText(listM.get(0).date);
        price.setText(String.valueOf(listM.get(0).price));
        capacity.setText(String.valueOf(listM.get(0).capacity));
        
        if(db.deleteFlight(id)==0){
                message.setText("Flight canceled with success.");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //int id = Integer.parseInt(tf.getText());
        
        //Database db = new Database();
        
        //listM = db.getFlightsDetails(id);
        
        //origin.setText(listM.get(0).origin);
    }    
    
}
