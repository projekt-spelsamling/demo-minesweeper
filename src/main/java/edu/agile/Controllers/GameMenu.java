package edu.agile.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenu {

    @FXML
    public Button easyButton;

    @FXML
    public Button mediumButton;

    @FXML
    public Button hardButton;

    @FXML
    public MenuItem exitButton;

    @FXML
    public MenuItem menuButton;




    @FXML
    public void menuButtonAction(ActionEvent event) {
        if (event.getSource() == menuButton) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Menu.fxml"));

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                Stage stage2 = (Stage) easyButton.getScene().getWindow();
                stage2.close();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void exitButtonAction(ActionEvent event) {
        if (event.getSource() == exitButton) {
            try {
                Stage stage = (Stage) easyButton.getScene().getWindow();
                stage.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
