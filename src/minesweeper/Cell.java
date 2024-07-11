package minesweeper;
import java.awt.*;        // Use AWT's Layout Manager
import java.awt.event.*;
import javax.swing.*;
import static minesweeper.MineSweeperMain.ROWS;
import static minesweeper.MineSweeperMain.COLS;

/**
 * Class to implement the interactable cells with mines or not in the game
 * Displays the text of adjacent mines and displays if the cell is revealed
 * or not
 */
public class Cell extends JButton{
    // Colors for each state
    public static final Color HIDDEN  = Color.GRAY;
    public static final Color REVEALED = Color.BLUE;
    public static final Color TEXT_REVLEAED = Color.ORANGE;
    public static final Color FLAGGED = Color.RED;

    // location of the cell
    int row;
    int col;

    // status of the cell
    boolean hasMine;
    boolean isRevealed;
    boolean isFlagged;

    // number of adjacent mines
    int adj;

    // initialize cell based on location
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.setFocusable(false);
    }

    // Method to initialize each cell with relevant information
    public void newGame(boolean hasMine, int adj){
        this.hasMine = hasMine;
        this.isRevealed = false;
        this.isFlagged = false;
        this.adj = adj;
        super.setEnabled(true);  // enable button
        super.setText("");       // display blank
    }



    /** Paint itself based on its status */
    public void paint() {
        super.setBackground(isRevealed? REVEALED: HIDDEN);
        super.setForeground(TEXT_REVLEAED);

        if(isRevealed == false && isFlagged){
            super.setIcon(new ImageIcon(MineSweeperMain.class.getResource("/minesweeper/sprites/flag.png")));
            super.setText(null);
        }
        else if(isRevealed && hasMine){
            super.setIcon(new ImageIcon(MineSweeperMain.class.getResource("/minesweeper/sprites/mine.png")));
            super.setText(null);
        }
        else{
            super.setIcon(null);
            if(isRevealed) {
                super.setText(Integer.toString(this.adj));
            }
        }
        super.setOpaque(true);
        super.setBorderPainted(false);
    }
}
