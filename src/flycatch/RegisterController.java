/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ruben.santos
 */
public class RegisterController implements Initializable {

    //FXML VARIABLES
    @FXML
    private Label signInHere;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField PassField;
    @FXML
    private Button flyWithUs;

    @FXML
    private void goToLogin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent login = loader.load();
        FXMLDocumentController controller = loader.getController();
        Scene loginScene = new Scene(login);
        Stage loginWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginWindow.setScene(loginScene);
        loginWindow.show();
    }

    private void error(String header, String content) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO!");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    @FXML
    public void registeUser(ActionEvent event) throws IOException {
        StringBuilder sb = new StringBuilder();

        String email = emailField.getText();
        String password = PassField.getText();

        if (email.equals("")) {
            sb.append("Insert your email to complete register.");
        }
        if (password.equals("")) {
            sb.append("Insert one strong password to complete register.");
        }
        if (sb.length() > 0) {
            error("Missing fields.", sb.toString());
            return;
        }

        Database db = new Database();

        switch (db.checkEmailValidation(email)) {

            case 1:
                error("Email already used!", "Insert another one.");
                return;
            case -1:
                error("Connection faill", "Faill acessing database");
                return;
        }

        switch (db.checkPasswordStrength(password)) {

            case -1:
                error("Bad password", "Password must have more than 6 characters");
                return;
        }
        
        if(db.addUser(email, password, 0, 0) == 0)
            goToMainMenu(event, db.getUserID(email));
        else
            error("Database!" , "Failed to add user."); 
    }
    
    @FXML
    private void goToMainMenu(ActionEvent event, int userID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent mainMenu = loader.load();
        MainMenuController controller = loader.getController();
        Scene mainMenuScene = new Scene(mainMenu);
        Stage mainMenuWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainMenuWindow.setScene(mainMenuScene);
        mainMenuWindow.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
