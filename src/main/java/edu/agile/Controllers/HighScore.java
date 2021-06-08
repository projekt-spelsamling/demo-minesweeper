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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HighScore implements Initializable {
    private static final String FXML_MAIN_MENU = "/Menu.fxml";
    private static final int HIGH_SCORE_SIZE = 10;

    public ScoreService scoreService;

    @FXML
    public TableView<Score> scoreTable;
    @FXML
    public TableColumn<Score, String> playerColumn;
    @FXML
    public TableColumn<Score, String> pointsColumn;
    @FXML
    public Button showMainMenu;

    public HighScore() {
        this.scoreService = ScoreService.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("player"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        scoreTable.getItems().addAll(scoreService.findHighScore(HIGH_SCORE_SIZE));
    }

    /**
     * Go back to main menu
     *
     * @param event
     */
    @FXML
    public void showMainMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) showMainMenu.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader((getClass().getResource(FXML_MAIN_MENU)));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
