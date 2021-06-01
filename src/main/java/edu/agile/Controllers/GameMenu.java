package edu.agile.Controllers;

import edu.agile.Models.Difficulty;
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

    public void easyButtonAction(MouseEvent event) {
        Difficulty difficulty = new Difficulty();
        difficulty.setHeight(200);
        difficulty.setWidth(400);
        if (event.getSource() == easyButton) {
            try {
                startGame(difficulty);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void mediumButtonAction(MouseEvent event) {
        Difficulty difficulty = new Difficulty();
        difficulty.setHeight(600);
        difficulty.setWidth(800);
        if (event.getSource() == mediumButton) {
            try {
                startGame(difficulty);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void hardButtonAction(MouseEvent event) {
        Difficulty difficulty = new Difficulty();
        difficulty.setHeight(800);
        difficulty.setWidth(1000);
        if (event.getSource() == hardButton) {
            try {
                startGame(difficulty);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void startGame(Difficulty difficulty) throws IOException {
        Stage stage = (Stage) easyButton.getScene().getWindow();
        stage.close();

        //Set new controller and pass game
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Pane.fxml")));
        GameBoard gameBoard = new GameBoard(difficulty);
        loader.setController(gameBoard);

        //Set stage with new scene
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


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
