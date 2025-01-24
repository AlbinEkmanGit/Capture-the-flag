package se.liu.albek052.schack;

/**
 * Start the game by creating Chessviewer and Board
 * {@link ChessViewer}
 * {@link Board}
 */
public class Tester
{
    public static void main(String[] args) {
	Board board = new Board();
	ChessViewer viewer = new ChessViewer(board);
	viewer.screenShow();
    }
}
