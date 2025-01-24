package se.liu.albek052.schack;

import se.liu.albek052.schack.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the time and defeated pieces for each player. The ColorType
 * field is used to know the color of the player
 */
public class Players
{
    private ColorType colorType;
    private GameTimer timer = new GameTimer(15, 0);
    private List<Piece> defeatedPieces = new ArrayList<>();

    public Players(ColorType colorType) {
	this.colorType = colorType;
    }

    /*
    public ColorType getColorType() {
	return colorType;
    }

     */

    public boolean countDown(){
	return timer.isTimerDone();
    }

    public void addDefeated(Piece piece){
	defeatedPieces.add(piece);
    }

    /**
     * Makes the player draw the timer and defeated pieces
     * @param g2d
     * @param squareSize
     * @param g
     * @param component
     * @param timerStatus
     */
    public void draw(int squareSize, Graphics g, ChessComponent component, boolean timerStatus){
	// Draw timer if setting allow
	if (timerStatus) {
	    int height = ColorType.BLACK == colorType ? squareSize * 2 : squareSize * 7;
	    g.setColor(Color.BLACK);
	    g.setFont(new Font("serif", Font.BOLD, 40));
	    g.drawString(timer.getMinute() + "." + timer.getSecond(), squareSize * 8, height);
	}
	// Draw defeated
	for (int i = 0; i < defeatedPieces.size(); i++) {
	    int y = defeatedPieces.get(i).getColor() == ColorType.BLACK ? 7 : 0;
	    defeatedPieces.get(i).draw(component, g, 8 * 2 + i, y * 2, squareSize / 2);
	}

    }
}
