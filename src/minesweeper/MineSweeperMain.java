package minesweeper;
import java.awt.*;        // Use AWT's Layout Manager
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * The minesweeper application makes a basic version of minewsweeper
 * with a 10X10 board and 10 mines.
 *
 * @author  Joshua Burrows
 * @version 1.0
 * @since   2014-07-11
 */
public class MineSweeperMain extends JFrame{
    // Statistics of the game board
    public static int ROWS = 10;
    public static int COLS = 10;
    public static int numMines = 10;

    // Button to call new game
    JButton new_game = new JButton("New Game");

    // The board the game is played on
    GameBoard board = new GameBoard(new_game);

    // Initialize application
    public MineSweeperMain(){
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(new_game, BorderLayout.SOUTH);
        cp.add(board, BorderLayout.CENTER);

        new_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame(numMines);
                board.paint();
            }
        });

        pack();  // Pack the UI components, instead of setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // handle window-close button
        setTitle("Minesweeper");
        setVisible(true);   // show it
    }

    // Main method to launch the application
    public static void main(String[] args){
        MineSweeperMain game = new MineSweeperMain();
        game.board.paint();
    }
}
