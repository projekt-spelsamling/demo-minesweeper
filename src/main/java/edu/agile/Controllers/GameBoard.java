package edu.agile;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends Application {

    private static final int TILE_SIZE = 40;
    private static final int W = 800;
    private static final int H = 600;

    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H / TILE_SIZE;
    private static int score = 0;


    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private Scene scene;

    public int bombs = 0;
    public int flaggedBombs = 0;
    public boolean gameOver = false;

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(W,H);

        for(int y = 0; y < Y_TILES; y++){
            for(int x = 0; x < X_TILES; x++){
                Tile tile = new Tile(x,y,Math.random() < 0.2, false);

                grid[x][y] = tile;
                root.getChildren().add(tile);
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

        return root;
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
        private boolean isFlagged = false;

        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE -2);
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBomb, boolean isFlagged){
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;
            this.isFlagged = isFlagged;

            border.setStroke(Color.LIGHTGRAY);

            text.setFont(Font.font(18));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);

            if(this.hasBomb){
                bombs += 1;
            }

            getChildren().addAll(border, text);

            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> {
                if(!gameOver){
                    if(e.getButton() == MouseButton.PRIMARY){
                        if(!this.isFlagged){
                            open();
                        }
                    } else if(e.getButton() == MouseButton.SECONDARY){
                        System.out.println("Right mouse clicked");
                        flag();
                    }
                }
            });
        }

        public void open(){
            if(isOpen){
                return;
            }

            if(hasBomb){
                gameOver();
            }

            isOpen = true;
            text.setVisible(true);
            border.setFill(null);
            score += 100;

            if(text.getText().isEmpty()){
                getNeighbors(this).forEach(Tile::open);
            }
        }

        public void flag(){
            if(isOpen){
                return;
            }

            if(isFlagged && hasBomb){
                isFlagged = true;
                flaggedBombs = 1;
                border.setFill(Color.RED);
            }
            else if(isFlagged){
                isFlagged = true;
                border.setFill(Color.RED);
            }
            else {
                isFlagged = false;
                border.setFill(Color.BLACK);
            }

            if(flaggedBombs == bombs){
                win();
            }
        }

        public void gameOver(){
            gameOver = true;
            if(gameOver){
                getNeighbors(this).forEach(Tile::open);
            }
            System.out.println("Score: " + score);
            System.out.println("Game Over");
        }

        public void win(){
            gameOver = true;
            System.out.println("Score: " + score);
            System.out.println("You defused all the bombs");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        System.out.println(args);
        launch(args);
    }
}
