package edu.agile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class JavaFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Menu.fxml"));


        Scene scene = new Scene(root);
        stage.setTitle("Newton Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void runJavaFX(String[] args) {
        launch();
    }

}