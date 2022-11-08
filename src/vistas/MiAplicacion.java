/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author henrypb
 */
public class MiAplicacion extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Circle circ = new Circle(40, 40, 30);
        Group root = new Group(circ);
        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("My JavaFX Application");
        stage.setScene(scene);
        stage.show();
    }  // start. 

    public static void main(String[] args) {
        Application.launch(args);
    }
    
}  // MiAplicacion. 
