package se.liu.albek052.schack.pieces;

import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import javax.swing.*;

/**
 * Class for queen pieces. Uses Piece and gives ImageIcon appropriate Image
 * {@link Piece}
 */
public class Queen extends Piece
{
    public Queen(final ColorType color, final ImageIcon icon, final Moves moves)
    {
	super(color, icon, moves);
    }

    /**
     * Check if move is diagonal or straight
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    @Override public boolean isValidMove(final int row, final int column, final int rowNew, final int columnNew) {
	boolean move = moves.moveDiagonal(row, column, rowNew, columnNew);
	move = move || moves.moveStraight(row, column, rowNew, columnNew);
	return move;
    }
}
