package com.CS250;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX Main
 */
public class Main extends Application {

    private static Scene scene;

   
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Layout.fxml"));
        Parent root = loader.load();
        stage.setTitle("Paint in Netbeans");
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}