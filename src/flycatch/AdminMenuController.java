/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class AdminMenuController implements Initializable {

    @FXML
    private ImageView leave;

    @FXML
    private ImageView reload;

    @FXML
    private Label menu;

    @FXML
    private AnchorPane slider;

    @FXML
    private Label menuBack;

    @FXML
    private Label hello;

    private int userID;

    @FXML
    private TableView<flights> tableview;
    //@FXML
    //private TableColumn<flights, Integer> id;
    @FXML
    private TableColumn<flights, String> origin;
    @FXML
    private TableColumn<flights, String> destiny;
    @FXML
    private TableColumn<flights, String> duration;
    @FXML
    private TableColumn<flights, String> date;
    @FXML
    private TableColumn<flights, Double> price;
    @FXML
    private TableColumn capacity;
    //private TableColumn<flights, Integer> capacity;

    ObservableList<flights> listM;

    flights flight = null;

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

        hello.setText("Hello " + db.getUserName(this.userID) + "!");
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //tableview
        
        //id.setCellValueFactory(new PropertyValueFactory<flights, Integer>("FlyghtsID"));
        origin.setCellValueFactory(new PropertyValueFactory<flights, String>("Origin"));
        destiny.setCellValueFactory(new PropertyValueFactory<flights, String>("Destiny"));
        duration.setCellValueFactory(new PropertyValueFactory<flights, String>("Duration"));
        date.setCellValueFactory(new PropertyValueFactory<flights, String>("Date"));
        price.setCellValueFactory(new PropertyValueFactory<flights, Double>("Price"));
        //capacity.setCellValueFactory(new PropertyValueFactory<flights, Integer>("Npassengers"));

        Callback<TableColumn<flights, String>, TableCell<flights, String>> cellFactory = (param) -> {
            final TableCell<flights, String> cell = new TableCell<flights, String>() {

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
                            flight = tableview.getSelectionModel().getSelectedItem();

                            if (flight == null) {
                                error("No row selected", "Select one row to see the details");
                            } else {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("Info.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                InfoController controller = loader.getController();
                                controller.getFlight(flight.id, flight.origin, flight.destiny, flight.duration, flight.date, flight.price, flight.capacity);
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

        listM = db.getData();

        tableview.setItems(listM);

        //Principal view
        leave.setOnMouseClicked(event -> {
            System.exit(0);
        }
        );

        slider.setTranslateX(-176);

        menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(0);
            slide.play();
            slider.setTranslateX(-176);
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(false);
                menuBack.setVisible(true);

            }
            );
        }
        );

        menuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-176);
            slide.play();
            slider.setTranslateX(0);
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(true);
                menuBack.setVisible(false);

            }
            );
        }
        );
    }

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AddFlight.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void updateTable(MouseEvent event) {
        Database db = new Database();

        listM.clear();

        listM = db.getData();

        tableview.setItems(listM);
    }

    @FXML
    private void getCancelView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("CancelFlights.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
        @FXML
    private void getUpdateView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("EditFlights.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @FXML
    private void getStatisticsView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
