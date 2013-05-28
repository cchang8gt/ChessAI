package com.chess.engine;

public class ChessMove {
    public int rowFrom;
    public int rowTo;
    public int colFrom;
    public int colTo;
    
    public ChessMove(int rowFrom, int colFrom, int rowTo, int colTo) {
	this.rowFrom = rowFrom;
	this.colFrom = colFrom;
	this.rowTo = rowTo;
	this.colTo = colTo;
    }
}
