package edu.agile.Controllers;

import edu.agile.Models.Difficulty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameBoard implements Initializable {

    @FXML
    public Pane gamePane;

    @FXML
    public Pane menuPane;

    @FXML
    public MenuItem exitButton;

    @FXML
    public MenuItem menuButton;

    @FXML
    public Button restartButton;

    @FXML
    public Label pointLabel;

    @FXML
    public Label gameLabel;


    private static final int TILE_SIZE = 40;
    private final int WINDOW_WIDTH;
    private final int GAME_HEIGHT;
    private static final int MENU_HEIGHT = 25;

    private final int X_TILES;
    private final int Y_TILES;


    private final Tile[][] grid;
    private Scene scene;

    public int bombs = 0;
    public int flaggedBombs = 0;
    public boolean gameOver = false;
    public int score = 0;

    public Difficulty difficulty;

    public GameBoard(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.WINDOW_WIDTH = difficulty.getWidth();
        this.GAME_HEIGHT = difficulty.getHeight();
        this.X_TILES = WINDOW_WIDTH / TILE_SIZE;
        this.Y_TILES = GAME_HEIGHT / TILE_SIZE;
        this.grid = new Tile[X_TILES][Y_TILES];
    }

    private Parent createContent() {
        gamePane.setPrefSize(WINDOW_WIDTH, GAME_HEIGHT);
        menuPane.setPrefSize(WINDOW_WIDTH, GAME_HEIGHT + MENU_HEIGHT);

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.2, false);

                grid[x][y] = tile;
                gamePane.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                //Sets bombs

                if (tile.hasBomb) {
                    continue;
                }

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0) {
                    tile.text.setText(String.valueOf(bombs));
                }

            }
        }

        return gamePane;
    }

    private List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }

    private class Tile extends StackPane {
        private final int x;
        private final int y;
        private final boolean hasBomb;
        private boolean isOpen = false;
        private boolean isFlagged;

        private final Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private final Text text = new Text();

        public Tile(int x, int y, boolean hasBomb, boolean isFlagged) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;
            this.isFlagged = isFlagged;

            border.setStroke(Color.LIGHTGRAY);

            text.setFont(Font.font(18));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);

            if (this.hasBomb) {
                bombs += 1;
            }

            getChildren().addAll(border, text);

            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> {
                if(!gameOver) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (!this.isFlagged) {
                            open();
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        System.out.println("Right mouse clicked");
                        flag();
                    }
                }
            });
        }

        public void open() {
            if(isOpen) {
                return;
            }

            if (hasBomb) {
                gameOver();
            }

            isOpen = true;
            text.setVisible(true);
            border.setFill(null);
            score += 100;

            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Tile::open);
            }
        }

        public void flag() {
            if (isOpen) {
                return;
            }

            if(isFlagged){
                if(hasBomb){
                    flaggedBombs --;
                }
                isFlagged = false;
                border.setFill(Color.BLACK);
            } else {
                if(hasBomb){
                    flaggedBombs ++;
                }
                isFlagged = true;
                border.setFill(Color.RED);
            }

            if (flaggedBombs == bombs) {
                win();
            }
        }

        public void gameOver() {
            gameOver = true;
            pointLabel.setTextFill(Color.GREEN);
            pointLabel.setText("Score: " + score);
            gameLabel.setTextFill(Color.TOMATO);
            gameLabel.setText("Game Over");
            System.out.println("Score: " + score);
            System.out.println("Game Over");
        }

        public void win() {
            gameOver = true;
            pointLabel.setTextFill(Color.GREEN);
            pointLabel.setText("Score: " + score);
            gameLabel.setTextFill(Color.GREEN);
            gameLabel.setText("You defused all the bombs");
            System.out.println("Score: " + score);
            System.out.println("You defused all the bombs");
        }
    }


    @FXML
    public void menuButtonAction(ActionEvent event) {
        if (event.getSource() == menuButton) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Menu.fxml"));

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                Stage stage2 = (Stage) gamePane.getScene().getWindow();
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
                Stage stage = (Stage) gamePane.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createContent();
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    Stage stage = (Stage) restartButton.getScene().getWindow();
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
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

}

