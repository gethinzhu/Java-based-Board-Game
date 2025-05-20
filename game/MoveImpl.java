package game;

public class MoveImpl implements Move {
    private final int row;
    private final int col;

    public MoveImpl(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Rows and columns can not be zero or negative.");
        }
        this.row = row;
        this.col = col;
    }

    // Returns the row of the move
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