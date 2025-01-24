package se.liu.albek052.schack.pieces.piecesfirst;

import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import se.liu.albek052.schack.pieces.Piece;
import javax.swing.*;

/**
 * Allow FirstPiece-pieces to use the firstMove variable
 */
public abstract class FirstPiece extends Piece
{
    protected boolean firstMove = true;
    protected FirstPiece(final ColorType color, final ImageIcon icon, final Moves moves) {
	super(color, icon, moves);
    }

    @Override public boolean getFirstMove() {
	return firstMove;
    }

    @Override public void setFirstMove(final boolean firstMove) {
	this.firstMove = firstMove;
    }
}
