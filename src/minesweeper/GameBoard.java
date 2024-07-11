package minesweeper;
import java.awt.*;        // Use AWT's Layout Manager
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import static minesweeper.MineSweeperMain.ROWS;
import static minesweeper.MineSweeperMain.COLS;

/**
 * Class to define the game board and relevant pieces
 */
public class GameBoard extends JPanel{
    // Statistics to define the game board
    public static final int CELL_SIZE = 60;
    public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;
    public static final int BOARD_WIDTH = CELL_SIZE * COLS;

    // Create the array of interactable cells
    Cell cells[][] = new Cell[ROWS][COLS];
    //Define locations of mines
    MineArray mineArray = new MineArray();
    // number of mines in the game
    int numMines = MineSweeperMain.numMines;

    // status of loss or victory
    boolean lost = false;
    boolean victory = false;

    // new game button can be altered to reflect victory or loss
    JButton newGameButton;

    // Method to initialize board object, takes button to be altered as input
    public GameBoard(JButton newGameButton){
        super.setLayout(new GridLayout(ROWS, COLS, 1, 1));

        MyMouseListener ml = new MyMouseListener();

        //initialize cells for the game to be played with and attach mouse interaction
        for(int row = 0; row < ROWS; row++){
            for(int col = 0;col < COLS; col++){
                cells[row][col] = new Cell(row, col);
                cells[row][col].addMouseListener(ml);
                super.add(cells[row][col]);
            }
        }

        // attach button for later alteration
        this.newGameButton = newGameButton;

        super.setPreferredSize(new Dimension(BOARD_HEIGHT, BOARD_WIDTH));

        // start a new game
        this.newGame(numMines);
    }

    // method to paint the scene
    public void paint() {
        //paint each cell
        for (int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++){
                cells[i][j].paint();
            }
        }

        // set text on game button
        String text = "New Game";
        if(this.lost){text = "You Lost... New Game?";}
        else if(this.victory){text = "You Won! New Game?";}
        this.newGameButton.setText(text);
    }

    // count the number of mines adjacent to the current mine for display on the cells
    public int adjMines(int i, int j){
        int sum = 0;
        for (int indi = (i > 0 ? -1 : 0); indi <= (i < ROWS-1 ? 1 : 0);
             indi++) {
            for (int indj = (j > 0 ? -1 : 0);
                 indj <= (j < COLS-1 ? 1 : 0); ++indj) {
                if (indi != 0 || indj != 0) {
                    sum += this.mineArray.mined[i + indi][j + indj]? 1:0;
                }
            }
        }
        return sum;
    }

    //helper method to randomly reveal a cell to start the game
    public void randomReveal(){
        boolean cont = true;
        while(cont){
            Random rand = new Random();
            int i = rand.nextInt(ROWS);
            int j = rand.nextInt(COLS);
            Cell current = cells[i][j];
            if(current.hasMine == false && current.adj == 0){
                current.isRevealed = true;
                revealAdjacent(i,j);
                cont = false;
            }
        }


    }

    // Method to start a new game and initialize all game elements
    public void newGame(int numMines){
        // set mine array
        this.mineArray.newGame(numMines);

        // initialize all cells
        for (int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++){
                cells[i][j].newGame(this.mineArray.hasMine(i,j), adjMines(i,j));
            }
        }

        randomReveal();
        this.lost = false;
        this.victory = false;
    }

    // method to see if the player has lost the game
    public void checkLoss(){
        for (int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++){
                if(cells[i][j].hasMine && cells[i][j].isRevealed){
                        this.lost = true;
                }
            }
        }
    }

    //method to see if the player has won the game
    public void checkWin(){
        int sumRevealed = 0;
        // sum revealed cells which don't have mines
        for (int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++){
                if(cells[i][j].isRevealed && !cells[i][j].hasMine){
                    sumRevealed += 1;
                }
            }
        }

        if(sumRevealed == ROWS * COLS - numMines){
            this.victory = true;
            System.out.println("Victory");
        }
    }

    // helper method to reveal adjacent cells
    public void revealAdjacent(int i, int j){
        // Recursively reveal the 8 neighboring cells
        for (int row = i - 1; row <= i + 1; row++) {
            for (int col = j - 1; col <= j + 1; col++) {
                // Need to ensure valid row and column numbers too
                if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                    if (!cells[row][col].isRevealed) {
                        cells[row][col].isRevealed=true;
                        if(cells[row][col].adj ==0){revealAdjacent(row,col);}
                    }
                }
            }
        }
    }


    // method to handle mouse input for the cells to see if they are being flagged or revealed
    // also advances game state after an action by painting and checking for victory
    public void handleInput(MouseEvent evt){
        if(!this.lost && !this.victory){
            if (evt.getComponent() instanceof Cell){
                Cell current = (Cell) evt.getComponent();
                if(evt.getButton() == MouseEvent.BUTTON1){
                    current.isRevealed = true;
                }
                if(evt.getButton() == MouseEvent.BUTTON3){
                    current.isFlagged = !current.isFlagged;
                }
                if(current.isRevealed && current.adj == 0){
                    revealAdjacent(current.row, current.col);
                }
            }
            checkLoss();
            checkWin();
            paint();
        }
    }

    // Implementation of a mouse listener to take inputs
    private class MyMouseListener implements MouseListener {
        // Called back upon mouse clicked
        @Override
        public void mouseClicked(MouseEvent evt) {

        }

        // Not used - need to provide an empty body to compile.
        @Override public void mousePressed(MouseEvent evt) {     handleInput(evt);}
        @Override public void mouseReleased(MouseEvent evt) { }
        @Override public void mouseEntered(MouseEvent evt) { }
        @Override public void mouseExited(MouseEvent evt) { }
    }
}
