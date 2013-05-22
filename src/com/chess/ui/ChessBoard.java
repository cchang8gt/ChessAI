package com.chess.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChessBoard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final boolean DEBUG = true;
    private static final int PLAYER = 0;
    private static final int COMPUTER = 1;
    private HashMap<String, Image> images;
    private int activeColor, playerColor, selectedRow = -1, selectedCol = -1;
    // holds panel info
    private JLabel[] labels = new JLabel[64];
    private ImagePanel[] panels = new ImagePanel[64];
    // game state info
    private ChessPiece[][] gameState = new ChessPiece[8][8];
    private ArrayList<ChessPiece> playerAlive, playerDead, aiAlive, aiDead;
    private int turnCounter = 0;

    // color == player's color.
    public ChessBoard(int color) {
	activeColor = color;
	playerColor = color;

	playerAlive = new ArrayList<ChessPiece>();
	playerDead = new ArrayList<ChessPiece>();
	aiAlive = new ArrayList<ChessPiece>();

	loadResources();
	constructGame();
	showGUI();
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
	    img = ImageIO.read(new File("res/Blue_Pawn.png"));
	    images.put("Blue Pawn", img);
	    img = ImageIO.read(new File("res/Red_Bishop.png"));
	    images.put("Red Bishop", img);
	    img = ImageIO.read(new File("res/Blue_Bishop.png"));
	    images.put("Blue Bishop", img);
	    img = ImageIO.read(new File("res/Red_Knight.png"));
	    images.put("Red Knight", img);
	    img = ImageIO.read(new File("res/Blue_Knight.png"));
	    images.put("Blue Knight", img);
	    img = ImageIO.read(new File("res/Red_Rook.png"));
	    images.put("Red Rook", img);
	    img = ImageIO.read(new File("res/Blue_Rook.png"));
	    images.put("Blue Rook", img);
	    img = ImageIO.read(new File("res/Red_Queen.png"));
	    images.put("Red Queen", img);
	    img = ImageIO.read(new File("res/Blue_Queen.png"));
	    images.put("Blue Queen", img);
	    img = ImageIO.read(new File("res/Red_King.png"));
	    images.put("Red King", img);
	    img = ImageIO.read(new File("res/Blue_King.png"));
	    images.put("Blue King", img);
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Red moves first.
     * 
     * First square starts white
     * 
     * Blue top: RKBQZBKR PPPPPPPP
     * 
     * Blue bot: PPPPPPPP RKBQZBKR
     * 
     */
    private void constructGame() {
	playerAlive = openingSetup(playerColor);
	aiAlive = openingSetup(playerColor ^ 1);

	if (DEBUG) {
	    System.out.println("Player Alive size: " + playerAlive.size());
	    System.out.println("AI Alive size: " + aiAlive.size());
	}

	// populate game state
	// i == row, j == col
	for (int i = 0; i < 2; i++) {
	    for (int j = 0; j < 8; j++) {
		gameState[i][j] = aiAlive.get(i * 8 + j);
	    }
	}
	for (int i = 2; i < 6; i++) {
	    for (int j = 0; j < 8; j++) {
		if (j % 2 == 0) {
		    if (i % 2 == 0) {
			gameState[i][j] = new ChessPiece(ChessPiece.GROUND_WHITE, ChessPiece.COLOR_WHITEBOARD);
		    }
		    else {
			gameState[i][j] = new ChessPiece(ChessPiece.GROUND_BLACK, ChessPiece.COLOR_BLACKBOARD);
		    }
		}
		else {
		    if (i % 2 == 0) {
			gameState[i][j] = new ChessPiece(ChessPiece.GROUND_BLACK, ChessPiece.COLOR_BLACKBOARD);
		    }
		    else {
			gameState[i][j] = new ChessPiece(ChessPiece.GROUND_WHITE, ChessPiece.COLOR_WHITEBOARD);
		    }
		    // gameState[i][j] = new ChessPiece(ChessPiece.GROUND_BLACK,
		    // ChessPiece.COLOR_BLACKBOARD);
		}
	    }
	}
	for (int i = 6; i < 8; i++) {
	    for (int j = 0; j < 8; j++) {
		gameState[i][j] = playerAlive.get((i - 6) * 8 + j);
	    }
	}

	if (DEBUG) {
	    for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
		    System.out.print(gameState[i][j].getArchetype() + " ");
		}
		System.out.println();
	    }
	}
    }

    private ArrayList<ChessPiece> openingSetup(int color) {
	ArrayList<ChessPiece> units = new ArrayList<ChessPiece>();
	// if we're setting up a player (red)
	if (color == playerColor) {
	    for (int i = 0; i < 8; i++) {
		units.add(new ChessPiece(ChessPiece.PAWN, color));
	    }
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.QUEEN, color));
	    units.add(new ChessPiece(ChessPiece.KING, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	}
	// if we're setting up a comp (blue)
	else {
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.QUEEN, color));
	    units.add(new ChessPiece(ChessPiece.KING, color));
	    units.add(new ChessPiece(ChessPiece.BISHOP, color));
	    units.add(new ChessPiece(ChessPiece.KNIGHT, color));
	    units.add(new ChessPiece(ChessPiece.ROOK, color));
	    for (int i = 0; i < 8; i++) {
		units.add(new ChessPiece(ChessPiece.PAWN, color));
	    }
	}

	return units;
    }

    private ArrayList<ChessPiece> defaultBoard(int color) {
	return null;
    }

    private void showGUI() {
	GridLayout gridLayout = new GridLayout(8, 8);
	this.setLayout(gridLayout);
	// add panels and labels to the central panel which holds the board
	addPanelsAndLabels();
    }

    private void addPanelsAndLabels() {
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
    
    private void addPanelsAndLabels(ArrayList<ChessPiece> gameState) {
	
    }

    private void drawGameState() {
	int count = 0;
	String[] colorMap = { "Red", "Blue", "White", "Black" };
	removeAll();
	for (int row = 0; row < 8; row++) {
	    for (int col = 0; col < 8; col++) {
		try {
		    if (gameState[row][col].getColor() == ChessPiece.COLOR_RED || gameState[row][col].getColor() == ChessPiece.COLOR_BLUE) {
			panels[count] = new ImagePanel(images.get(colorMap[gameState[row][col].getColor()] + " " + gameState[row][col].getArchetype()), gameState[row][col], row, col);
		    }
		    else {
			panels[count] = new ImagePanel(images.get(gameState[row][col].getArchetype()), gameState[row][col], row, col);
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

    private void doAction(int rowFrom, int colFrom, int rowTo, int colTo) {
	System.out.println("Moved");
    }

    /**
     * updates turn counter
     */
    private void updateTurn() {
	// sanity check
	selectedRow = selectedCol = -1;
	turnCounter++;
    }

    private void addPiece(ImageIcon img, String block) {
	for (int s = 0; s < labels.length; s++) {
	    if (labels[s].getName().equalsIgnoreCase(block)) {
		labels[s].setIcon(img);
	    }
	}
    }

    /**
     * Classifies piece owner. 0 if player, 1 if ai, 2 if nothing.
     * 
     * @param x
     * @param y
     * @return
     */
    private int classifyPiece(int x, int y) {
	ChessPiece c = gameState[x][y];
	if (c.getColor() == playerColor) {
	    return 0;
	}
	else if (c.getColor() != playerColor) {
	    return 1;
	}
	else {
	    return 2;
	}
    }

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
	    i = (ImagePanel) e.getSource();
	    chessPiece = i.piece;
	    if (piece.getArchetype().equals("Black Ground") == false && piece.getArchetype().equals("White Ground") == false && piece.getColor() == playerColor == true || selectedCol == col || selectedRow == row) {
		if (selectedCol == -1) {
		    selectedCol = col;
		    selectedRow = row;
		}
		else if (selectedRow == -1) {
		    selectedCol = col;
		    selectedRow = row;
		}
		else {
		    if (selectedRow == row && selectedCol == col) {
			selectedRow = -1;
			selectedCol = -1;
		    }
		    else {
			doAction(selectedRow, selectedCol, row, col);
			selectedRow = -1;
			selectedCol = -1;
		    }
		}
		if (DEBUG) {
		    System.out.print(piece.getArchetype());
		    System.out.println(": " + selectedRow + "," + selectedCol);
		}
	    }

	}
    }
}
