package game;

public class GridImpl implements Grid {
    private final int size;
    private final PieceColour[][] grid;

    public GridImpl(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("The length of one side of the square should not less than 1");
        }
        this.size = size;
        this.grid = new PieceColour[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = PieceColour.NONE;
            }
        }
    }

    // Returns the size of the grid
    // The grid is always a square
    // This is the length of one side of the square
    @Override
    public int getSize() {
        return size;
    }
    
    // Returns the piece at the given row and column
    // Should return PieceColour.NONE if the position is empty
    // Throws IllegalArgumentException if the row or column is out of bounds
    @Override
    public PieceColour getPiece(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Row or column should be non-negative");
        }
        if (row >= size ||col >= size) {
            throw new IllegalArgumentException("Row or column is out of bounds");
        }
        return grid[row][col];
    }

    // Sets the piece at the given row and column
    // Throws IllegalArgumentException if the piece is not a valid colour
    @Override
    public void setPiece(int row, int col, PieceColour piece) {
        if (row < 0|| col < 0) {
            throw new IllegalArgumentException("Row or column should be non-negative");
        }
        if (row >= size ||col >= size) {
            throw new IllegalArgumentException("Row or column is out of bounds");
        }
        if (piece == null) {
            throw new IllegalArgumentException("The piece is not a valid colour");
        }
        grid[row][col] = piece;
    }

    // Returns a copy of the grid
    // Note that this is should be a deep copy
    // Which means that the grid returned should be a new object
    // And there is no way to modify the internal state of this grid
    // by modifying the grid returned
    @Override
    public Grid copy() {
        GridImpl copy = new GridImpl(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy.setPiece(i, j, grid[i][j]); // copy the grid
            }
        }
        return copy;
    }

    // Returns a string representation of the grid
    // The string should be like this: B = Black, W = White, . = Empty 
    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == PieceColour.WHITE) {
                    stringbuilder.append('W');
                } else if (grid[i][j] == PieceColour.BLACK) {
                    stringbuilder.append('B');
                } else {
                    stringbuilder.append('.');
                }
            }
            stringbuilder.append('\n');
        }
        return stringbuilder.toString();
    }
}