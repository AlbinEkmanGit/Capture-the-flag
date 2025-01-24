package se.liu.albek052.schack.pieces;

import se.liu.albek052.schack.ChessComponent;
import se.liu.albek052.schack.ColorType;
import se.liu.albek052.schack.Moves;
import javax.swing.*;
import java.awt.*;

/**
 * Abstract superclass for pieces. Given unique pieceType and Image icon
 * form subclasses
 */
public abstract class Piece
{
    protected ColorType color;
    protected ImageIcon icon;
    protected Moves moves;
    protected boolean doOnce = true;


    protected Piece(final ColorType color, final ImageIcon icon, Moves moves) {
	this.color = color;
	this.icon = icon;
	this.moves = moves;
    }

    public ColorType getColor() {
	return color;
    }

    public boolean getFirstMove() {
	return false;
    }

    public void setFirstMove(boolean firstMove) {
	assert true;
    }

    /**
     * Used by subclasses to tell board if they can make the given move
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public abstract boolean isValidMove(final int row, final int column, final int rowNew, final int columnNew);

    /**
     * Draws the piece on the board
     * @param c
     * @param g
     * @param row
     * @param column
     * @param squareSize
     */
    public void draw(ChessComponent c, final Graphics g, final int row, final int column, final int squareSize){
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	if (doOnce)
	    resize(squareSize );
	icon.paintIcon(c, g, row * squareSize, column * squareSize);
    }

    /**
     * Called once to resize the piece to the size of the board
     * @param c
     * @param g
     * @param row
     * @param column
     * @param squareSize
     */
    public void resize(final int squareSize){
	if (squareSize != 0) {
	    Image image = icon.getImage(); // transform it
	    Image newImg = image.getScaledInstance(squareSize, squareSize,  Image.SCALE_SMOOTH); // scale it the smooth way
	    icon = new ImageIcon(newImg);  // transform it back
	    doOnce = false;
	}
    }

    /**
     * Calls path clear from Moves
     * @see Moves#pathClear(Piece, int, int, int, int) 
     * @param piece
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public boolean isPathClear(Piece piece, int row, int column, final int rowNew, final int columnNew) {
    	return moves.isPathClear(piece, row, column, rowNew, columnNew);
    }
}
