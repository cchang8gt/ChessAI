package com.chess.ai.chrisai;
import java.util.ArrayList;

import com.chess.engine.Chess;
import com.chess.engine.ChessMove;
import com.chess.engine.ChessPiece;
import java.util.Random;

public class ChrisAI {
	public ArrayList<ChessPiece> myPiece;
	public ArrayList<ChessMove> myMoves;
	public ChessMove cm;
	public Random r;
	ChessPiece piece;
	
	public ChessMove action(Chess g) {
		myPiece = g.getMyUnits();
		r = new Random();
		
		//i spent no time on this
		while(true) {
			piece = myPiece.get(r.nextInt(myPiece.size()));
			if(piece.getActions().size() > 0) {
				break;
			}
		}
		return piece.getActions().get(r.nextInt(piece.getActions().size()));
	}

}
