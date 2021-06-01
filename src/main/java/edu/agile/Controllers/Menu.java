package edu.agile.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Menu{

    @FXML
    public Button startButton;

    @FXML
    public Button customButton;

    @FXML
    public Button exitButton;



    @FXML
    public void startButtonAction(MouseEvent event){
        if (event.getSource() == startButton) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Pane.fxml"));

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                Stage stage2 = (Stage) exitButton.getScene().getWindow();
                stage2.close();
                stage.show();



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void customButtonAction(MouseEvent event) {
        if (event.getSource() == customButton) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GameMenu.fxml"));

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                Stage stage2 = (Stage) exitButton.getScene().getWindow();
                stage2.close();
                stage.show();

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

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


