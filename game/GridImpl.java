package game;

public class GridImpl implements Grid {
    private final int size;
    private final PieceColour[][] grid;

    // Constructor
    public GridImpl(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("The length of one side of the square should not less than 1");
        }
        this.size = size;
        this.grid = new PieceColour[size][size];
        //Initializes the size x size grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = PieceColour.NONE; //Initialize the grid with NONE
            }
        }
    }

    // Returns the size
    @Override
    public int getSize() {
        return size;
    }
    
    // Returns the piece at the given row and column
    @Override
    public PieceColour getPiece(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Row or column should be non-negative");
        }
        if (row >= size ||col >= size) {
            throw new IllegalArgumentException("Row or column is out of bounds");
        }
        if (grid[row][col] == null) {
            return PieceColour.NONE; // Returns PieceColour.NONE if the position is empty
        }
        return grid[row][col]; 
    }

    // Sets the piece at the given row and column
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

    // Returns a deep copy of the grid
    // The grid returned should be a new object
    @Override
    public Grid copy() {
        GridImpl deepcopy = new GridImpl(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                deepcopy.setPiece(i, j, grid[i][j]); // copy the grid
            }
        }
        return deepcopy;
    }

    // Returns a string representation of the grid
    // The string should be like this: B = Black, W = White, . = Empty 
    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (grid[i][j]) {
                case WHITE:
                    stringbuilder.append('W');
                    break;
                case BLACK:
                    stringbuilder.append('B');
                    break;
                default:
                    stringbuilder.append('.');
                    break;
            }
            }
            stringbuilder.append('\n'); //start a new line after finishing row i
        }
        return stringbuilder.toString();
    }
}