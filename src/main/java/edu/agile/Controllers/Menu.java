package edu.agile.Controllers;

import edu.agile.Models.Difficulty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Menu {
    public static final String FXML_HIGH_SCORE = "/highscore.fxml";

    @FXML
    public Button startButton;

    @FXML
    public Button customButton;

    @FXML
    public Button showHighScore;

    @FXML
    public Button exitButton;


    @FXML
    public void startButtonAction(MouseEvent event) {
        Difficulty difficulty = new Difficulty();
        difficulty.setHeight(600);
        difficulty.setWidth(800);
        if (event.getSource() == startButton) {
            try {
                startGame(difficulty);

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
                stage.setTitle("Newton Minesweeper");
                Stage stage2 = (Stage) exitButton.getScene().getWindow();
                stage2.close();
                stage.show();

            } catch (Exception e) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startGame(Difficulty difficulty) throws IOException {
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.close();

        //Set new controller and pass game
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Pane.fxml")));
        GameBoard gameBoard = new GameBoard(difficulty);
        loader.setController(gameBoard);

        //Set stage with new scene
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Newton Minesweeper");
        stage.show();
    }

    public void showHighScore(MouseEvent mouseEvent) {
        try {
            Stage stage = (Stage) showHighScore.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader((getClass().getResource(FXML_HIGH_SCORE)));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


