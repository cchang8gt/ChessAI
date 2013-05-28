package com.chess.engine;

import com.chess.ui.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Observes and validates moves performed on Game State.
 * 
 * @author Chris
 * TODO:Add castling
 * TODO:Add En Passant
 * TODO:Add Check/Checkmate mechanics
 * TODO:Add Generate Legal moves for each piece
 */
public class Chess {
    private static final long serialVerionUID = 1L;
    private static final int RED = 0;
    private static final int BLUE = 1;
    private static final boolean DEBUG = true;

    private ChessBoard chessboard;
    public ChessPiece[][] gameState;
    // HUMAN ONLY DETERMINES POSITIONING. HUMAN == BOTTOM
    public int activeColor, playerColor, aiColor, humanColor = -1;
    public ArrayList<ChessPiece> playerAlive, playerDead, aiAlive, aiDead;
    public int turnCounter = 0;

    // Color is always player's color (the one that will be on the bottom)
    public Chess(ChessBoard c, int color) {
	activeColor = RED;
	if (color == RED) {
	    humanColor = color;
	    playerColor = color;
	    aiColor = color ^ 1;

	}
	else {
	    humanColor = color;
	    playerColor = color ^ 1;
	    aiColor = color;
	}
	chessboard = c;
	gameState = new ChessPiece[8][8];
	playerAlive = new ArrayList<ChessPiece>();
	playerDead = new ArrayList<ChessPiece>();
	aiAlive = new ArrayList<ChessPiece>();
	aiDead = new ArrayList<ChessPiece>();

	playerAlive = openingSetup(humanColor);
	aiAlive = openingSetup(humanColor ^ 1);
    }

    /**
     * Opening layout of each side's pieces.
     * 
     * @param color
     * @return
     */
    private ArrayList<ChessPiece> openingSetup(int color) {
	ArrayList<ChessPiece> units = new ArrayList<ChessPiece>();
	// if we're setting up a player (red)
	if (color == humanColor) {
	    for (int i = 0; i < 8; i++) {
		units.add(new ChessPiece(ChessPiece.PAWN, color));
	    }
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.QUEEN, color));
	    units.add(new ChessPiece(ChessPiece.KING, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	}
	// if we're setting up a comp (blue)
	else {
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.QUEEN, color));
	    units.add(new ChessPiece(ChessPiece.KING, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	    for (int i = 0; i < 8; i++) {
		units.add(new ChessPiece(ChessPiece.PAWN, color));
	    }
	}

	return units;
    }

    /**
     * Validates a move, color is the color of the player making the move.
     * 
     * @param rowStart
     * @param colStart
     * @param rowEnd
     * @param colEnd
     * @param color
     * @return
     * 
     *         TODO: Add basic movement rules for each piece Add capture rules
     *         for each piece Add en passant for Pawn Add Rank change for pawn
     *         Clean up this code man!
     */
    public boolean validateMove(int rowFrom, int colFrom, int rowTo, int colTo) {
	// If moving piece belongs to active player and destination does not
	// belong to the active player
	//If the destination is not the active color AND the origin is the active color   DEBUG PURPOSE: If destination color does not match origin color
	if (/*gameState[rowTo][colTo].getColor() != activeColor && gameState[rowFrom][colFrom].getColor() == activeColor*/gameState[rowTo][colTo].getColor() != gameState[rowFrom][colFrom].getColor()) {
	    if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.PAWN)) {
		// if piece is on the bottom of the map
		if (gameState[rowFrom][colFrom].getColor() == humanColor) {
		    if (colTo == colFrom - 1 && rowTo == rowFrom - 1) {
			if (gameState[rowTo][colTo].getColor() == (activeColor ^ 1)) {
			    return true;
			}
			else {
			    return false;
			}
		    }
		    else if (colTo == colFrom + 1 && rowTo == rowFrom - 1) {
			if (gameState[rowTo][colTo].getColor() == (activeColor ^ 1)) {
			    return true;
			}
			else {
			    return false;
			}
		    }
		    // If pawn is on the same side of the map
		    if (colTo == colFrom) {
			// If Pawn is at starting location and the destination
			if (rowFrom == 6) {
			    if (gameState[rowTo][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1) && rowTo == rowFrom - 1) {
				return true;
			    }
			    else if (gameState[rowTo][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1) && rowTo == rowFrom - 2) {
				if (gameState[rowTo - 1][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1)) {
				    return true;
				}
			    }
			    else {
				return false;
			    }
			}
			else if (rowFrom < 6) {
			    if (gameState[rowTo][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1) && rowTo == rowFrom - 1) {
				return true;
			    }
			    else {
				return false;
			    }
			}
		    }
		}
		// if piece is on the top of the map
		else {
		    if (colTo == colFrom - 1 && rowTo == rowFrom + 1) {
			if (gameState[rowTo][colTo].getColor() == (activeColor ^ 1)) {
			    return true;
			}
		    }
		    else if (colTo == colFrom + 1 && rowTo == rowFrom + 1) {
			if (gameState[rowTo][colTo].getColor() == (activeColor ^ 1)) {
			    return true;
			}
		    }
		    // If pawn is on the same side of the map
		    if (colTo == colFrom) {
			// If Pawn is at starting location and the destination
			if (rowFrom == 1) {
			    if (gameState[rowTo][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1) && rowTo == rowFrom + 1) {
				return true;
			    }
			    else if (gameState[rowTo][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1) && rowTo == rowFrom + 2) {
				if (gameState[rowTo - 1][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1)) {
				    return true;
				}
			    }
			}
			else if (rowFrom > 1) {
			    if (gameState[rowTo][colTo].getColor() != activeColor && gameState[rowTo][colTo].getColor() != (activeColor ^ 1) && rowTo == rowFrom + 1) {
				return true;
			    }
			}
		    }
		}
	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.BISHOP)) {
		/*
		 * Top right: rowTo < rowFrom, colTo > colFrom Top left: rowTo <
		 * rowFrom, colTo < colFrom Bottom Left: rowTo > rowFrom, colTo
		 * < colFrom Bottom Right:rowTo > rowFrom, colTo > colFrom
		 */
		if (Math.abs(rowFrom - rowTo) == Math.abs(colFrom - colTo)) {
		    int distance = Math.abs(rowFrom - rowTo);
		    int x = 0;
		    int y = 0;
		    // top right
		    if (rowTo < rowFrom && colTo > colFrom) {
			for (int i = 1; i < distance; i++) {
			    if (gameState[rowFrom - i][colFrom + i].getColor() == activeColor || gameState[rowFrom - i][colFrom + i].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    // top left
		    else if (rowTo < rowFrom && colTo < colFrom) {
			for (int i = 1; i < distance; i++) {
			    if (gameState[rowFrom - i][colFrom - i].getColor() == activeColor || gameState[rowFrom - i][colFrom - i].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    // bottom left
		    else if (rowTo > rowFrom && colTo < colFrom) {
			for (int i = 1; i < distance; i++) {
			    if (gameState[rowFrom + i][colFrom - i].getColor() == activeColor || gameState[rowFrom + i][colFrom - i].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    // bottom right
		    else if (rowTo > rowFrom && colTo > colFrom) {
			for (int i = 1; i < distance; i++) {
			    if (gameState[rowFrom + i][colFrom + i].getColor() == activeColor || gameState[rowFrom + i][colFrom + i].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    else {
			return false;
		    }
		}
	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.KNIGHT)) {
		/*
		 * Top right: rowTo < rowFrom, colTo > colFrom 
		 * Top left: rowTo < rowFrom, colTo < colFrom 
		 * Bottom Left: rowTo > rowFrom, colTo < colFrom 
		 * Bottom Right:rowTo > rowFrom, colTo > colFrom code
		 * is way too verbose
		 */
		//top right
		if (rowTo < rowFrom && colTo > colFrom) {
		    if (Math.abs(rowTo - rowFrom) == 2 && Math.abs(colTo - colFrom) == 1) {
			return true;
		    }
		    else if (Math.abs(rowTo - rowFrom) == 1 && Math.abs(colTo - colFrom) == 2) {
			return true;
		    }
		    else {
			return false;
		    }
		}
		//top left
		else if (rowTo < rowFrom && colTo < colFrom) {
		    if (Math.abs(rowTo - rowFrom) == 2 && Math.abs(colTo - colFrom) == 1) {
			return true;
		    }
		    else if (Math.abs(rowTo - rowFrom) == 1 && Math.abs(colTo - colFrom) == 2) {
			return true;
		    }
		    else {
			return false;
		    }
		}
		else if (rowTo > rowFrom && colTo < colFrom) {
		    if (Math.abs(rowTo - rowFrom) == 2 && Math.abs(colTo - colFrom) == 1) {
			return true;
		    }
		    else if (Math.abs(rowTo - rowFrom) == 1 && Math.abs(colTo - colFrom) == 2) {
			return true;
		    }
		    else {
			return false;
		    }
		}
		else if (rowTo > rowFrom && colTo > colFrom) {
		    if (Math.abs(rowTo - rowFrom) == 2 && Math.abs(colTo - colFrom) == 1) {
			return true;
		    }
		    else if (Math.abs(rowTo - rowFrom) == 1 && Math.abs(colTo - colFrom) == 2) {
			return true;
		    }
		    else {
			return false;
		    }
		}
		else {
		    return false;
		}
	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.ROOK)) {
		// if left or right
		int distance;
		if (rowTo == rowFrom && colTo != colFrom) {
		    // left
		    if (colTo < colFrom) {
			distance = Math.abs(colTo - colFrom);
			for (int i = 1; i < distance; i++) {
			    if (gameState[rowFrom][colFrom - i].getColor() == activeColor || gameState[rowFrom][colFrom - i].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    // right
		    else if (colTo > colFrom) {
			distance = Math.abs(colTo - colFrom);
			for (int i = 1; i < distance; i++) {
			    if (gameState[rowFrom][colFrom + i].getColor() == activeColor || gameState[rowFrom][colFrom + i].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    else {
			return false;
		    }
		}
		// if up or down
		else if (colTo == colFrom && rowTo != rowFrom) {
		    // up
		    if (rowTo < rowFrom) {
			distance = Math.abs(rowTo - rowFrom);
			for (int i = 1; i < distance; i++) {
			    System.out.println("DISTANCE: " + i);
			    if (gameState[rowFrom - i][colFrom].getColor() == activeColor || gameState[rowFrom - i][colFrom].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    // down
		    else if (rowTo > rowFrom) {
			distance = Math.abs(rowTo - rowFrom);
			for (int i = 1; i < distance; i++) {
			    if (gameState[rowFrom + 1][colFrom].getColor() == activeColor || gameState[rowFrom + 1][colFrom].getColor() == (activeColor ^ 1)) {
				return false;
			    }
			}
			return true;
		    }
		    // na (shouldn't really be run, just check)
		    else {
			return false;
		    }
		}
		// if something else
		else {
		    return false;
		}
	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.QUEEN)) {
		boolean rooktest = false;
		boolean bishoptest = false;
		gameState[rowFrom][colFrom] = new ChessPiece(ChessPiece.ROOK, gameState[rowFrom][colFrom].getColor());
		rooktest = validateMove(rowFrom, colFrom, rowTo, colTo);
		gameState[rowFrom][colFrom] = new ChessPiece(ChessPiece.BISHOP, gameState[rowFrom][colFrom].getColor());
		bishoptest = validateMove(rowFrom, colFrom, rowTo, colTo);
		gameState[rowFrom][colFrom] = new ChessPiece(ChessPiece.QUEEN, gameState[rowFrom][colFrom].getColor());
		if (rooktest || bishoptest) {
		    return true;
		}
		return false;
	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.KING)) {
		if (gameState[rowTo][colTo].getColor() != gameState[rowFrom][colFrom].getColor()) {
		    if (Math.abs(rowTo - rowFrom) == 0 && Math.abs(colTo - colFrom) == 0) {
			return false;
		    }
		    if (Math.abs(rowTo - rowFrom) == 1 || Math.abs(colTo - colFrom) == 1) {
			return true;
		    }
		}
		return false;
	    }
	}
	else {
	    return false;
	}
	return false;
    }

    /**
     * Updates the positions of all pieces on the board
     */
    public void updatePositions() {
	for(int i = 0; i < gameState.length; i++) {
	    for(int j = 0; j < gameState[0].length; j++) {
		gameState[i][j].setRow(i);
		gameState[i][j].setCol(j);
	    }
	}
	
	if(DEBUG) {
	    for(int i = 0; i < gameState.length; i++) {
		for(int j = 0; j < gameState[0].length; j++) {
		    System.out.print(gameState[i][j].getColor() + " ");
		}
		System.out.println();
	    }
	}
    }
    
    /**
     * Returns true if king of the color is checked
     * 
     * @param color
     * @return
     */
    public boolean isChecked(int color) {
	if (color == playerColor) {

	}
	else {

	}
	return false;
    }

    public ArrayList<ChessPiece> getMyUnits() {
	return null;
    }
}
