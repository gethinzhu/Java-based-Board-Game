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

    // True if the game is over
    // The game is over when there is a winner or there are no more moves (a draw)
    @Override
    public boolean isOver() {
        boolean flag = false;
        if (winner != null) {
            flag = true;
        }
        if (getMoves().isEmpty()) {
            flag = true;
        }
        return flag;
    }

    // The colour of the winner.
    // You should use the PathFinder class to determine the winner
    // Should return PieceColour.NONE if the game is not over
    // Should also return PieceColour.NONE if the game is a draw
    @Override
    public PieceColour winner() {
        if (winner == PieceColour.BLACK) {
            return PieceColour.BLACK;
        }
        if (winner == PieceColour.WHITE) {
            return PieceColour.WHITE;
        }  
            return PieceColour.NONE;
    }
    
    // Returns the current player
    // If the game is over, the output of this method does not matter
    @Override
    public PieceColour currentPlayer() {
        return currentPlayer;
    }
 
    // Returns a collection of all empty positions on the grid
    // If there are no more valid moves, the collection will be empty
    @Override
    public Collection<Move> getMoves() {
        List<Move> moves = new ArrayList<>(); // List to store valid moves
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                // Add the position to the list of valid moves is it is empty
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
        // If the game is over, let the result of this method is undefined
        if (isOver()) {
            return;
        }
        // Throws IllegalArgumentException if the move is null
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }
        int row = move.getRow(), col = move.getCol();
        if (row < 0 || row >= grid.getSize()) {
            throw new IllegalArgumentException("The position is out of bounds");
        } else if(col < 0 || col >= grid.getSize()) {
            throw new IllegalArgumentException("The position is out of bounds");
        }
        // Throws IllegalArgumentException if the position is invalid ( e.g. out of bounds or being occupied )
        if (grid.getPiece(row, col) != PieceColour.NONE) {
            throw new IllegalArgumentException("The position is already occupied");
        }
        grid.setPiece(move.getRow(), move.getCol(), currentPlayer);
        PieceColour lastPlayer = currentPlayer;
        // Switch player from white to black or vice versa
        if (currentPlayer == PieceColour.WHITE) {
            currentPlayer = PieceColour.BLACK;
        } else {
            currentPlayer = PieceColour.WHITE;
        }
        // Check if the last player has won the game
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
    // The game returned should be a new object
    @Override
    public Game copy() {
        GameImpl deepcopy = new GameImpl(grid.getSize());
        // Copy grid state
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                PieceColour piece = grid.getPiece(i, j);
                if (piece != PieceColour.NONE) {
                    deepcopy.getGrid().setPiece(i, j, piece);
                }
            }
        }
        deepcopy.currentPlayer = this.currentPlayer;
        deepcopy.winner = this.winner;
        return deepcopy;
    }
}