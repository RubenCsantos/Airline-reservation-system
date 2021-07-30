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
 *
 * @author ruben.santos
 */
public class FXMLDocumentController implements Initializable {

    //FXML VARIABLES
    @FXML
    private Label signUpHere;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button signIn;

    private void error(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO!");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    @FXML
    private void goToRegister(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent register = loader.load();
        RegisterController controller = loader.getController();
        Scene registerScene = new Scene(register);
        Stage registerWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        registerWindow.setScene(registerScene);
        registerWindow.show();
    }

    @FXML
    public void loginUser(ActionEvent event) throws IOException {
        Database db = new Database();

        StringBuilder sb = new StringBuilder();
        String email = emailField.getText();
        String password = passField.getText();

        if (email.equals("")) {
            sb.append("Insert your email.");
        }
        if (password.equals("")) {
            sb.append("Insert your password");
        }
        if (sb.length() > 0) {
            error("Missing fields!", sb.toString());
        }

        int userID = db.checkCredentials(email, password);

        if (userID != -1 && userID != 1) {
            goToMainMenu(event, userID);
        } else if (userID == 1) {
            error("Wrong Credentials!", "Your email or password must be wrong.");
        } else if (userID == -1) {
            error("Database!", "Database connection failled");
        }
    }

    @FXML
    private void goToMainMenu(ActionEvent event, int userID) throws IOException {
        Database db = new Database();

        if (db.getUserType(userID) == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent mainMenu = loader.load();
            MainMenuController controller = loader.getController();
            controller.getUser(userID);
            Scene mainMenuScene = new Scene(mainMenu);
            Stage mainMenuWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainMenuWindow.setScene(mainMenuScene);
            mainMenuWindow.show();
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMenu.fxml"));
            Parent AdminMenu = loader.load();
            AdminMenuController controller = loader.getController();
            controller.getUser(userID);
            Scene mainMenuScene = new Scene(AdminMenu);
            Stage AdminMenuWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            AdminMenuWindow.setScene(mainMenuScene);
            AdminMenuWindow.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
