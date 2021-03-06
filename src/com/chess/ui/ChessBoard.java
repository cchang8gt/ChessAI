package com.chess.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.chess.ai.chrisai.ChrisAI;
import com.chess.engine.Chess;
import com.chess.engine.ChessMove;
import com.chess.engine.ChessPiece;

/**
 * Handles UI components of the game of Chess
 * 
 * @author Chris
 * 
 *         TODO: Clean up code and add chess game code to the chess game TODO:
 *         Add shading for clicked elements to fix visibility
 * 
 *         TODO: Add text UI (not important)
 */
public class ChessBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUG = false;

	private HashMap<String, Image> images;
	Chess chessGame;

	private int selectedRow = -1;
	private int selectedCol = -1;
	// holds panel info
	private JLabel[] labels = new JLabel[64];
	private ImagePanel[] panels = new ImagePanel[64];

	// game state info
	private ChessPiece[][] gameState = new ChessPiece[8][8];
	private int turnCounter = 0;

	// agents
	private ChrisAI top;
	private ChrisAI bot;

	public ChessBoard(int color) {
		chessGame = new Chess(this, color);

		loadResources();
		constructGame();
		showGUI();
	}

	public ChessBoard(ChrisAI top, ChrisAI bot) {
		chessGame = new Chess(this, top, bot);
		chessGame.botgame = true;
		
		loadResources();
		constructGame();
		showGUI();

		chessGame.updatePositions();
		chessGame.updateLegalMoves();
	}

	// load image files into hashmap
	private void loadResources() {
		images = new HashMap<String, Image>();

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("res/Black_Ground.png"));
			images.put("Black Ground", img);
			img = ImageIO.read(new File("res/White_Ground.png"));
			images.put("White Ground", img);
			img = ImageIO.read(new File("res/Red_Pawn.png"));
			images.put("Red Pawn", img);
			img = ImageIO.read(new File("res/Red_Bishop.png"));
			images.put("Red Bishop", img);
			img = ImageIO.read(new File("res/Red_Knight.png"));
			images.put("Red Knight", img);
			img = ImageIO.read(new File("res/Red_Rook.png"));
			images.put("Red Rook", img);
			img = ImageIO.read(new File("res/Red_Queen.png"));
			images.put("Red Queen", img);
			img = ImageIO.read(new File("res/Red_King.png"));
			images.put("Red King", img);

			img = ImageIO.read(new File("res/Red_Pawn_Dark.png"));
			images.put("Red Pawn Dark", img);
			img = ImageIO.read(new File("res/Red_Bishop_Dark.png"));
			images.put("Red Bishop Dark", img);
			img = ImageIO.read(new File("res/Red_Knight_Dark.png"));
			images.put("Red Knight Dark", img);
			img = ImageIO.read(new File("res/Red_Rook_Dark.png"));
			images.put("Red Rook Dark", img);
			img = ImageIO.read(new File("res/Red_Queen_Dark.png"));
			images.put("Red Queen Dark", img);
			img = ImageIO.read(new File("res/Red_King_Dark.png"));
			images.put("Red King Dark", img);

			img = ImageIO.read(new File("res/Blue_Pawn.png"));
			images.put("Blue Pawn", img);
			img = ImageIO.read(new File("res/Blue_Bishop.png"));
			images.put("Blue Bishop", img);
			img = ImageIO.read(new File("res/Blue_Knight.png"));
			images.put("Blue Knight", img);
			img = ImageIO.read(new File("res/Blue_Rook.png"));
			images.put("Blue Rook", img);
			img = ImageIO.read(new File("res/Blue_Queen.png"));
			images.put("Blue Queen", img);
			img = ImageIO.read(new File("res/Blue_King.png"));
			images.put("Blue King", img);

			img = ImageIO.read(new File("res/Blue_Pawn_Dark.png"));
			images.put("Blue Pawn Dark", img);
			img = ImageIO.read(new File("res/Blue_Bishop_Dark.png"));
			images.put("Blue Bishop Dark", img);
			img = ImageIO.read(new File("res/Blue_Knight_Dark.png"));
			images.put("Blue Knight Dark", img);
			img = ImageIO.read(new File("res/Blue_Rook_Dark.png"));
			images.put("Blue Rook Dark", img);
			img = ImageIO.read(new File("res/Blue_Queen_Dark.png"));
			images.put("Blue Queen Dark", img);
			img = ImageIO.read(new File("res/Blue_King_Dark.png"));
			images.put("Blue King Dark", img);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the chess game state NOTE: this should be handled by the
	 * chess engine.
	 */
	private void constructGame() {

		if (DEBUG) {
			System.out.println("Player Alive size: " + chessGame.playerAlive.size());
			System.out.println("AI Alive size: " + chessGame.aiAlive.size());
		}

		// populate game state
		// i == row, j == col
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				chessGame.gameState[i][j] = chessGame.aiAlive.get(i * 8 + j);
			}
		}

		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (j % 2 == 0) {
					if (i % 2 == 0) {
						chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_WHITE, ChessPiece.COLOR_WHITEBOARD);
					}
					else {
						chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_BLACK, ChessPiece.COLOR_BLACKBOARD);
					}
				}
				else {
					if (i % 2 == 0) {
						chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_BLACK, ChessPiece.COLOR_BLACKBOARD);
					}
					else {
						chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_WHITE, ChessPiece.COLOR_WHITEBOARD);
					}
					// chessGame.gameState[i][j] = new
					// ChessPiece(ChessPiece.GROUND_BLACK,
					// ChessPiece.COLOR_BLACKBOARD);
				}
			}
		}

		for (int i = 6; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessGame.gameState[i][j] = chessGame.playerAlive.get((i - 6) * 8 + j);
			}
		}
		chessGame.updatePositions();
		if (DEBUG) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					System.out.print(chessGame.gameState[i][j].getArchetype() + " ");
				}
				System.out.println();
			}
		}
	}

	/**
	 * Constructs chess board using activeColor
	 */
	private void constructBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessGame.gameState[i][j].getColor() != ChessPiece.COLOR_RED && chessGame.gameState[i][j].getColor() != ChessPiece.COLOR_BLUE) {
					if (j % 2 == 0) {
						if (i % 2 == 0) {
							chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_WHITE, ChessPiece.COLOR_WHITEBOARD);
						}
						else {
							chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_BLACK, ChessPiece.COLOR_BLACKBOARD);
						}
					}
					else {
						if (i % 2 == 0) {
							chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_BLACK, ChessPiece.COLOR_BLACKBOARD);
						}
						else {
							chessGame.gameState[i][j] = new ChessPiece(ChessPiece.GROUND_WHITE, ChessPiece.COLOR_WHITEBOARD);
						}
						// chessGame.gameState[i][j] = new
						// ChessPiece(ChessPiece.GROUND_BLACK,
						// ChessPiece.COLOR_BLACKBOARD);
					}
				}
			}
		}
	}

	/**
	 * Shows GUI
	 */
	private void showGUI() {
		GridLayout gridLayout = new GridLayout(8, 8);
		this.setLayout(gridLayout);
		// add panels and labels to the central panel which holds the board
		addPanelsAndLabels();
	}

	/**
	 * Creates the board and adds necessary panels etc.
	 */
	public void addPanelsAndLabels() {
		constructBoard();
		drawGameState();
		for (int i = 0; i < panels.length; i++) {
			labels[i] = new JLabel();
			// know pos of label on board
			labels[i].setName(panels[i].getName());
			// add labels to panel
			panels[i].add(labels[i]);
			// add panels
			this.add(panels[i]);
		}
	}

	/**
	 * Updates the ASCII board
	 */
	private void textBoard() {

	}

	/**
	 * Updates the game state GUI.
	 */
	public void drawGameState() {
		int count = 0;
		String[] colorMap = { "Red", "Blue", "White", "Black" };
		removeAll();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				try {
					if (chessGame.gameState[row][col].getColor() == ChessPiece.COLOR_RED || chessGame.gameState[row][col].getColor() == ChessPiece.COLOR_BLUE) {
						panels[count] = new ImagePanel(images.get(colorMap[chessGame.gameState[row][col].getColor()] + " "
								+ chessGame.gameState[row][col].getArchetype()), chessGame.gameState[row][col], row, col);
					}
					else {
						panels[count] = new ImagePanel(images.get(chessGame.gameState[row][col].getArchetype()), chessGame.gameState[row][col], row, col);
					}
					panels[count].setName("FOO");
					count++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		}
		revalidate();
	}

	/**
	 * For human players. Called when a move is made by using the mouse
	 * 
	 * @param rowFrom
	 * @param colFrom
	 * @param rowTo
	 * @param colTo
	 */
	private void doAction(int rowFrom, int colFrom, int rowTo, int colTo) {
		/*
		 * Check and make sure that From and To colors are not the same If To is
		 * an enemy piece, capture If To is not an enemey piece, move
		 * 
		 * Movement rules: IF you move piece A to B Save A cell color Save B
		 * cell color Make B = A Set A color to B Set old tile color to A
		 */
		chessGame.doAction(rowFrom, colFrom, rowTo, colTo);
		selectedRow = selectedCol = -1;
		if (DEBUG) {
			// System.out.println("Moved? " +
			// chessGame.validateMove(rowFrom, colFrom, rowTo, colTo));
			System.out.println("--------------------doAction--------------------");
			System.out.print("Moved from: " + rowFrom + ", " + colFrom + " to: " + rowTo + ", " + colTo);
			System.out.println();
			System.out.print("AlivePlayer: " + chessGame.playerAlive.size() + " DeadPlayer: " + chessGame.playerDead.size());
			System.out.println();
			System.out.print("AlivePlayer Units: ");
			for (int i = 0; i < chessGame.playerAlive.size(); i++) {
				System.out.print(chessGame.playerAlive.get(i).getArchetype() + " ");
			}
			System.out.println();
			System.out.print("DeadPlayer Units: ");
			for (int i = 0; i < chessGame.playerDead.size(); i++) {
				System.out.print(chessGame.playerDead.get(i).getArchetype() + " ");
			}
			System.out.println();
			System.out.print("AliveAI: " + chessGame.aiAlive.size() + " DeadAI: " + chessGame.aiDead.size());
			System.out.println();
			System.out.print("AliveAI Units: ");
			for (int i = 0; i < chessGame.aiAlive.size(); i++) {
				System.out.print(chessGame.aiAlive.get(i).getArchetype() + " ");
			}
			System.out.println();
			System.out.print("DeadAI Units: ");
			for (int i = 0; i < chessGame.aiDead.size(); i++) {
				System.out.print(chessGame.aiDead.get(i).getArchetype() + " ");
			}
			System.out.println();
			System.out.println("--------------------doAction--------------------");
			System.out.println();
		}
	}

	private void startGame() {
		ChessMove move;
		int counter = 0;
		while (counter < 50) {
			if (chessGame.getTurn() % 2 == 0) {
				move = bot.action(chessGame);
				if (chessGame.validateMove(move)) {
					chessGame.doAction(move);
					counter++;
				}
			}
			else {
				move = top.action(chessGame);
			}
		}
	}

	/**
	 * Nested class for Swing labels
	 * 
	 * @author qob
	 * 
	 */
	class ImagePanel extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		private Image image;
		private ChessPiece piece;
		private int row, col;

		public ImagePanel(Image img, ChessPiece piece, int row, int col) {
			image = img;
			this.piece = piece;
			this.row = row;
			this.col = col;

			addActionListener(this);
		}

		protected void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}

		public void actionPerformed(ActionEvent e) {
			ImagePanel i;
			ChessPiece chessPiece;
			ChessPiece prevPiece;

			i = (ImagePanel) e.getSource();
			chessPiece = i.piece;
			/*
			 * If clicked piece is not black ground AND If clicked piece is not
			 * white ground AND If clicked piece is the owner's color OR First
			 * click
			 */

			if (chessGame.botgame == false) {
				if (selectedRow == -1 || selectedCol == -1) {
					if (piece.getArchetype().equals("Black Ground") == false && piece.getArchetype().equals("White Ground") == false // &&
																																		// piece.getColor()
																																		// ==
																																		// chessGame.playerColor
																																		// ==
																																		// true)
																																		// {
					) {
						selectedRow = row;
						selectedCol = col;
					}
				}
				// if not first click
				else {
					doAction(selectedRow, selectedCol, row, col);
					selectedRow = selectedCol = -1;
				}
				if (DEBUG) {
					System.out.println("--------------------actionPerformed--------------------");
					System.out.print("Archetype: " + piece.getArchetype());
					System.out.println(": " + selectedRow + "," + selectedCol);
					System.out.println("--------------------actionPerformed--------------------");
				}
				addPanelsAndLabels();
			}
			else {
				chessGame.playSingleStep();
				addPanelsAndLabels();
			}
		}
	}
}
