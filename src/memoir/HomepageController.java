/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoir;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
/**
 * FXML Controller class
 *
 * @author amaln
 */
public class HomepageController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private Button journal;
    @FXML
    private Button note;
    @FXML
    private Label label;
    @FXML
    private AnchorPane mainpane;
    @FXML
    private Button logout;
    @FXML
    private AnchorPane notepane;
    @FXML
    private AnchorPane journalpane;
    @FXML
    private AnchorPane homepane;
    @FXML
    private AnchorPane todopane;
    @FXML
    private Button todo;
    @FXML
    private Button addTodoBtn;
    @FXML
    private Button addNoteBtn;
    @FXML
    private Button addJournalBtn;
    @FXML
    private ImageView image;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private ListView<String> todolist;
    @FXML
    private ListView<String> notelist;
    @FXML
    private ListView<String> journallist;
    @FXML
    private Label username;
    
    ObservableList<String> list = FXCollections.observableArrayList("Strawberry", "Avacado","Orange","Apple","Pineapple","Coconut","Mango","Peach","None");
    Image image1;
    static int index = -1;
    static ArrayList<Journal> journalList;
    static ArrayList<Journal> noteList;
    static ArrayList<Todo> todoList;
    
    /**
     * Initializes the controller class.
     * @param arg0
     * @param arg1
     */
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
                // TODO Auto-generated method stub    
                
                combobox.setItems(list);
                username.setText(LoginController.user.username);

                refreshJ();
                refreshN();
                refreshT();

                journallist.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2) {
                            try {
                                index = journallist.getSelectionModel().getSelectedIndex();
                                
                                Parent parent = FXMLLoader.load(getClass().getResource("journalnew.fxml"));
                                
                                Scene scene = new Scene(parent);
                                
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.showAndWait();
                                refreshJ();
                                index = -1;
                                
                            } catch (IOException ex) {
                                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                
                todolist.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2) {
                            try {
                                index = todolist.getSelectionModel().getSelectedIndex();
                                
                                Parent parent = FXMLLoader.load(getClass().getResource("todonew.fxml"));
                                
                                Scene scene = new Scene(parent);
                                
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.showAndWait();
                                refreshT();
                                index = -1;
                                
                            } catch (IOException ex) {
                                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                
                notelist.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2) {
                            try {
                                index = notelist.getSelectionModel().getSelectedIndex();
                                
                                Parent parent = FXMLLoader.load(getClass().getResource("notenew.fxml"));
                                
                                Scene scene = new Scene(parent);
                                
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.showAndWait();
                                refreshN();
                                index = -1;
                                
                            } catch (IOException ex) {
                                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
	}

    @FXML
    private void logout(javafx.event.ActionEvent event) throws IOException {
        LoginController.auth = null;
        LoginController.user = null;
        
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene scene = new Scene(parent, logout.getScene().getWidth(), logout.getScene().getHeight());
        
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
    }
    
    @FXML
    private void homeBtn(javafx.event.ActionEvent event) throws IOException {
        homepane.toFront();
        home.setEffect(new InnerShadow());
        journal.setEffect(null);
        todo.setEffect(null);
        note.setEffect(null);
    }
    
    @FXML
    private void journalBtn(javafx.event.ActionEvent event) throws IOException {
        journalpane.toFront();
        journal.setEffect(new InnerShadow());
        home.setEffect(null);
        todo.setEffect(null);
        note.setEffect(null);
    }
    
    @FXML
    private void diaryBtn(javafx.event.ActionEvent event) throws IOException {
        todopane.toFront();
        todo.setEffect(new InnerShadow());
        journal.setEffect(null);
        home.setEffect(null);
        note.setEffect(null);
    }
    
    @FXML
    private void noteBtn(javafx.event.ActionEvent event) throws IOException {
        notepane.toFront();
        note.setEffect(new InnerShadow());
        journal.setEffect(null);
        todo.setEffect(null);
        home.setEffect(null);
    }
    
    @FXML
    void changeImage(ActionEvent event) {
        if("Strawberry".equals(combobox.getValue())) {
            image1 = new Image("memoir/strawberry.png");
            image.setImage(image1);
        }
	  
	if("Avacado".equals(combobox.getValue())) {
            image1 = new Image("memoir/avocado.png");
            image.setImage(image1);
        }
            
        if("Orange".equals(combobox.getValue())) {
            image1 = new Image("memoir/Orange.png");
            image.setImage(image1);
        }
            
        if("Apple".equals(combobox.getValue())) {
            image1 = new Image("memoir/Apple.png");
            image.setImage(image1);
        }
		  
        if("None".equals(combobox.getValue())) {
            image1 = new Image("memoir/clipart1458421.png");
            image.setImage(image1);
        }
		  
        if("Pineapple".equals(combobox.getValue())) {
            image1 = new Image("memoir/Pineapple.png");
            image.setImage(image1);
        }
		  
        if("Coconut".equals(combobox.getValue())) {
            image1 = new Image("memoir/Coconut.png");
            image.setImage(image1);
        }
		  
        if("Mango".equals(combobox.getValue())) {
            image1 = new Image("memoir/Mango.png");
            image.setImage(image1);
        }
		  
        if("Peach".equals(combobox.getValue())) {
            image1 = new Image("memoir/Peaches.png");
            image.setImage(image1);
        }
    }

    @FXML
    private void addTodo(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("todonew.fxml"));
        
        Scene scene = new Scene(parent);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        refreshT();
        index = -1;
    }

    @FXML
    private void addJournal(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("journalnew.fxml"));
        
        Scene scene = new Scene(parent);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        refreshJ();
        index = -1;
    }

    @FXML
    private void addNote(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("notenew.fxml"));
        
        Scene scene = new Scene(parent);
        
        Stage stage = new Stage();
        stage.setOnCloseRequest(event1 -> {
            
        });
        stage.setScene(scene);
        stage.showAndWait();
        refreshN();
        index = -1;
    }
    
    void refreshJ(){
        ArrayList<String> newList = new ArrayList<>();
        int i = 0;
        
        journalList = RestClient.get_categorized_list("j",LoginController.auth);
        
        while(i < journalList.size()) {
            newList.add(i, journalList.get(i).title+" : "+journalList.get(i).description);
            i++;
        }
        
        journallist.setItems(FXCollections.observableArrayList(newList));    
    }
    
    void refreshN(){
        ArrayList<String> newList = new ArrayList<>();
        int i = 0;
        
        noteList = RestClient.get_categorized_list("d", LoginController.auth);
        
        while(i < noteList.size()) {
            newList.add(i, noteList.get(i).emotion+" - "+noteList.get(i).title+" : "+noteList.get(i).description);
            i++;
        }
        
        notelist.setItems(FXCollections.observableArrayList(newList));
    }
    
    void refreshT(){
        ArrayList<String> newList = new ArrayList<>();
        int i = 0;
                
        todoList = RestClient.get_all_todo(LoginController.auth);
        
        while(i < todoList.size()) {
            String[] seq = todoList.get(i).end_date.split(" "); 
            LocalDate date = LocalDate.parse(seq[0]);
            newList.add(i, date.getDayOfMonth()+" "+date.getMonth()+" - "+todoList.get(i).title+" : "+todoList.get(i).description);
            i++;
        }
        
        todolist.setItems(FXCollections.observableArrayList(newList));
    }
}