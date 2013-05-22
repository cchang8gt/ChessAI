package com.chess.ui;
import javax.swing.JFrame;

public class GameContainer extends JFrame {
    private static final long serialVersionUID = 1L;
    public ChessBoard board;
    
    public GameContainer() {
	this.add(new ChessBoard(ChessPiece.COLOR_RED));
	this.setTitle("Chess");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setSize(800, 800);
	this.setVisible(true);
	this.setResizable(false);
    }
    
    public static void main(String[] args) {
	new GameContainer();
    }
}
