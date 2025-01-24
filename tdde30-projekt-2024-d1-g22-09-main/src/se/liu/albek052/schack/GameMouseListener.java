package se.liu.albek052.schack;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listens to the mouse using MouseAdapter to get the x and y position of the mouse
 * {@link MouseAdapter}
 */
public class GameMouseListener extends MouseAdapter
{
    private Board board;
    private Dimension dimension;

    protected GameMouseListener(final Board board, final Dimension dimension) {
	this.board = board;
        this.dimension = dimension;
    }

    /**
     * Finds the x, y coordinates when mouse pressed and calls board.initiateMove
     * @param e the event to be processed
     */
    @Override public void mousePressed(MouseEvent e) {
        int posX = e.getX();
        int posY = e.getY();
        int squareSize = dimension.height/10;
        //System.out.println((posX/squareSize) + " x, " + ((posY-50)/squareSize) + " y");
        //System.out.println((posX) + " x, " + (posY-50) + " y");
        int squareX = (posX/squareSize);
        int squareY = ((posY-50)/squareSize);
        if ((squareY < 8 && squareY >= 0) && (squareX < 8 && squareX >= 0))
            board.initiateMove(squareX, squareY);
	/*int key = e.getKeyCode();
	if (key == KeyEvent.){

	}
	else if (key == KeyEvent.VK_RIGHT) {

	}
	else if (key == KeyEvent.VK_DOWN) {

	}
	else if (key == KeyEvent.VK_UP) {

	}
	 */
    }
}
