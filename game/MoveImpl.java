package game;

public class MoveImpl implements Move {
    private final int row;
    private final int col;

    // Constructor
    public MoveImpl(int row, int col) {
        // Checks if the row and column are valid
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Row and column can not be zero or negative.");
        }
        this.row = row;
        this.col = col;
    }

    // Returns row
    @Override
    public int getRow() {
        return row;
    }

    // Returns the column of the move
    @Override
    public int getCol() {
        return col;
    }

    
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}