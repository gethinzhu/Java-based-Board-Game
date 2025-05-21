package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameImpl implements Game {
    private final Grid grid;
    private PieceColour currentPlayer;
    private PieceColour winner;

    public GameImpl(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Grid size must be at least 1");
        }
        this.grid = new GridImpl(size);
        this.currentPlayer = PieceColour.WHITE;
        this.winner = null;
    }

    // True if the game is over
    // The game is over when there is a winner or there are no more moves (a draw)
    @Override
    public boolean isOver() {
        return winner != null || getMoves().isEmpty();
    }

    // The colour of the winner.
    // You should use the PathFinder class to determine the winner
    // Should return PieceColour.NONE if the game is not over
    // Should also return PieceColour.NONE if the game is a draw
    @Override
    public PieceColour winner() {
        if (winner == PieceColour.BLACK) {
            return PieceColour.BLACK;
        }else if (winner == PieceColour.WHITE) {
            return PieceColour.WHITE;
        }  
            return PieceColour.NONE;
    }
    

    // The colour of the current player (the player who will make the next move)
    // If the game is over, the output of this method is undefined
    // That is, it does not matter what this method returns if the game is over
    @Override
    public PieceColour currentPlayer() {
        return currentPlayer;
    }
 
    // Gets a Collection of all valid moves for the current player
    // See https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Collection.html
    // Note that ArrayList implements Collection
    // The collection should be empty if there are no valid moves
    @Override
    public Collection<Move> getMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.getPiece(i, j) == PieceColour.NONE) {
                    moves.add(new MoveImpl(i, j));
                }
            }
        }
        return moves;
    }

    // Executes a move for the current player
    // Updates the internal game state to reflect the move
    // Changes the current player to the other colour after the move is made
    // If the game is over, the result of this method is undefined
    // That is, it does not matter what this method does if the game is over
    // Throws an IllegalArgumentException if the move is invalid
    // An invalid move is one where the position is already occupied
    // or the position is out of bounds
    @Override
    public void makeMove(Move move) {  
        // If the game is over, do nothing
        if (isOver()) {
            return;
        }
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }
        int row = move.getRow(), col = move.getCol();
        if (row < 0 || row >= grid.getSize()) {
            throw new IllegalArgumentException("The position is out of bounds");
        } else if(col < 0 || col >= grid.getSize()) {
            throw new IllegalArgumentException("The position is out of bounds");
        } else if (grid.getPiece(row, col) != PieceColour.NONE) {
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
    
    // Returns a copy of the grid
    // Note that this is should be a deep copy
    // Which means that the grid returned should be a new object
    // And there is no way to modify the internal state of the game
    // by modifying the grid returned
    @Override
    public Grid getGrid() {
        return grid.copy();
    }

    // Returns a copy of the game
    // Note that this is should be a deep copy
    // Which means that the game returned should be a new object
    // And there is no way to modify the internal state of the this game
    // by modifying the game returned
    @Override
    public Game copy() {
        GameImpl copy = new GameImpl(grid.getSize());
        // Copy grid state
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                PieceColour piece = grid.getPiece(i, j);
                if (piece != PieceColour.NONE) {
                    copy.getGrid().setPiece(i, j, piece);
                }
            }
        }
        copy.currentPlayer = this.currentPlayer;
        copy.winner = this.winner;
        return copy;
    }
}