package minesweeper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.awt.*;        // Use AWT's Layout Manager
import java.awt.event.*;
import javax.swing.*;
import static minesweeper.MineSweeperMain.ROWS;
import static minesweeper.MineSweeperMain.COLS;

/**
 * Class to implement the generation of the array of mines for the game board
 */
public class MineArray {
    // array of locations which are mined
    boolean mined[][];

    public MineArray(){
        super();
    }

    // generate teh locations of all mines by randomly generating unique positions for each
    private int[] generate_locations(int rangeEnd, int numMines){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < rangeEnd; i++) list.add(i);
        Collections.shuffle(list);
        int[] ret = new int[numMines];
        for (int i = 0; i < numMines; i++) ret[i] = list.get(i);
        return ret;
    }

    // set up new mine locations for a new game
    public void newGame(int numMines){
        int[] locs = generate_locations(COLS * ROWS, numMines);
        this.mined = new boolean[COLS][ROWS];

        for(int i = 0; i < locs.length; i++){
            int loc = locs[i];
            this.mined[loc/COLS][loc%ROWS] = true;
        }
    }

    // show if a specific cell has a mine or not, for use in GameBoard
    public boolean hasMine(int row, int col){
        return this.mined[row][col];
    }
}
