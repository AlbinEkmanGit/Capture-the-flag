package se.liu.albek052.schack.pieces.piecesfirst;

import se.liu.albek052.schack.Board;
import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import se.liu.albek052.schack.pieces.Piece;

import javax.swing.*;

/**
 * Class for king pieces. Uses Piece and gives ImageIcon appropriate Image
 * {@link Piece}
 */
public class King extends FirstPiece
{
    private Board board;
    public King(final ColorType color, final ImageIcon icon, final Moves moves, final Board board)
    {
	super(color, icon, moves);
	this.board = board;
    }

    /**
     * Check if king has moved one square in any direction or castled
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    @Override public boolean isValidMove(final int row, final int column, final int rowNew, final int columnNew) {
	return moves.moveKing(this, row, column, rowNew, columnNew);
    }

    @Override public void resize(final int squareSize) {
	if(!doOnce){
	    board.setGameOver(true);
	}
	super.resize(squareSize);
    }
}
