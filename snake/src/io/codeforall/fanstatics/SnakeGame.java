package io.codeforall.fanstatics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

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

    // 8. game logic
    Timer gameLoop;

    // 8.4 setting the moves
    int velocityX;
    int velocityY;


    SnakeGame(int boardWidth, int BoardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = BoardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        // 9.1 the game must listen to key actions
        addKeyListener(this);
        setFocusable(true);

        // 3.1. snake head
        snakeHead = new Tile(5, 5);

        // 6.1 snake food
        food = new Tile(10, 10);

        // 7.1
        random = new Random();
        placeFood();

        // 8.5
        velocityX = 0; // -1 goes to the left
        velocityY = 0;

        // 8.1
        gameLoop = new Timer(100, this);
        // 8.3
        gameLoop.start();
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

    public void move(){
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
    }

    // 8.2
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        // calls draw() over and over again
        repaint();
    }


    // 9. Making the squares move through key pressing
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            velocityX = 0;
            velocityY = -1;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            velocityX = 0;
            velocityY = 1;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            velocityX = -1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            velocityX = 1;
            velocityY = 0;
        }

    }

    // do not need this
    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e) {}
}
