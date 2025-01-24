package se.liu.albek052.schack.pieces;

import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import javax.swing.*;

/**
 * Class for bishop pieces. Uses Piece and gives ImageIcon appropriate Image
 *  {@link Piece}
 */
public class Bishop extends Piece
{
    public Bishop(final ColorType color, final ImageIcon icon, final Moves moves)
    {
	super(color, icon, moves);
    }

    @Override public boolean isValidMove(final int row, final int column, final int rowNew, final int columnNew) {
	return moves.moveDiagonal(row, column, rowNew, columnNew);
    }
}
