package se.liu.albek052.schack.pieces;

import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import javax.swing.*;

/**
 * Class for knight pieces. Uses Piece and gives ImageIcon appropriate Image
 * {@link Piece}
 */
public class Knight extends Piece
{
    public Knight(final ColorType color, final ImageIcon icon, final Moves moves)
    {
	super(color, icon, moves);
    }

    /**
     * Look for valid move.
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    @Override public boolean isValidMove(final int row, final int column, final int rowNew, final int columnNew) {
	return ((Math.abs(row - rowNew) == 2 &&
		 Math.abs(column - columnNew) == 1) ||
		(Math.abs(row - rowNew) == 1 &&
		 Math.abs(column - columnNew) == 2));
    }

    /**
     * Don't need to look for path clear
     * @param piece
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    @Override public boolean isPathClear(final Piece piece, final int row, final int column, final int rowNew, final int columnNew) {
	return moves.isSquareOccupy(piece, rowNew, columnNew);
    }
}
