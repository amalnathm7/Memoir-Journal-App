/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoir;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author amaln
 */
public class LoginController implements Initializable {
    
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Button signup;
    @FXML
    private Button forgotpassword;
    @FXML
    private BorderPane border;
    @FXML
    private AnchorPane mainpane;
    @FXML
    private VBox vbox;
    @FXML
    private Label label;
    @FXML
    private TextField username;
    
    static User user;
    static String auth;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RestClient.check_server_time();
    }

    @FXML
    private void signUp(javafx.event.ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        
        signup.getScene().setRoot(parent);
    }
    
    @FXML
    private void logIn(javafx.event.ActionEvent event) throws IOException {
        try {
            auth = RestClient.login(username.getText(), password.getText());
            
            user = RestClient.me(auth);
            
            Parent parent = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            
            Scene scene = new Scene(parent, login.getScene().getWidth(), login.getScene().getHeight());
        
            Stage stage = (Stage) login.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
    }
}