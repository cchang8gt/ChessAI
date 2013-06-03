package com.chess.ui;
import javax.swing.JFrame;
import com.chess.ai.chrisai.*;
import com.chess.engine.ChessPiece;
public class GameContainer extends JFrame {
    private static final long serialVersionUID = 1L;
    public ChessBoard board;
    
    public GameContainer() {
	//this.add(new ChessBoard(ChessPiece.COLOR_RED));
    this.add(new ChessBoard(new ChrisAI(), new ChrisAI()));
	this.setTitle("Chess");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setSize(800, 800);
	this.setVisible(true);
	this.setResizable(false);
    }
    
    public GameContainer(ChrisAI a, ChrisAI b) {
	
    }
    
    public static void main(String[] args) {
	new GameContainer();
    }
}
