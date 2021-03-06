package com.chess.engine;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Represents a chess piece.
 * Characterized by Color and Rank.
 * Includes empty board space
 * @author qob
 *
 */
public class ChessPiece {
    //Colors
    public static final int COLOR_RED = 0;
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_WHITEBOARD = 2;
    public static final int COLOR_BLACKBOARD = 3;
    
    //Archetypes and Characteristics
    public static final String PAWN = "Pawn";
    public static final String BISHOP = "Bishop";
    public static final String KNIGHT = "Knight";
    public static final String ROOK = "Rook";
    public static final String QUEEN = "Queen";
    public static final String KING = "King";
    public static final String DARK = "Dark";
    public boolean dark = false;
    //Ground
    public static final String GROUND_WHITE = "White Ground";
    public static final String GROUND_BLACK = "Black Ground";
    
    private String archetype;
    private int color;
    private boolean enpassantable;
    private ArrayList<ChessMove> movesList;
    private int row;
    private int col;
    
    public ChessPiece(String archetype, int color) {
	this.archetype = archetype;
	this.color = color;
	this.enpassantable = false;
    }
    
    public ChessPiece(ChessPiece piece) {
	this.archetype = piece.getArchetype();
	this.color = piece.getColor();
	this.enpassantable = false;
    }
    
    public int getColor() {
	return this.color;
    }
    
    public String getArchetype() {
	return this.archetype;
    }
    
    public boolean isDark() {
	return this.dark;
    }
    
    public void setRow(int r) {
	this.row = r;
    }
    
    public int getRow() {
	return this.row;
    }
    
    public void setCol(int c) {
	this.col = c;
    }
    
    public int getCol() {
	return this.col;
    }
    
    public void setMovesList(ArrayList<ChessMove> l) {
    	this.movesList = l;
    }
    
    public ArrayList<ChessMove> getActions() {
    	return this.movesList;
    }
    
    public String toString() {
    	String s;
    	s = "Rank: " + this.archetype + " Position: " + this.row + ", " + this.col;
    	return s;
    }
}
