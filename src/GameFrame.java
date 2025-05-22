import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class GameFrame extends JFrame {
	public static final int SCALE = 75;

	int activeRow;
	int activeColumn;
	
	private ArrayList<Level> levelList;
	private int levelIndex;
	
	JPanel screenPanel;
	Level activeLevel;
	
	public GameFrame() {
		levelIndex = 0;
		createLevelList();
		
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu helpMenu = new JMenu("Help");
		JMenu levelMenu = new JMenu("Level Select");
		
		JMenuItem howToPlayItem = new JMenuItem("How to Play");
		JMenuItem aboutItem = new JMenuItem("About");
		howToPlayItem.addActionListener(e -> howToPlayHandler() );
		aboutItem.addActionListener(e -> aboutHandler() );
		helpMenu.add(howToPlayItem);
		helpMenu.add(aboutItem);
		
		for (int i = 0; i < levelList.size(); i++) {
			final int levelNumber = i;
			JMenuItem levelItem = new JMenuItem("Level " + levelNumber);
			levelItem.addActionListener(e -> levelSelectHandler(levelNumber) );
			levelMenu.add(levelItem);
		}
		
		menuBar.add(helpMenu);
		menuBar.add(levelMenu);
		
		setJMenuBar(menuBar);
		
		screenPanel = new JPanel();
		add(screenPanel);
		loadLevel(levelList.get(levelIndex));
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setTitle("Fragile Walk");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public void howToPlayHandler() {
		JOptionPane.showMessageDialog(null, 
		"You can land on every tile ONLY ONCE * \n" 
			+ "Break all tiles and reach the flag LAST. \n \n" 
			+ "* Some tiles have to be clicked \n"
			+ " multipe times to break!");
	}
	
	public void aboutHandler() {
		JOptionPane.showMessageDialog(null, "Made by Eddie! 🎮\nHope you enjoy the game!");
	}
	
	public void levelSelectHandler(int levelNumber) {
		levelIndex = levelNumber;
		loadLevel(levelList.get(levelIndex) );
	}
	
	public void loadLevel(Level myLevel) {
		activeLevel = myLevel.copy();
		screenPanel.setLayout(new GridLayout(activeLevel.getRows(), activeLevel.getColumns() ) );
		refreshMap();
		pack();
		setLocationRelativeTo(null);
		
	}
	
	public void refreshMap() {
		screenPanel.removeAll();
		for (int r = 0; r < activeLevel.map.length; r++) {
			for (int c = 0; c < activeLevel.map[r].length; c++) {
				screenPanel.add(new TilePanel(activeLevel.map[r][c], r, c, this));
			}
		}
		screenPanel.revalidate();
		screenPanel.repaint();
	}
	
	public void handleTileCLick(TilePanel myTilePanel) {
		if (myTilePanel.getTileValue() == 10) { activeLevel.map[myTilePanel.getRow()][myTilePanel.getColumn()] = 1; }
		else { activeLevel.map[myTilePanel.getRow()][myTilePanel.getColumn()] -= 3; }
		
		deborder();
		
		addBorder( myTilePanel.getRow() + 1,myTilePanel.getColumn() );
		addBorder( myTilePanel.getRow() - 1,myTilePanel.getColumn() );
		addBorder( myTilePanel.getRow(),myTilePanel.getColumn() + 1 );
		addBorder( myTilePanel.getRow(),myTilePanel.getColumn() - 1 );
		// repeat the code for flags
		addBorder( myTilePanel.getRow() + 1,myTilePanel.getColumn() );
		addBorder( myTilePanel.getRow() - 1,myTilePanel.getColumn() );
		addBorder( myTilePanel.getRow(),myTilePanel.getColumn() + 1 );
		addBorder( myTilePanel.getRow(),myTilePanel.getColumn() - 1 );
		
		refreshMap();
		if (checkForWin() ) {
			handleWin();
		}
		else if (checkForNoMoves() ) {
			handleNoMoves();
		}
	}
	
	private void addBorder(int row, int column) {
		try {
			if (activeLevel.map[row][column] % 2 != 0 && isTraversable(activeLevel.map[row][column])) { activeLevel.map[row][column] += 1; }
		} catch (Exception e) { }
	}
	
	private void deborder() {
		int[][] map = activeLevel.map;
		for (int r = 0; r < activeLevel.map.length; r++) {
			for (int c = 0; c < activeLevel.map[r].length; c++) {
				if (map[r][c] % 2 == 0) { activeLevel.map[r][c] -= 1; }
			}
		}
	}
	
	public boolean isTraversable(int tileValue) {
		if (tileValue <= 2) { return false; }
		else if (tileValue <= 8) { return true; }
		else {
			for (int r = 0; r < activeLevel.map.length; r++) {
				for (int c = 0; c < activeLevel.map[r].length; c++) {
					if (activeLevel.map[r][c] > 2 && activeLevel.map[r][c] <= 8) { return false; }
				}
			}
			return true;
		}
	}
	
	public boolean checkForWin() {
		for (int r = 0; r < activeLevel.map.length; r++) {
			for (int c = 0; c < activeLevel.map[r].length; c++) {
				if (activeLevel.map[r][c] > 2) { return false; }
			}
		}
		return true;
		
	}
	
	public boolean checkForNoMoves() {
		for (int r = 0; r < activeLevel.map.length; r++) {
			for (int c = 0; c < activeLevel.map[r].length; c++) {
				if (activeLevel.map[r][c] % 2 == 0) { return false; }
			}
		}
		return true;
	}
	
	public void handleWin() {
		levelIndex += 1;
		if (levelIndex < levelList.size() ) {
			loadLevel( levelList.get(levelIndex) );
		}
		else {
			JOptionPane.showMessageDialog(this, "You win! 🎉");
		}
	}
	
	public void handleNoMoves() {
		loadLevel( levelList.get(levelIndex) );
	}
	
	public void createLevelList() {
		levelList = new ArrayList<>();
		
		levelList.add( new Level(new int[][] {
			{-1,-1,9,-1,-1},
			{-1,-1,3,3,-1},
			{-1,3,3,3,-1},
			{-1,3,3,-1,-1},
			{-1,-1,4,-1,-1}
		}) );
		
		levelList.add( new Level(new int[][] {
			{-1,-1,-1,-1,-1,9,-1,-1,-1,-1,-1},
			{3,3,-1,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,-1,3,3,-1,3},
			{3,-1,3,3,-1,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,-1,3,3},
			{-1,-1,-1,-1,-1,4,-1,-1,-1,-1,-1}
		}) );
		
		levelList.add( new Level(new int[][] {
			{-1,-1,9,-1,-1},
			{-1,3,3,3,-1},
			{-1,5,3,5,-1},
			{-1,3,3,3,-1},
			{-1,-1,4,-1,-1}
		}) );
		
		levelList.add( new Level(new int[][] {
			{-1,-1,9,-1,-1},
			{3,5,5,3,-1},
			{5,7,-1,7,5},
			{-1,3,3,5,3},
			{-1,-1,4,-1,-1}
		}) );
		
		levelList.add( new Level(new int[][] {
			{-1,-1,9,-1,-1}, 
			{3,3,3,5,3}, 
			{5,5,-1,5,3}, 
			{3,5,3,3,3}, 
			{3,3,3,3,3}, 
			{-1,-1,4,-1,-1} 
		}) );
		
		levelList.add( new Level(new int[][] {
			{-1,-1,9,-1}, {3,5,5,3}, {3,5,3,5}, {3,7,5,5}, {-1,4,-1,-1}
		}) );
		
		levelList.add( new Level(new int[][] {
			{-1,3,-1,3,3,3,-1}, {3,7,3,3,3,7,3}, 
			{3,3,3,3,3,3,-1}, {3,3,3,9,3,3,3}, 
			{-1,3,3,3,-1,3,3}, {3,7,3,3,3,7,3}, 
			{-1,3,3,3,-1,3,4}
		}) );
		
		levelList.add( new Level(new int[][] {
			{3,3,3,3,3,3,3}, {3,5,3,-1,3,5,3}, {3,3,3,3,3,3,3}, {3,3,3, 3, 3, 3, 3}, {3,5,3,3,3,5,3}, {3,3,5,5,5,3,3}, {3,3,3,3,3,3,3}, {-1,-1,-1,4,-1,-1,-1}
		}) );
		
	}
	
}