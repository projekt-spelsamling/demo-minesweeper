package edu.agile.Controllers;

import edu.agile.Models.Score;
import edu.agile.service.ScoreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for popup window that allows players to submit a high-score
 */
public class SubmitScore implements Initializable {
    private static final String FXML_PATH = "/Score.fxml";
    private final Score score;
    private final ScoreService scoreService;
    private final Stage window;

    @FXML
    Label points;
    @FXML
    TextField player;
    @FXML
    Button saveScore;
    @FXML
    Button abort;

    /**
     * @param game   game name
     * @param points points in the game
     */
    public SubmitScore(String game, int points) {
        this.window = new Stage();
        this.score = Score.builder()
                .points(points)
                .game(game)
                .build();
        this.scoreService = ScoreService.getInstance();
    }

    /**
     * Method to display the popup window. The previous window remains in the background but cannot be interacted with until the popup is closed.
     *
     * @param title title of window
     */
    public void display(String title) {
        try {
            window.setTitle(title);
            window.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
            loader.setController(this);
            Parent root = loader.load();
            window.setScene(new Scene(root));

            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the score to database
     *
     * @param event
     */
    private void saveScore(ActionEvent event) {
        String playerName = player.getText();
        if (playerName.length() > 0) {
            score.setPlayer(playerName);
        }
        scoreService.addScore(score);
        window.close();
    }

    /**
     * Close the popup without sending a score
     *
     * @param event
     */
    private void abort(ActionEvent event) {
        window.close();
    }

    /**
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        points.setText(String.valueOf(score.getPoints()));
        saveScore.setOnAction(this::saveScore);
        abort.setOnAction(this::abort);
    }
}
