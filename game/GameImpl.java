package game;

import java.util.ArrayList;
import java.util.Collection;

public class GameImpl implements Game {
    private final Grid grid;
    private PieceColour currentPlayer;
    private boolean isOver;
    private PieceColour winner;

    public GameImpl(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Grid size must be at least 1");
        }
        this.grid = new GridImpl(size);
        this.currentPlayer = PieceColour.WHITE;
        this.isOver = false;
        this.winner = PieceColour.NONE;
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public PieceColour currentPlayer() {
        return currentPlayer;
    }

    @Override
    public Collection<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        if (isOver) {
            return moves;
        }
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.getPiece(i, j) == PieceColour.NONE) {
                    moves.add(new MoveImpl(i, j));
                }
            }
        }
        return moves;
    }

    @Override
    public void makeMove(Move move) {
        if (isOver) {
            throw new IllegalStateException("Game is over");
        }
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }
        grid.setPiece(move.getRow(), move.getCol(), currentPlayer);
        // Check for winner
        if (PathFinder.topToBottom(grid, currentPlayer) || 
            PathFinder.leftToRight(grid, currentPlayer)) {
            isOver = true;
            winner = currentPlayer;
        } else if (getMoves().isEmpty()) {
            isOver = true;
            winner = PieceColour.NONE; // Draw
        }
        // Switch player
        currentPlayer = (currentPlayer == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;
    }

    @Override
    public boolean isOver() {
        return isOver;
    }

    @Override
    public PieceColour winner() {
        return winner;
    }

    @Override
    public Game copy() {
        GameImpl copy = new GameImpl(grid.getSize());
        // Copy grid state
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                PieceColour piece = grid.getPiece(i, j);
                if (piece != PieceColour.NONE) {
                    copy.grid.setPiece(i, j, piece);
                }
            }
        }
        copy.currentPlayer = this.currentPlayer;
        copy.isOver = this.isOver;
        copy.winner = this.winner;
        return copy;
    }
}