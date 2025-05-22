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

        // Initialize the game with a 5x5 grid
        Game game = new GameImpl(5);
        expect(PieceColour.WHITE, game.currentPlayer()); // White starts first
        expect(25, game.getMoves().size()); // all positions on the 5x5 grid are available
        expect(false, game.isOver()); // game not over at the beginning
        expect(PieceColour.NONE, game.winner()); // no winner at the beginning
        expect(5, game.getGrid().getSize()); // the grid size should be 5

        // Test deep copy
        Grid gridCopy = game.getGrid();
        gridCopy.setPiece(0, 0, PieceColour.BLACK); // Modify copy
        expect(PieceColour.BLACK, gridCopy.getPiece(0, 0)); // The gridCopy is modified
        expect(PieceColour.NONE, game.getGrid().getPiece(0, 0)); // The original one is unchanged

        // Test makeMove and state updates
        game.makeMove(new MoveImpl(0, 0)); // White moves at (0, 0)
        expect(PieceColour.BLACK, game.currentPlayer()); // the current player should be black
        expect(PieceColour.WHITE, game.getGrid().getPiece(0, 0)); // White piece placed at (0, 0)
        expect(24, game.getMoves().size()); // There should be 24 moves left


        // Test some invalid moves
        // Test whether it throws an exception when the move is null
        caught = false;
        try {
            game.makeMove(null); 
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);
        // When the move is out of bounds or occupied
        caught = false;
        try {
            game.makeMove(new MoveImpl(-1, 0)); // out of bounds
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);
        caught = false;
        try {
            game.makeMove(new MoveImpl(0, -1)); // out of bounds
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);
        caught = false;
        try {
            game.makeMove(new MoveImpl(0, 0)); // already occupied
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);


        // Test White winning (left to right)
        game.makeMove(new MoveImpl(4, 0)); // Black
        game.makeMove(new MoveImpl(0, 1)); // White
        expect(PieceColour.NONE, game.winner()); // No winner right now
        game.makeMove(new MoveImpl(4, 1)); // Black
        game.makeMove(new MoveImpl(0, 2)); // White
        game.makeMove(new MoveImpl(4, 2)); // Black
        game.makeMove(new MoveImpl(0, 3)); // White
        game.makeMove(new MoveImpl(4, 3)); // Black
        game.makeMove(new MoveImpl(0, 4)); // White wins the game
        expect(PieceColour.WHITE, game.winner()); //Winner should be White
        expect(true, game.isOver()); // the game is over
        expect(16, game.getMoves().size()); // There should be left 16 moves(25-9)
        // Test whether the game is truly over and behaivior of copy
        PieceColour whiteWinner = game.winner();
        Game copyGame1 = game.copy();
        game.makeMove(new MoveImpl(4, 4)); // Let the Black connect 5 pieces
        expect(whiteWinner, game.winner()); // The winner should still be White
        expect(PieceColour.NONE, game.getGrid().getPiece(4, 4)); // The original one is unchanged
        copyGame1.makeMove(new MoveImpl(1, 0)); // Modify copy
        expect(PieceColour.WHITE, game.winner()); // Original unchanged
        expect(PieceColour.WHITE, copyGame1.winner()); // Copy winner should be the same
        expect(true, copyGame1.isOver()); // Copy should be over

        // Test Black winning (top to bottom)
        Game game2 = new GameImpl(5);
        expect(PieceColour.WHITE, game2.currentPlayer()); // White starts first
        game2.makeMove(new MoveImpl(0, 0)); // White 
        game2.makeMove(new MoveImpl(0, 4)); // Black
        game2.makeMove(new MoveImpl(0, 1)); // White
        game2.makeMove(new MoveImpl(1, 4)); // Black
        game2.makeMove(new MoveImpl(0, 2)); // White
        game2.makeMove(new MoveImpl(2, 4)); // Black
        game2.makeMove(new MoveImpl(0, 3)); // White
        game2.makeMove(new MoveImpl(3, 4)); // Black
        game2.makeMove(new MoveImpl(3, 3)); // White
        game2.makeMove(new MoveImpl(4, 4)); // Black wins the game (0,4 to 4,4)
        expect(PieceColour.BLACK, game2.winner());
        expect(true, game2.isOver());
        expect(15, game2.getMoves().size()); // There should be left 15 moves (25-10)
        // Test whether the game is truly over
        PieceColour blackWinner = game2.winner();
        game2.makeMove(new MoveImpl(0, 4)); // This move should not generate another winner
        expect(blackWinner, game2.winner());

        // Test game on a 3x3 grid
        // The author plays this with his friend
        // SO this is a real recorded game
        // Of course, the author wins
        Game game3 = new GameImpl(3);
        expect(PieceColour.WHITE, game3.currentPlayer()); // Test whether White starts first
        game3.makeMove(new MoveImpl(1, 1)); // White
        game3.makeMove(new MoveImpl(0, 0)); // Black
        game3.makeMove(new MoveImpl(2, 0)); // White
        game3.makeMove(new MoveImpl(1, 0)); // Black
        game3.makeMove(new MoveImpl(2, 2)); // White
        game3.makeMove(new MoveImpl(2, 1)); // Black
        game3.makeMove(new MoveImpl(1, 2)); // White
        game3.makeMove(new MoveImpl(0, 2)); // Black
        game3.makeMove(new MoveImpl(0, 1)); // White wins the game
        expect(true, game3.isOver());
        expect(PieceColour.WHITE, game3.winner());
        expect(0, game3.getMoves().size()); // There should be left 0 moves(5 White + 4 Black)
        


        // Test draw game on a 2x2 grid
        Game game4 = new GameImpl(2);
        expect(PieceColour.WHITE, game4.currentPlayer()); // Test whether White starts first
        game4.makeMove(new MoveImpl(0, 0)); // White
        game4.makeMove(new MoveImpl(0, 1)); // Black
        game4.makeMove(new MoveImpl(1, 1)); // White
        game4.makeMove(new MoveImpl(1, 0)); // Black
        expect(true, game4.isOver()); 
        expect(PieceColour.NONE, game4.winner());
        expect(0, game4.getMoves().size()); // There should be left 0 moves (2 White + 2 Black)

        // Test 1x1 grid
        Game game5 = new GameImpl(1);
        expect(PieceColour.WHITE, game5.currentPlayer()); // Test whether White starts first
        game5.makeMove(new MoveImpl(0, 0)); // White
        expect(true, game5.isOver());
        expect(PieceColour.WHITE, game5.winner());
        expect(0, game5.getMoves().size()); // There should be left 0 moves (1 White)

        // Test deep copy of the game
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
