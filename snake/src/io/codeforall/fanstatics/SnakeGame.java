package io.codeforall.fanstatics;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel {

    // 2. Defining the position of squares
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    // the little squares on top of background
    int tileSize = 25;

    // 3. snake
    Tile snakeHead;

    // 6. food;
    Tile food;

    // 7. Random object
    Random random;



    SnakeGame(int boardWidth, int BoardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = BoardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);

        // 3.1. snake head
        snakeHead = new Tile(5, 5);

        // 6.1 snake food
        food = new Tile(10, 10);

        // 7.1
        random = new Random();
        placeFood();

    }

    // 4. methods to add color
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // shows a grid
        for (int i = 0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight); // vertically
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); // horizontally
        }

        // food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // snake
        g.setColor(Color.green);
        // 4.1 fillRect(0, 0, getWidth(), getHeight())
            // this shows a green rectangle in the beginning of grid
        // g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
    }

    // puts the food in random places
    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize); // 600/25 = 24
        food.y = random.nextInt(boardHeight/tileSize);
    }
}
