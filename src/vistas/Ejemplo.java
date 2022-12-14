package vistas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author pulgar.h
 */
public class Ejemplo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 200, 150);
        scene.setFill(Color.LIGHTGRAY);

        Circle circle = new Circle(60, 40, 30, Color.GREEN);

        Text text = new Text(10, 90, "JavaFX Scene");
        text.setFill(Color.DARKRED);

        Font font = new Font(20);
        text.setFont(font);

        root.getChildren().add(circle);
        root.getChildren().add(text);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    
}  // clase Ejemplo. 
