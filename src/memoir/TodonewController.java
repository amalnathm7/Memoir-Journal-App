/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoir;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author amaln
 */
public class TodonewController extends HomepageController implements Initializable {

    @FXML
    private AnchorPane journalpane;
    @FXML
    private Button attach;
    @FXML
    private Button redo;
    @FXML
    private Button delete;
    @FXML
    private DatePicker enddate;
    @FXML
    private TextArea description;
    @FXML
    private TextField title;
    @FXML
    private Button save;
    @FXML
    private Label label;
    
    Todo todo;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if(index != -1) {
                todo = todoList.get(index);
            
                label.setText(todo.title);
                title.setText(todo.title);
                description.setText(todo.description);
                
                String[] seq = todo.end_date.split(" "); 
                
                enddate.setValue(LocalDate.parse(seq[0]));
            }
        } catch(Exception e) {
            e.printStackTrace();
            index = -1;
        }
    }    
    
    @FXML
    private void saveBtn (javafx.event.ActionEvent event) throws IOException {
        try {
            if(index == -1)
                RestClient.create_todo(title.getText(), description.getText(), enddate.getValue().toString(), LoginController.auth);
            else{
                todo.title = title.getText();
                todo.description = description.getText();
                todo.end_date = enddate.getValue().toString();
                
                RestClient.put_todo(todo, LoginController.auth);
            }
            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();
            index = -1;
        } catch (Exception e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
    }

    @FXML
    private void addImg(ActionEvent event) {
    }

    @FXML
    private void redoBtn(ActionEvent event) {
        label.setText("New To-do");
        title.setText("");
        description.setText("");
        enddate.setValue(null);
    }

    @FXML
    private void deleteBtn(ActionEvent event) {
        if(index != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this note?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            
            if (alert.getResult() == ButtonType.YES) {
                RestClient.get_or_delete_todo(todo.id, "DELETE", LoginController.auth);
                alert.close();
                index = -1;
                Stage stage = (Stage) delete.getScene().getWindow();
                stage.close();
            }
            else {
                alert.close();
            }
        }
    }
}
