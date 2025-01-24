package se.liu.albek052.schack;

import javax.swing.*;
import java.awt.*;

/**
 * Responsible for drawing the game and updating the screen when
 */
public class ChessComponent extends JComponent implements BoardListener
{
    private Board board;
    private boolean timerStatus;

    public ChessComponent(final Board board, boolean timerStatus) {
	this.board = board;
	this.timerStatus = timerStatus;
    }

    @Override public void boardChanged(){
	repaint();
    }

    @Override public Dimension getPreferredSize(){
	Dimension dimension = new Dimension(1600, 700 );
	//Dimension dimension = getToolkit().getScreenSize();
	return dimension;
    }

    /**
     * Tells board, player(timer) and pieces to draw themselves.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override protected  void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	int squareSize = getPreferredSize().height/10;
	board.draw(g2d, squareSize, g, this);
	for (Players players: board.getPlayers())
		players.draw(squareSize, g, this, timerStatus);
    }
}
