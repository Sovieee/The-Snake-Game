import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener , KeyListener
{
    private class Tile
    {
        int x;
        int y;

        Tile (int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    // snake

    Tile snakeHead;
    ArrayList <Tile> snakeBody;
    // food
    Tile food;
    
    Random random;

    // game logic

    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        setVisible(true);
        addKeyListener(this);
        setFocusable(true);

       snakeHead = new Tile(5,5);
       snakeBody = new ArrayList <Tile>();

       food = new Tile (10,10);

        random = new Random();
        placeFood();
        velocityX = 1; // going right
        velocityY = 0; // going downwards (if 1)

        // game timmer

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g)
    {
        // grid's

        // for (int i=0; i < boardWidth/tileSize; i++)
        // {
        //     g.drawLine( i*tileSize , 0 , i*tileSize , boardHeight); // vertical lines (x1,y1,x2,y2)
        //     g.drawLine(0 , i*tileSize , boardWidth , i*tileSize); // horizontal lines (x1,y1,x2,y2)
        // }


        // food's looks

        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize, food.y*tileSize , tileSize, tileSize,true);


        

        // snake's head look

        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize,true);

        // snake's body look

        for (int i = 0; i < snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

        // score and other info at display

        g.setFont(new Font("Arial" , Font.PLAIN , 16));
        if (gameOver)
        {
            g.setColor(Color.red);
            g.drawString ("GAME OVER: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("SCORE: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        


    }

    public void placeFood()
    {
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2)
    {
        return tile1.x == tile2.x && tile1.y == tile2.y;

    }

    public void move ()
    {

        // eating food check

        if (collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        
        // moving snake body as a whole

        for (int i = snakeBody.size()-1 ; i >= 0 ; i--)
        {
            Tile snakePart = snakeBody.get(i);
            if (i == 0)
            {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // moving snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;


        // game over conditions

        for (int i =0; i < snakeBody.size() ; i++)
        {
            Tile snakePart = snakeBody.get(i);
             // collision with head

             if (collision(snakeHead, snakePart))
             {
                gameOver = true;
             }
        }

        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight)
        {
            gameOver = true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        move();
        repaint();                                     // after every 100 miliseconds the repaint will be called and it will repaint the whole game again n again
        if (gameOver)
        {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
       
            if (e.getKeyCode() == KeyEvent.VK_UP && velocityY !=1) // if Up arrow key is pressed then ,
            {
                velocityX = 0;
                velocityY = -1; // snake head moves upwards
            }

            else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1)
            {
                velocityX = 0;
                velocityY = 1;
            }

            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1)
            {
                velocityX = 1;
                velocityY = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) 
            {
                velocityX = -1;
                velocityY = 0;
            }

    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
