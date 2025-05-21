package game.tests;

import game.*;

public class GameTest extends Test {
    public static void main(String[] args) {
        boolean caught = false;
        try {
            new GameImpl(0);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        caught = false;
        try {
            new GameImpl(5);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(false, caught);

        // TODO: Complete this test
        // Test the GameImpl class
        // Initialize the game with a 5x5 grid
        Game game = new GameImpl(5);
        expect(PieceColour.WHITE, game.currentPlayer()); // White starts
        expect(25, game.getMoves().size()); // 5x5 grid as all positions are available
        expect(false, game.isOver()); // Has not over at the beginning
        expect(PieceColour.NONE, game.winner()); // There is no winner at the beginning
        expect(5, game.getGrid().getSize()); // Test whether the grid size is correct

        // Test whether the getGrid method could return a deep copy of the grid
        Grid gridCopy = game.getGrid();
        gridCopy.setPiece(0, 0, PieceColour.BLACK); // Modify copy
        expect(PieceColour.BLACK, gridCopy.getPiece(0, 0)); // The gridCopy is modified
        expect(PieceColour.NONE, game.getGrid().getPiece(0, 0)); // The original one is unchanged

        // Test makeMove and state updates
        game.makeMove(new MoveImpl(0, 0)); // White moves at (0, 0)
        expect(PieceColour.BLACK, game.currentPlayer()); // Now the current player should be black
        expect(PieceColour.WHITE, game.getGrid().getPiece(0, 0)); // White piece placed at (0, 0)
        expect(24, game.getMoves().size()); // There should be 24 moves left as (0, 0) is occupied


        // Test some invalid moves
        // Test whether it throws an exception when the move is null
        caught = false;
        try {
            game.makeMove(null); 
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        // Test whether it throws an exception when the move is out of bounds
        caught = false;
        try {
            game.makeMove(new MoveImpl(-1, 0)); // The row is out of bounds
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        caught = false;
        try {
            game.makeMove(new MoveImpl(0, -1)); // The column is out of bounds
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        caught = false;
        try {
            game.makeMove(new MoveImpl(0, 0)); // The position is already occupied
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);


        // Test White winning (left to right)
        game.makeMove(new MoveImpl(4, 0)); // Black
        game.makeMove(new MoveImpl(0, 1)); // White
        game.makeMove(new MoveImpl(4, 1)); // Black
        game.makeMove(new MoveImpl(0, 2)); // White
        game.makeMove(new MoveImpl(4, 2)); // Black
        game.makeMove(new MoveImpl(0, 3)); // White
        game.makeMove(new MoveImpl(4, 3)); // Black
        game.makeMove(new MoveImpl(0, 4)); // White wins the game as it has 5 pieces in a row (0,0 to 0,4)
        expect(PieceColour.WHITE, game.winner()); //Test whether the winner is correct
        expect(true, game.isOver()); // Test whether the game is over
        expect(16, game.getMoves().size()); // There should be left 16 moves as 25 moves minus 9 moves (5 White + 4 Black)
        // Test whether the game is truly over
        PieceColour whiteWinner = game.winner();
        game.makeMove(new MoveImpl(4, 4)); // The game is over, so this move should not generate another winner
        expect(whiteWinner, game.winner());
        // Test game end behavior with copy
        Game copyGame1 = game.copy();
        copyGame1.makeMove(new MoveImpl(1, 0)); // Modify copy
        expect(PieceColour.WHITE, game.winner()); // Original unchanged
        expect(PieceColour.WHITE, copyGame1.winner()); // Copy winner unchanged

        // Test Black winning (top to bottom)
        Game game2 = new GameImpl(5);
        game2.makeMove(new MoveImpl(0, 0)); // White 
        game2.makeMove(new MoveImpl(0, 4)); // Black
        game2.makeMove(new MoveImpl(0, 1)); // White
        game2.makeMove(new MoveImpl(1, 4)); // Black
        game2.makeMove(new MoveImpl(0, 2)); // White
        game2.makeMove(new MoveImpl(2, 4)); // Black
        game2.makeMove(new MoveImpl(0, 3)); // White
        game2.makeMove(new MoveImpl(3, 4)); // Black
        game2.makeMove(new MoveImpl(3, 3)); // White
        game2.makeMove(new MoveImpl(4, 4)); // Black wins the game as it has 5 pieces in a row (0,4 to 4,4)
        expect(PieceColour.BLACK, game2.winner());
        expect(true, game2.isOver());
        expect(16, game.getMoves().size()); // There should be left 16 moves as 25 moves minus 9 moves (5 White + 4 Black)
        // Test whether the game is truly over
        PieceColour blackWinner = game.winner();
        game.makeMove(new MoveImpl(0, 4)); // The game is over, so this move should not generate another winner
        expect(blackWinner, game.winner());

        // Test game in a 3x3 grid
        // The author plays this with his friend
        // SO this is a real recorded game
        // Of course, the author wins
        Game game3 = new GameImpl(3);
        game3.makeMove(new MoveImpl(1, 1)); // White
        game3.makeMove(new MoveImpl(0, 0)); // Black
        game3.makeMove(new MoveImpl(2, 0)); // White
        game3.makeMove(new MoveImpl(1, 0)); // Black
        game3.makeMove(new MoveImpl(2, 2)); // White
        game3.makeMove(new MoveImpl(2, 1)); // Black
        game3.makeMove(new MoveImpl(1, 2)); // White
        game3.makeMove(new MoveImpl(0, 2)); // Black
        game3.makeMove(new MoveImpl(0, 1)); // White wins the game as it has 5 pieces in a row (1,0 to 1,2)
        expect(PieceColour.WHITE, game3.winner());
        expect(true, game3.isOver());
        expect(0, game3.getMoves().size()); // There should be left 0 moves as 9 moves minus 9 moves (5 White + 4 Black)
        


        // Test draw game in a 2x2 grid
        Game game4 = new GameImpl(2);
        game4.makeMove(new MoveImpl(0, 0)); // White
        game4.makeMove(new MoveImpl(0, 1)); // Black
        game4.makeMove(new MoveImpl(1, 1)); // White
        game4.makeMove(new MoveImpl(1, 0)); // Black
        expect(true, game4.isOver());
        expect(PieceColour.NONE, game4.winner());
        expect(0, game4.getMoves().size());

        // Test deep copy of the game
        // The original game should not be modified when the copy is modified
        Game copyGame2 = new GameImpl(5);
        copyGame2.makeMove(new MoveImpl(0, 0)); // White moves in copyGame2
        Game copyGame3 = copyGame2.copy();
        copyGame3.makeMove(new MoveImpl(0, 1)); // Black moves in copyGame3
        expect(PieceColour.NONE, copyGame2.getGrid().getPiece(0, 1)); // Original unchanged
        expect(PieceColour.BLACK, copyGame3.getGrid().getPiece(0, 1)); // Copy changed
        expect(PieceColour.WHITE, copyGame3.currentPlayer()); // Copy state updated
        expect(PieceColour.BLACK, copyGame2.currentPlayer()); // Original state unchanged
        checkAllTestsPassed();
    }
}
