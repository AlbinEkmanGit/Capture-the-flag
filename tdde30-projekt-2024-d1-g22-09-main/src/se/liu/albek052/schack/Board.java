package se.liu.albek052.schack;

import se.liu.albek052.schack.pieces.*;
import se.liu.albek052.schack.pieces.piecesfirst.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Is used to store all the pieces and their position on the board
 * in addition to functions that move the pieces.
 */
public class Board
{
    private boolean gameOver = false;
    private ColorType turn = ColorType.WHITE;
    private Players[] players;
    private Piece[][] pieces;
    private Players activePlayer;
    private Piece lastMoved = null;
    private int width = 8;
    private int height = 8;
    private Point selectedPiece = null;
    private List<BoardListener> boardListeners = new ArrayList<>();
    private boolean moveInitiated = false;

    public Board() {
	pieces = new Piece[width][height];
	players = new Players[2];
	players[1] = new Players(ColorType.BLACK);
	players[0] = new Players(ColorType.WHITE);
	activePlayer = players[0];
	createPieces();
    }

    public int getWidth() {
	return width;
    }
    public int getHeight() {
	return height;
    }
    public Piece getPiece(int row, int column){
	return pieces[row][column];
    }
    public Players[] getPlayers() {
	return players;
    }
    public void setGameOver(final boolean gameOver) {
	this.gameOver = gameOver;
    }

    public void setPieces(Piece piece, int row, int column) {
	if(moveInitiated) {
	    pieces[row][column] = piece;
	    notifyListeners();
	}
    }

    /**
     * Saves the previously moved piece
     * Used for Pawn to allow passant for only one turn
     * @param lastMoved
     */
    private void setLastMoved(final Piece lastMoved) {
	if(this.lastMoved != null) {
	    this.lastMoved.setFirstMove(false); // set twoSquare to false if pawn
	}
	this.lastMoved = lastMoved;
    }

    public void addBoardListener(BoardListener bl){
	boardListeners.add(bl);
    }

    private void notifyListeners(){
	for(BoardListener bl: boardListeners){
	    bl.boardChanged();
	}
    }

    /**
     * Update clk if option si picked
     * @param timerB
     */
    public void tick(boolean timerB){
	if (timerB && lastMoved != null) {
	    if (!gameOver) {
		gameOver = getActivePlayer().countDown();
	    }
	}
	notifyListeners();
    }

    /**
     * Returns the active player form the list players
     * @return
     */
    private Players getActivePlayer(){
	for (Players player: players) {
	    //if (player.getColorType() == turn){
	    if (player.equals(activePlayer)) {
		return player;
	    }
	}
	return null;
    }

    /**
     * Saves a piece to seletedPiece-field or moves seletedPiece to another square
     * by caling on validMove-function in selectedPiece if the path is clear
     * @param rowNew
     * @param columnNew
     */
    public void initiateMove(int rowNew, int columnNew) {
	// Game Over
	if (!gameOver) {
	    // Select piece
	    if (selectedPiece == null && getPiece(rowNew, columnNew) != null) {
		if (turn == (pieces[rowNew][columnNew]).getColor()) {
		    // Save selected piece
		    selectedPiece = new Point(rowNew, columnNew);
		    System.out.println("Piece selected");
		    notifyListeners();
		} else {
		    System.out.println("Opponents turn");
		}
	    } else if (selectedPiece != null) {
		//////////////////
		int row = (int) selectedPiece.getX();
		int column = (int) selectedPiece.getY();
		Piece piece = pieces[row][column];
		////////////////////
		if (piece.isPathClear(piece, row, column, rowNew, columnNew)) {
		    // Prevent draw from affecting board
		    moveInitiated = true;
		    //if (moves.checkMove(piece, row, column, rowNew, columnNew)) {
		    if (piece.isValidMove(row, column, rowNew, columnNew)){
			movePiece(row, column, rowNew, columnNew);
			notifyListeners();
		    } else {
			System.out.println("Invalid move");
		    }
		} else {
		    System.out.println("Path blocked");
		}
		// Reset
		moveInitiated = false;
		selectedPiece = null;
	    } else {
		System.out.println("Pick a piece");
	    }
	}
	else
	    System.out.println("Game Over");
    }

    /**
     * Takes a piece and moves it to a new square and
     * also tells FirstPiece-pieces when they have made their first move and
     * lastly tells the program when it's game over.
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     */
    public void movePiece(int row, int column, int rowNew, int columnNew){
	Piece piece = pieces[row][column];
	if (moveInitiated) {
	    moveInitiated = false;
	    piece.setFirstMove(false);
	    ///////////////////////////////////////////////////////////////
	    // Swap turn
	    switchTurn(piece.getColor());
	    // Move piece
	    pieces[row][column] = null;
	    // Save ////////////////////
	    Piece pieceCheck = pieces[rowNew][columnNew];
	    // Add to deafeated
	    if (pieceCheck != null) {
		getActivePlayer().addDefeated(pieceCheck);
	    }
	    ///////////////////////////
	    pieces[rowNew][columnNew] = piece;
	    System.out.println("////////////////////////////////////");
	    setLastMoved(piece);
	    notifyListeners();
	    // Check game over
	    /*
	    if (pieceCheck != null && pieceCheck.getPieceType() == PieceType.KING) {
		gameOver = true;
	    	System.out.println("Game Over");
	    }
	     */
	    if (pieceCheck != null)
	    	pieceCheck.resize(0);
	}
    }

    /**
     * Change turn for players
     * @param turn
     */
    private void switchTurn(ColorType turn){
	for (Players player: players) {
	    if (!player.equals(activePlayer)) {
		activePlayer = player;
		break;
	    }
	}
	this.turn = turn == ColorType.BLACK ? ColorType.WHITE : ColorType.BLACK;
    }

    /**
     * Draws the board and all the pieces on it in appropriet square
     * and also changes the color for viable squares to move to.
     * @param g2d
     * @param squareSize
     * @param g
     * @param component
     */
    public void draw(Graphics2D g2d, int squareSize, Graphics g, ChessComponent component){
	int paint = 1;
	for (int y = 0; y < height; y++) {
	    paint++;
	    for (int x = 0; x < width; x++) {
		// Paint regular
		if (paint % 2 == 1) {
		    g2d.setColor(Color.black);
		} else
		    g2d.setColor(Color.white);
		// Paint green on valid
		if (selectedPiece != null){
		    ///////////////////////////
		    int row = (int) selectedPiece.getX();
		    int column = (int) selectedPiece.getY();
		    Piece pieceTest = pieces[row][column];
		    ///////////////////////////
		    // Painting
		    if (pieceTest.isPathClear(pieceTest, row, column, x, y)) {
			//if (moves.checkMove(pieceTest, row, column, x, y)) {
			if (pieceTest.isValidMove(row, column, x, y)){
			    if (paint % 2 == 1) {
				// Dark green
				g2d.setColor(Color.getHSBColor(0.3f, 1.0f, 0.3f));
			    } else
				g2d.setColor(Color.green);
			}
		    }
		}
		g2d.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
		// Paint piece
		Piece piece = pieces[x][y];
		if (piece != null) {
		    piece.draw(component, g, x, y, squareSize);
		}
		paint++;
	    }
	}
	notifyListeners();
    }

    /**
     * Creates all the pieces and places them on the board
     */
    private void createPieces(){
	Moves moves = new Moves(this);
	// Create pieces
	// Black
	// Rook
	pieces[0][0] = new Rook(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/rook1.png")), moves);
	pieces[7][0] = new Rook(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/rook1.png")), moves);
	// Knight
	pieces[1][0] = new Knight(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/knight1.png")), moves);
	pieces[6][0] = new Knight(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/knight1.png")), moves);
	// Bishop
	pieces[2][0] = new Bishop(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/bishop1.png")), moves);
	pieces[5][0] = new Bishop(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/bishop1.png")), moves);
	// Queen
	pieces[3][0] = new Queen(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/queen1.png")), moves);
	// King
	pieces[4][0] = new King(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/king1.png")), moves, this);
	// Pawns
	for (int i = 0; i < width; i++){
	    pieces[i][1] = new Pawn(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/pawn1.png")), moves);
	}
	// White
	// Rook
	pieces[0][7] = new Rook(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/rook.png")), moves);
	pieces[7][7] = new Rook(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/rook.png")), moves);
	// Knight
	pieces[1][7] = new Knight(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/knight.png")), moves);
	pieces[6][7] = new Knight(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/knight.png")), moves);
	// Bishop
	pieces[2][7] = new Bishop(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/bishop.png")), moves);
	pieces[5][7] = new Bishop(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/bishop.png")), moves);
	// Queen
	pieces[3][7] = new Queen(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/queen.png")), moves);
	// King
	pieces[4][7] = new King(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/king.png")), moves, this);
	// Pawns
	for (int i = 0; i < width; i++){
	    pieces[i][6] = new Pawn(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/pawn.png")), moves);
	}
    }
}

