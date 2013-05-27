package com.chess.engine;
import java.util.HashMap;
import java.util.Map;

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
    private int cellColor; //this shouldn't change
    
    public ChessPiece(String archetype, int color) {
	this.archetype = archetype;
	this.color = color;
    }
    
    public ChessPiece(ChessPiece piece) {
	this.archetype = piece.getArchetype();
	this.color = piece.getColor();
	this.cellColor = piece.getCellColor();
    }
    
    public int getColor() {
	return this.color;
    }
    
    public String getArchetype() {
	return this.archetype;
    }
    
    public void setCellColor(int c) {
	this.cellColor = c;
    }
    
    public int getCellColor() {
	return this.cellColor;
    }
    
    public String getCellColorString() {
	if(this.cellColor == COLOR_WHITEBOARD) {
	    return GROUND_WHITE;
	}
	else {
	    return GROUND_BLACK;
	}
    }
    
    public boolean isDark() {
	return this.dark;
    }
}
