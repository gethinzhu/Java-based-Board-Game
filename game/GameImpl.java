package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameImpl implements Game {
    private final Grid grid;
    private PieceColour currentPlayer;
    private PieceColour winner;

    // Constructor
    public GameImpl(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Grid size must be at least 1");
        }
        this.grid = new GridImpl(size);
        this.currentPlayer = PieceColour.WHITE; // White starts by default
        this.winner = null;
    }

    // The game is over when there is a winner or there are no more moves (a draw)
    @Override
    public boolean isOver() {
        boolean gameOver = false;
        if (winner != null) {
            gameOver = true;
        }
        if (getMoves().isEmpty()) {
            gameOver = true;
        }
        return gameOver;
    }

    // Returns the winner
    @Override
    public PieceColour winner() {
        if (winner == PieceColour.BLACK) {
            return PieceColour.BLACK;
        }
        if (winner == PieceColour.WHITE) {
            return PieceColour.WHITE;
        }  
            return PieceColour.NONE; // No winner(game not over or draw)
    }
    
    // Returns the current player
    @Override
    public PieceColour currentPlayer() {
        return currentPlayer;
    }
 
    // Returns a collection of all empty positions on the grid
    @Override
    public Collection<Move> getMoves() {
        List<Move> moves = new ArrayList<>(); // List to store valid moves
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                // Adds the position to the list of valid moves
                if (grid.getPiece(i, j) == PieceColour.NONE) {
                    moves.add(new MoveImpl(i, j)); 
                }
            }
        }
        return moves;
    }

    // Executes a move for the current player
    @Override
    public void makeMove(Move move) {  
        if (isOver()) {
            return;
        }
        // Throws IllegalArgumentException
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }
        int row = move.getRow(), col = move.getCol();
        if (row < 0 || row >= grid.getSize()) {
            throw new IllegalArgumentException("The position is out of bounds");
        } else if (col < 0 || col >= grid.getSize()) {
            throw new IllegalArgumentException("The position is out of bounds");
        }
        // Cannot place a piece on an occupied position, even the game is over
        if (grid.getPiece(row, col) != PieceColour.NONE) {
            throw new IllegalArgumentException("The position is already occupied"); 
        }
        // If the game is over, let the result of this method is undefined
        
        // Switches the player from white to black and vice versa
        grid.setPiece(move.getRow(), move.getCol(), currentPlayer);
        PieceColour lastPlayer = currentPlayer;
        if (currentPlayer == PieceColour.WHITE) {
            currentPlayer = PieceColour.BLACK;
        } else {
            currentPlayer = PieceColour.WHITE;
        }
        // Checks if the last player wins the game
        if (PathFinder.topToBottom(grid, lastPlayer) || PathFinder.leftToRight(grid, lastPlayer)) {
            winner = lastPlayer;
        }
        
    }
    
    // Returns a deep copy of the grid
    @Override
    public Grid getGrid() {
        return grid.copy();
    }

    // Returns a deep copy of the game
    @Override
    public Game copy() {
        GameImpl deepcopy = new GameImpl(grid.getSize());
        Grid deepGrid = ((GameImpl) deepcopy).grid; // Casting
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                    deepGrid.setPiece(i, j, grid.getPiece(i, j)); //deep copy
            }
        }
        deepcopy.currentPlayer = this.currentPlayer;
        deepcopy.winner = this.winner;
        return deepcopy;
    }
}