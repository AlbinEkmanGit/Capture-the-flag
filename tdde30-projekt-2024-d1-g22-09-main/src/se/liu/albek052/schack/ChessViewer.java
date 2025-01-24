package se.liu.albek052.schack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates the window the game is played on using JFrame
 * {@link JFrame}
 */
public class ChessViewer
{
    private Board board;
    private boolean timerB = false;

    public ChessViewer(final Board board) {
	this.board = board;
    }
    public void screenShow(){
	JFrame frame = new JFrame("Chess");

	// Visual for manu
	final JMenuBar menuBar = new JMenuBar();
	final JMenuItem button = new JMenuItem("Exit game");
	button.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		int answer = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?");
		if (answer == JOptionPane.YES_OPTION)
		    System.exit(0);
	    }
	});
	menuBar.add(button);

	// Ask if timer
	int answer = JOptionPane.showConfirmDialog(frame, "Do you want timer?");
	if (answer == JOptionPane.YES_OPTION)
	    timerB = true;

	/*
	final JMenu help = new JMenu("Settings");
	help.add(new JMenuItem("About WordProcessor 1.0"));
	menuBar.add(Box.createHorizontalGlue()); // "Glue" fungerar som en fjäder, trycker "help" till höger
	menuBar.add(help);

	 */

	// Visual for game
	/*
	frame.setSize(component.getPreferredSize());
	frame.add(component, BorderLayout.CENTER);
	frame.addKeyListener(new WordProcessor20(board));
	frame.setJMenuBar(menuBar);
	frame.setVisible(true);

	 */


	ChessComponent component = new ChessComponent(board, timerB);
	board.addBoardListener(component);
	System.out.println("Hello");
	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		// Gå ett steg i spelet!
		board.tick(timerB);
	    }
	};

	final Timer clockTimer = new Timer(1000, doOneStep); // 1 sec
	clockTimer.setCoalesce(true);
	clockTimer.start();

	frame.setSize(component.getPreferredSize());
	frame.add(component, BorderLayout.CENTER);
	frame.addMouseListener(new GameMouseListener(board, component.getPreferredSize()));
	frame.setJMenuBar(menuBar);
	frame.setVisible(true);
	//this.show(component);
    }
}
