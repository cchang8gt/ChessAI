package com.chess.ai.chrisai;
import java.util.ArrayList;

import com.chess.engine.Chess;
import com.chess.engine.ChessMove;
import com.chess.engine.ChessPiece;

public class ChrisAI {
	public ArrayList<ChessPiece> myPiece;
	public ArrayList<ChessMove> myMoves;
	public ChessMove cm;
	public ChessMove action(Chess g) {
		myPiece = g.getMyUnits();
		for(int i = 0; i < myPiece.size(); i++) {
			for(int j = 0; j < myPiece.get(i).getActions().size(); j++) {
				cm = myPiece.get(i).getActions().get(0);
			}
		}
		return cm;
	}

}
