package edu.agile.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
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


    private static final int TILE_SIZE = 40;
    private static final int WINDOW_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int MENU_HEIGHT = 25;

    private static final int X_TILES = WINDOW_WIDTH / TILE_SIZE;
    private static final int Y_TILES = GAME_HEIGHT / TILE_SIZE;


    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private Scene scene;

    private Parent createContent(){
        gamePane.setPrefSize(WINDOW_WIDTH, GAME_HEIGHT);
        menuPane.setPrefSize(WINDOW_WIDTH, GAME_HEIGHT + MENU_HEIGHT);

        for(int y = 0; y < Y_TILES; y++){
            for(int x = 0; x < X_TILES; x++){
                Tile tile = new Tile(x,y,Math.random() < 0.2);

                grid[x][y] = tile;
                gamePane.getChildren().add(tile);
            }
        }

        for(int y = 0; y < Y_TILES; y++){
            for(int x = 0; x < X_TILES; x++){
                Tile tile = grid[x][y];
                //Sets bombs

                if(tile.hasBomb){
                    continue;
                }

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if(bombs > 0){
                    tile.text.setText(String.valueOf(bombs));
                }

            }
        }

        return gamePane;
    }

    private List<Tile> getNeighbors(Tile tile){
        List<Tile> neighbors = new ArrayList<>();

        int[] points = new int[] {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for(int i = 0; i < points.length; i++){
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y +dy;

            if(newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES){
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }

    private class Tile extends StackPane{
        private int x,y;
        private boolean hasBomb;
        private boolean isOpen = false;
        private int bombs;

        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE -2);
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBomb){
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            border.setStroke(Color.LIGHTGRAY);

            text.setFont(Font.font(18));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);

            getChildren().addAll(border, text);

            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> open());
        }

        private void reload() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/Pane.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            Stage stage2 = (Stage) gamePane.getScene().getWindow();
            stage2.close();
            stage.show();
        }

        public void open(){
            if(isOpen){
                return;
            }

            if(hasBomb){
                System.out.println("Game Over");
                try {
                    reload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            isOpen = true;
            text.setVisible(true);
            border.setFill(null);

            if(text.getText().isEmpty()){
                getNeighbors(this).forEach(Tile::open);
            }
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
    }

}
