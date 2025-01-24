package se.liu.albek052.schack.pieces.piecesfirst;

import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import se.liu.albek052.schack.pieces.Piece;
import javax.swing.*;

/**
 * Class for pawn pieces. Uses Piece and gives ImageIcon appropriate Image.
 * Extra field after moving two squares.
 * {@link Piece}
 */
public class Pawn extends FirstPiece
{
    private boolean twoSquare = false;
    public Pawn(final ColorType color, final ImageIcon icon, final Moves moves)
    {
	super(color, icon, moves);
    }

    //public boolean isTwoSquare() {
	//return twoSquare;
    //}
    public void setTwoSquare(final boolean twoSquare) {
	this.twoSquare = twoSquare;
    }

    /**
     * Call on moves to check if moved straight, attack or passant
     * @see Moves#movePawn(Piece, int, int, int, int)
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    @Override public boolean isValidMove(final int row, final int column, final int rowNew, final int columnNew) {
	return moves.movePawn(this, row, column, rowNew, columnNew);
    }

    /**
     * Set twoSquare to appropriate value for passant
     */
    @Override public void setFirstMove(boolean firstMove) {
	twoSquare = twoSquare && this.firstMove;
	super.setFirstMove(false);
    }

    @Override public boolean getFirstMove() {
	if (!firstMove)
	    return twoSquare;
	return super.getFirstMove();
    }
}
