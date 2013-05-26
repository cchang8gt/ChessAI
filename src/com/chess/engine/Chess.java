package com.chess.engine;

import com.chess.ui.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Observes and validates moves performed on Game State.
 * 
 * @author Chris
 * 
 */
public class Chess {
    private static final long serialVerionUID = 1L;
    private static final int RED = 0;
    private static final int BLUE = 1;
    
    private ChessBoard chessboard;
    public ChessPiece[][] gameState;
    
    public int activeColor, playerColor, aiColor, humanColor = -1;
    public ArrayList<ChessPiece> playerAlive, playerDead, aiAlive, aiDead;
    public int turnCounter = 0;
 
    //Color is always player's color (the one that will be on the bottom)
    public Chess(ChessBoard c, int color) {
	activeColor = RED;
	if(color == RED) {
	    humanColor = color;
	    playerColor = color;
	    aiColor = color^1;
	    
	}
	else {
	    humanColor = color;
	    playerColor = color^1;
	    aiColor = color;
	}
	chessboard = c;
	gameState = new ChessPiece[8][8];
	playerAlive = new ArrayList<ChessPiece>();
	playerDead = new ArrayList<ChessPiece>();
	aiAlive = new ArrayList<ChessPiece>();
	aiDead = new ArrayList<ChessPiece>();
	
	playerAlive = openingSetup(humanColor);
	aiAlive = openingSetup(humanColor^1);
    }

    /**
     * Opening layout of each side's pieces.
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
     */
    public boolean validateMove(int rowFrom, int colFrom, int rowTo, int colTo, int color) {
	// If moving piece belongs to active player and destination does not
	// belong to the active player
	if (gameState[rowTo][colTo].getColor() != color && gameState[rowFrom][colFrom].getColor() == color) {

	    if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.PAWN)) {

	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.BISHOP)) {

	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.KNIGHT)) {

	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.ROOK)) {

	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.QUEEN)) {

	    }
	    else if (gameState[rowFrom][colFrom].getArchetype().equals(ChessPiece.KING)) {

	    }
	}
	else {
	    System.out.println("Illegal Move");
	}
	return true;
    }

    public ArrayList<ChessPiece> getMyUnits() {
	return null;
    }
}
