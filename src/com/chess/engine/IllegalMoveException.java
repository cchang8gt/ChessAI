package com.chess.engine;

public class IllegalMoveException extends Exception {
	public IllegalMoveException() {}
	public IllegalMoveException(String msg) {
		super(msg);
	}
}
