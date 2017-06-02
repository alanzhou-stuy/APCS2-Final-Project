package main;

import java.awt.event.*;
import javax.swing.*;

public class Rules {
	private Tile current;
	private static int TIMER;
	private Colorizer colorizer;
	Grid g;

	public Rules() {
		TIMER = 0;
	}

	public Rules(Colorizer colorizer, Tile current,Grid g) {
		this();
		this.colorizer = colorizer;
		this.current = current;
		this.g = g;
	}

	public void run() {
		if (TIMER % 20 == 0) {
			if (hitBottom() || hitBlock()) {
				current = colorizer.spawnBlock();
			} else {
				current = colorizer.drop(current, 1);
				//current = colorizer.moveRight(current);			
				/*for (Square s : current.getSquares()) {
				}
					System.out.println(s.getYCor());
				}*/
			}
		}
		TIMER++;

	}

	public boolean hitBottom() {
		int lowestY = g.getSquare(g.numRows - 1,0).getYCor();
		for (Square s : current.getSquares()) {
			if (s.getYCor() == lowestY) {
				return true;
			}
		}
		return false;
	}

	public boolean hitBlock() {
		int[] white = new int[] { 255, 255, 255 };
		for (Square[] rowOfSquares : g.grid) {
			for (Square s : rowOfSquares) {
				for (Square s1: current.getSquares()) {
					//System.out.println(s1.getYCor() + g.squareSize);
					//System.out.println(s.getYCor());
					if (((s1.getYCor() + g.squareSize + 1) == s.getYCor()) && (s.getColor() != white)) {
						return true;
					}
				}
			}
		}
		/*for (Square s: current.getSquares()) {
			if (g.getSquare(s.getXCor(), (s.getYCor() + g.squareSize)).getColor() != white) {
				return true;
			}
		}*/
		return false;
	}

	public boolean blockOffMap() {
		int lowestX = g.getSquare(0,0).getXCor();
		int highestX = g.getSquare(0,g.numCols - 1).getXCor();
		int lowestY = g.getSquare(g.numRows - 1,0).getYCor();
		for (Square s: current.getSquares()) {
			//need to change
			if (s.getXCor() <= lowestX || s.getYCor() > lowestY || s.getXCor() > highestX) { 
				return true;
			}
		}
		return false;
	}
	
	public int calLowestYCorOfTile(Tile t) {
		int lowest = t.getSquares().get(0).getYCor();
		for (Square s: t.getSquares()) {
			if (s.getYCor() < lowest) {
				lowest = s.getYCor();
			}
		}
		return lowest;
	}
	
	public int calHighestYCorOfTile(Tile t) {
		int highest = t.getSquares().get(0).getYCor();
		for (Square s: t.getSquares()) {
			if (s.getYCor() > highest) {
				highest = s.getYCor();
			}
		}
		return highest;
	}
	
	public int calNextColoredSquare(Tile t) {
		int[] white = new int[] { 255, 255, 255 };
		for (Square[] rowOfSquares : g.grid) {
			for (Square s: rowOfSquares) {
				if ((calLowestYCorOfTile(t) == s.getYCor()) && (s.getColor() != white)){
					return s.getXCor();
				}
			}
		}
		return -1;
	}
	
	public void fullLine() {
		int counter = g.getNumCols();
		int[] white = new int[] { 255, 255, 255 };
		for (Square[] rowOfSquares: g.grid) {
			for (Square s: rowOfSquares) {
				if (s.getColor() != white) {
					counter --;
				}
				if (counter == 0) {
					clearLine();
				}
			}
			counter = g.getNumCols();
		}
	}
	
	public void clearLine() {
		for (int r = 1; r < g.getNumRows(); r++) {
			for (int s = 0; s < g.getNumCols(); s++) {
				g.grid[r][s].setColor(g.grid[r+1][s].getColor());
			}
		}
	}

	public class KeyAction extends AbstractAction {
		private String sequence;
		private KeyStroke keystroke;
		
		public KeyAction(String sequence) {
			this.sequence = sequence;
		}
		
		public void actionPerformed(ActionEvent e) {
			/*
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				current = colorizer.moveRight(current);
				System.out.println("yay");
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				current = colorizer.moveLeft(current);
			}
			*/
		}	
	}
}
