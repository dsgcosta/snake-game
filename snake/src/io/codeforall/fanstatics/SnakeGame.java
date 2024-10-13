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
    // 10. snake body
    ArrayList<Tile> snakeBody;


    // 6. food;
    Tile food;

    // 7. Random object
    Random random;

    // 8. game logic
    Timer gameLoop;

    // 8.4 setting the moves
    int velocityX;
    int velocityY;

    // 14 Game over conditions
    boolean gameOver = false;


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
        // 10.1
        snakeBody = new ArrayList<Tile>();

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
        /*
        for (int i = 0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight); // vertically
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); // horizontally
        }
         */

        // food
        g.setColor(Color.red);
       // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // snake head
        g.setColor(Color.green);
        // 4.1 fillRect(0, 0, getWidth(), getHeight())
            // this shows a green rectangle in the beginning of grid
        // g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // 12.2 add more tiles to snake after eat
        for (int i = 0; i< snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Score
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    // puts the food in random places
    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize); // 600/25 = 24
        food.y = random.nextInt(boardHeight/tileSize);
    }

    // 12. Detect collisions
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        // 12.1 eat food (add a new tile to snake if collision)
        if (collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        
        // 13. snake body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
            
        }

        // snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        
        // 14.1 Game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);

            // collide with snake head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }

            // 14.3 Game over if collide with walls
            if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
               snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
                gameOver = true;
            }
            
        }
    }

    // 8.2
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        // calls draw() over and over again
        repaint();

        // 14.2 Game over stops game
        if(gameOver){
            gameLoop.stop();
        }
    }


    // 9. Making the squares move through key pressing
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
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
