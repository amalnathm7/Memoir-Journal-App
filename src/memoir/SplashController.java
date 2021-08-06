/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoir;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author amaln
 */
public class SplashController implements Initializable {

    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRotate(c1, true, 360, 5);
        setRotate(c2, true, 180, 10);
        setRotate(c3, true, 145, 15);
    }
    
    int rotate = 0;
    
    private void setRotate(Circle c, boolean reverse, int angle, int duration)
    {
        RotateTransition rotation = new RotateTransition(Duration.seconds(duration), c);
        
        rotation.setAutoReverse(reverse);
        
        rotation.setByAngle(angle);
        rotation.setDelay(Duration.seconds(0));
        rotation.setRate(3);
        rotation.setCycleCount(18);
        rotation.play();
    }
}
