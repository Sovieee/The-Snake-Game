import javax.swing.*;
public class snake {
    public static void main(String[] args) throws Exception{
        
        int boardHeight = 600;
        int boardWidth = boardHeight;

        JFrame frame = new JFrame ("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth , boardHeight); // creates a window frame of 600X600 pixels
        frame.setLocationRelativeTo(null);    // will ensure that the window opens at the center of the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // will make sure that window closes as user clicks on close button
       
       
        SnakeGame obj = new SnakeGame (boardWidth, boardHeight);
        frame.add(obj);
        frame.pack();
        obj.requestFocus(); // will listen for key presses
    }
    
}
