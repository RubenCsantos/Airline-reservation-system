/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class MyFlightsController implements Initializable {

    @FXML
    private TableView<tickets> tableview;
    //@FXML
    //private TableColumn<flights, Integer> id;
    @FXML
    private TableColumn<tickets, Integer> id;
    @FXML
    private TableColumn<tickets, Integer> ntickets;
    @FXML
    private TableColumn<tickets, Integer> userid;
    @FXML
    private TableColumn<tickets, Integer> flightsid;
    @FXML
    private TableColumn capacity;
    //private TableColumn<flights, Integer> capacity;

    ObservableList<tickets> listM;

    tickets ticket = null;

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
    }

    public void tableAppear(ActionEvent event) {
        //tableview
        //id.setCellValueFactory(new PropertyValueFactory<flights, Integer>("FlyghtsID"));
        //id.setCellValueFactory(new PropertyValueFactory<tickets, Integer>("TicketID"));
        ntickets.setCellValueFactory(new PropertyValueFactory<tickets, Integer>("Ntickets"));
        //flightsid.setCellValueFactory(new PropertyValueFactory<tickets, Integer>("FlyghtsID"));
        //date.setCellValueFactory(new PropertyValueFactory<flights, String>("Date"));
        //price.setCellValueFactory(new PropertyValueFactory<flights, Double>("Price"));
        //capacity.setCellValueFactory(new PropertyValueFactory<flights, Integer>("Npassengers"));

        Callback<TableColumn<tickets, String>, TableCell<tickets, String>> cellFactory = (param) -> {
            final TableCell<tickets, String> cell = new TableCell<tickets, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button moreButton = new Button("Info");
                        moreButton.setStyle("-fx-background-color: #5581b4; -fx-font-weight: bold");
                        moreButton.setOnAction((ActionEvent event) -> {

                            //tableview.getItems().removeAll(tableview.getSelectionModel().getSelectedItem());
                            ticket = tableview.getSelectionModel().getSelectedItem();

                            if (ticket == null) {
                                error("No row selected", "Select one row to see the details");
                            } else {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("MyFlightsInfo.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                MyFlightsInfoController controller = loader.getController();
                                controller.getFlight(ticket.id,ticket.flightid);
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            }

                        });
                        setGraphic(moreButton);
                        setText(null);
                    }
                }
            };

            cell.setAlignment(Pos.CENTER);
            return cell;
        };

        capacity.setCellFactory(cellFactory);

        Database db = new Database();

       listM = db.getTicketData(this.userID);

        tableview.setItems(listM);

    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
