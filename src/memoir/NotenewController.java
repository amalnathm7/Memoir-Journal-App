/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoir;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class NotenewController extends HomepageController implements Initializable {

    @FXML
    private AnchorPane notepane;
    @FXML
    private Button attach;
    @FXML
    private Button redo;
    @FXML
    private Button delete;
    @FXML
    private TextArea description;
    @FXML
    private TextField title;
    @FXML
    private Button save;
    @FXML
    private Label label;
    @FXML
    private TextField category;
    
    Journal note;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if(index != -1) {
                note = noteList.get(index);
            
                label.setText(note.title);
                title.setText(note.title);
                description.setText(note.description);
                category.setText(note.emotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            index = -1;
        }
    }
    
    @FXML
    private void saveBtn (javafx.event.ActionEvent event) throws IOException {
        try {
            if(index == -1)
                RestClient.create_journal(title.getText(), description.getText(), category.getText(), null, "d", LoginController.auth);
            else{
                note.title = title.getText();
                note.description = description.getText();
                note.emotion = category.getText();
                
                RestClient.put_journal(note, LoginController.auth);
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
        label.setText("New Note");
        title.setText("");
        description.setText("");
        category.setText("");
    }

    @FXML
    private void deleteBtn(ActionEvent event) {
        if(index != -1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this note?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                RestClient.get_or_delete_journal(note.id, "DELETE", LoginController.auth);
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
