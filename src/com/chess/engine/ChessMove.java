package com.chess.engine;

public class ChessMove implements Comparable<ChessMove> {
	public int rowFrom;
	public int rowTo;
	public int colFrom;
	public int colTo;
	public int turnNumber;

	public ChessMove(int rowFrom, int colFrom, int rowTo, int colTo, int turnNumber) {
		this.rowFrom = rowFrom;
		this.colFrom = colFrom;
		this.rowTo = rowTo;
		this.colTo = colTo;
		this.turnNumber = turnNumber;
	}
	
	public ChessMove(int rowFrom, int colFrom, int rowTo, int colTo) {
		this.rowFrom = rowFrom;
		this.colFrom = colFrom;
		this.rowTo = rowTo;
		this.colTo = colTo;
	}
	
	public int compareTo(ChessMove compare) {
		int turnNumber = ((ChessMove)compare).turnNumber;
		return this.turnNumber - turnNumber;
	}
	
	public String toString(){
		String s; 
		s = "From: " + this.rowFrom + ", " + this.colFrom + " To: " + this.rowTo + ", " + this.colTo;
		return s;
	}
}
