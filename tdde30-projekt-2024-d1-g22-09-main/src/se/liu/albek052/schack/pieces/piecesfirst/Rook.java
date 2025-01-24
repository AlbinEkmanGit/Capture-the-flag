package se.liu.albek052.schack.pieces.piecesfirst;

import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import javax.swing.*;

/**
 * Class for rook pieces. Uses Piece and gives ImageIcon appropriate Image
 * {@link Piece}
 */
public class Rook extends FirstPiece
{
    public Rook(final ColorType color, final ImageIcon icon, final Moves moves)
    {
	super(color, icon, moves);
    }

    /**
     * Check if move is straight
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    @Override public boolean isValidMove(final int row, final int column, final int rowNew, final int columnNew) {
	return moves.moveStraight(row, column, rowNew, columnNew);
    }
}
