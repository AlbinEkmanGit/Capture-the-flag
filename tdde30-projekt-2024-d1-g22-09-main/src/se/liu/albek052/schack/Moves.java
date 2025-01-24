package se.liu.albek052.schack;

import se.liu.albek052.schack.pieces.piecesfirst.Pawn;
import se.liu.albek052.schack.pieces.Queen;
import se.liu.albek052.schack.pieces.Piece;

import javax.swing.*;

/**
 * Contains functions for checking every type of move a piece would want to make
 * each piece calls on specific functions depending on which piece calls
 */
public class Moves
{
    private Board board;

    public Moves(final Board board) {
	this.board = board;
    }

    /**
     * Checks if two sqares are on a straight line
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public boolean moveOne(final int row, final int column, final int rowNew, final int columnNew){
	return ((Math.abs(rowNew - row) <= 1) && (Math.abs(columnNew - column) <= 1));
    }

    /**
     * Checks if two sqares are on a diagonal line
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public boolean moveDiagonal(final int row, final int column, final int rowNew, final int columnNew){
	return (Math.abs(rowNew - row) == Math.abs(columnNew - column));
    }

    /**
     * Checks if two sqares are next to eachother (diagonal and straight)
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public boolean moveStraight(final int row, final int column, final int rowNew, final int columnNew){
	return ((rowNew == row) || (column == columnNew));
    }

    /////////////////////////////////////////////////////////////////
    // KING, start
    ///////////////////////////////////////////////////////////////
    /**
     * Checks if the king can make the requested move (castle or move one square)
     * @param piece
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public boolean moveKing(Piece piece, final int row, final int column, final int rowNew, final int columnNew){
	if(moveOne(row, column, rowNew, columnNew)){
	    return true;
	}
	else return isKingCastle(piece, row, column, rowNew, columnNew);
    }

    /**
     * Check if the king can make the requested castle
     * @param piece
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    private boolean isKingCastle(Piece piece, int row, int column, int rowNew, int columnNew){
	// Rook row positions
	int checkRow = rowNew==2 ? 0 : board.getWidth()-1;
	// Check clear position
	int checkRowTest = rowNew==2 ? 3 : 5;
	if (isRookCastle(piece, checkRow, columnNew, checkRowTest, columnNew) && Math.abs(rowNew - row) == 2 && column == columnNew) {
	    // Valid castle
	    return true;
	}
	//System.out.println("Invalid castle");
	return false;
    }

    /**
     * Check if the rook can make the requested castle and path clear
     * between both pieces
     * @param piece
     * @param row
     * @param column
     * @param rowTest
     * @param columnTest
     * @return
     */
    private boolean isRookCastle(Piece piece, int row, int column, int rowTest, int columnTest){
	// Check for piece
	if (piece.getFirstMove() && board.getPiece(row, column) != null){
	    Piece possibleRook = board.getPiece(row, column);
	    // Check path and previous move, if true = rook
	    if (possibleRook.getFirstMove() && isPathClear(piece, row, column, rowTest, columnTest)) {
		// Rook confirmed and castle move valid
		board.movePiece(row, column, rowTest, column);
		return true;
	    }
	}
	return false;
    }

    /////////////////////////////////////////////////////////////////
    // KING, end
    ///////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////
    // Pawn, start
    ///////////////////////////////////////////////////////////////

    /**
     * Checks if a pawn can make the requested move and if it is promoted
     * @param piece
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public boolean movePawn(Piece piece, final int row, final int column, final int rowNew, final int columnNew){
	Pawn pawn = (Pawn) piece;
	// Move one or two squares
	int moveLength = pawn.getFirstMove() ? 2 : 1;
	boolean valid = ((rowNew == row) && (Math.abs(columnNew - column) <= moveLength)) && board.getPiece(rowNew, columnNew) == null;


	// Check direction
	if ((columnNew < column && pawn.getColor() == ColorType.WHITE) || (columnNew > column && pawn.getColor() == ColorType.BLACK)) {
	    // Check diagonal moves
	    if (!valid && isPawnDiagonal(row, column, rowNew, columnNew)) {
		valid = true;
	    }
	    // Know if pawn moved one or two squares
	    if (valid) {
		pawn.setTwoSquare(pawn.getFirstMove() && Math.abs(columnNew - column) != 1);
	    }
	    // Promotion
	    if ((columnNew == board.getHeight()-1 || columnNew == 0) && valid) {
		if (piece.getColor() == ColorType.WHITE)
		    board.setPieces(new Queen(ColorType.WHITE, new ImageIcon(ClassLoader.getSystemResource("images/queen.png")), this), row, column);
		else
		    board.setPieces(new Queen(ColorType.BLACK, new ImageIcon(ClassLoader.getSystemResource("images/queen1.png")), this), row, column);
	    }
	    return valid;
	}
	return false;
    }

    /**
     * Used by movePawn to check the diagonal moves a pawn can make
     * which is an attack or passant
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    private boolean isPawnDiagonal(int row, int column, int rowNew, int columnNew){
	// Check for attack
	if (Math.abs(rowNew - row) == 1 && Math.abs(columnNew - column) == 1 && (board.getPiece(rowNew, columnNew) != null)) {
	    return true;
	}
	// Passant
	else if(rowNew != row) {
	    int rowTest = (int) (Math.signum(rowNew - row)) + row;
	    // Check for piece
	    if (board.getPiece(rowTest, column) != null) {
		// Check if Pawn
		//if (board.getPiece(rowTest, column).getPieceType() == PieceType.PAWN) {
		Piece piece = board.getPiece(rowTest, column);
		// Check if moved 2 and for enemies
		if (piece.getFirstMove() && isSquareOccupy(board.getPiece(row, column), rowTest, column) && column != 6 && column != 1) {
		    // Prevent pawn from moving too far
		    if (moveOne(row, column, rowNew, columnNew) && (rowTest) == rowNew) {
			board.setPieces(null, rowTest, column);
			return true;
		    }
		}
	    }
	}
	return false;
    }

    /////////////////////////////////////////////////////////////////
    // Pawn, end
    ///////////////////////////////////////////////////////////////

    /**
     * Checks if there are pieces between two points on the board and
     * if the target square can be occupied (no ally)
     * @param piece
     * @param row
     * @param column
     * @param rowNew
     * @param columnNew
     * @return
     */
    public boolean isPathClear(Piece piece, int row, int column, final int rowNew, final int columnNew){
	while (true) {
	    // Check direction
	    double rowDirection = rowNew - row;
	    double columnDirection = columnNew - column;
	    // Check square or move along
	    column += (int) (Math.signum(columnDirection));
	    row += (int) (Math.signum(rowDirection));
	    // Check if done
	    if (row == rowNew && column == columnNew) {
		return isSquareOccupy(piece, rowNew, columnNew);
		//	Check for other piece
	    } else if (board.getPiece(row, column) != null) {
		return false;
	    }
	}
    }

    /**
     * Checks if it is an enemy, allied or empty square
     * and prevents allied pieces from standing on same square
     * @param piece
     * @param row
     * @param column
     * @return
     */
    public boolean isSquareOccupy(Piece piece, int row, int column){
	Piece otherPiece = board.getPiece(row, column);
	// Allow if pieces are different colors or if square not occupied
	if ((otherPiece != null && otherPiece.getColor() != piece.getColor()) || otherPiece == null){
	    return true;
	}
	else return false;
    }
}
