package edu.agile.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Menu {

    @FXML
    public Button startButton;

    @FXML
    public Button customButton;

    @FXML
    public Button exitButton;


    @FXML
    public void exitButtonAction(MouseEvent event) {
        if (event.getSource() == exitButton) {
                try {
                    Stage stage = (Stage) exitButton.getScene().getWindow();
                    stage.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


