package main;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;

import processing.core.PApplet;

public class Rules extends PApplet {
	private Tile current;
	private static int TIMER;
	private Colorizer colorizer;
	private int SPEED = 5;
	private int FRAMERATE = 60;
	private Grid g;
	private Main main;
	private Score s;

	public Rules() {
		TIMER = 0;
	}

	public Rules(Colorizer colorizer, Tile current, Grid g) {
		this();
		this.colorizer = colorizer;
		this.current = current;
		this.g = g;
	}

	public void setSpeed(int speed) {
		SPEED = speed;
	}

	public void setFR(int framerate) {
		FRAMERATE = framerate;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void run() {
		// Polynomial regression
		int run_period = (int) ((-0.0235 * Math.pow(SPEED, 3) + 0.69
				* Math.pow(SPEED, 2) - 7.85 * SPEED + 35) * (FRAMERATE / 60.0)) + 1;

		if (TIMER % run_period == 0) {
			if (hitBottom()) {
				// System.out.println(current.getSquares().get(0).getXCor());
				current = colorizer.spawnBlock();
			} else if (hitBlock()) {
				System.out.println("Triggered");
				current = colorizer.spawnBlock();
			} else {
				current = colorizer.drop(current, 1);
				// clearLine();
			}
		}
		TIMER++;
	}

	public void registerKeyPress(int keyCode) {
		if (keyCode == RIGHT) {
			if (!hitSides()) {
				current = colorizer.moveRight(current);
			}
		} else if (keyCode == LEFT) {
			if (!hitSides()) {
				current = colorizer.moveLeft(current);
			}
		} else if (keyCode == UP) {
			current = colorizer.rotate(false, current, 1);
		} else if (main.keyCode == main.DOWN) {
			// current = colorizer.drop(current);
		}
	}

	public boolean hitBottom() {
		int lowestY = g.getSquare(g.numRows - 1, 0).getYCor();
		for (Square s : current.getSquares()) {
			if (s.getYCor() == lowestY) {
				return true;
			}
		}
		return false;
	}

	public boolean hitSides() {
		if (leftest() == g.getSquare(0, 0).getXCor()
				|| rightest() == g.getSquare(0, g.numCols - 1).getXCor()) {
			return true;
		}
		return false;
	}

	public int leftest() {
		Square leftest = current.getSquares().get(0);
		for (Square s : current.getSquares()) {
			if (s.getXCor() < leftest.getXCor()) {
				leftest = s;
			}
		}
		return leftest.getXCor();
	}

	public int rightest() {
		Square rightest = current.getSquares().get(0);
		for (Square s : current.getSquares()) {
			if (s.getXCor() > rightest.getXCor()) {
				rightest = s;
			}
		}
		return rightest.getXCor();
	}

	public ArrayList<Square> lowest() {
		ArrayList<Square> ans = new ArrayList<Square>();
		Square lowest = current.getSquares().get(0);
		for (Square s : current.getSquares()) {
			if (s.getYCor() < lowest.getYCor()) {
				lowest = s;
			}
		}
		int counter = 0;
		for (Square s : current.getSquares()) {
			if (s.getYCor() <= lowest.getYCor()) {
				ans.get(counter).setXYCor(s.getYCor(), s.getXCor());
				counter++;
			}
		}
		return ans;
	}

	/*
	 * public void hitBlock() { for (Square s: lowest()) {
	 * 
	 * } }
	 */

	public boolean part(Square t) {
		return t.partOfCurrentBlock;
	}

	public boolean hitBlock() {
		/*
		 * if (current.getSquares().get(0).getXCor() > 500) { return false; }
		 * else {
		 */
		for (Square s : current.getSquares()) {
			if ((colorizer.colored(s.getRowIndex() + 1, s.getColIndex()))
					&& part(g.grid[s.getRowIndex() + 1][s.getColIndex()])) {
				return true;
			}
		}

		return false;
	}

	public boolean blockOffMap() {
		int lowestX = g.getSquare(0, 0).getXCor();
		int highestX = g.getSquare(0, g.numCols - 1).getXCor();
		int lowestY = g.getSquare(g.numRows - 1, 0).getYCor();
		for (Square s : current.getSquares()) {
			// need to change
			if (s.getXCor() <= lowestX || s.getYCor() > lowestY
					|| s.getXCor() > highestX) {
				return true;
			}
		}
		return false;
	}

	public int calLowestYCorOfTile(Tile t) {
		int lowest = t.getSquares().get(0).getYCor();
		for (Square s : t.getSquares()) {
			if (s.getYCor() < lowest) {
				lowest = s.getYCor();
			}
		}
		return lowest;
	}

	public int calHighestYCorOfTile(Tile t) {
		int highest = t.getSquares().get(0).getYCor();
		for (Square s : t.getSquares()) {
			if (s.getYCor() > highest) {
				highest = s.getYCor();
			}
		}
		return highest;
	}

	public int calNextColoredSquare(Tile t) {
		int[] white = new int[] { 255, 255, 255 };
		return 1;
	}

	public void clearLine(int r) {
		int[] white = new int[] { 255, 255, 255 };
		for (int s = 0; s < g.numCols; s++) {
			g.grid[r][s].color = white;
		}
	}

	public void clearLine() {
		int[] white = new int[] { 255, 255, 255 };
		int counter = g.getNumCols();
		for (int r = 0; r < g.getNumRows(); r++) {
			for (int s = 0; s < g.getNumCols(); s++) {
				if (g.grid[r][s].color != white) {
					counter--;
					System.out.println(counter);
				}
				if (counter == 0) {
					numOfLines++;
					clearLine(r);
				}
			}
			counter = g.getNumCols();
		}
	}

	public void clearLine1() {
		boolean gapBetweenLines;
		int numOfLines;
		int[] white = new int[] { 255, 255, 255 };
		for (int r = 0; r < g.getNumRows(); r++) {
			gapBetweenLines = false;
			for (int s = 0; s < g.getNumCols(); s++) {
				if (g.grid[r][s].color ) {
					gapBetweenLines = true;
					break;
				}
			}
			if (!gapBetweenLines) {
				lowerRow(s);
				r --;
				numOfLines++;
			}
		}	
		int n = numOfLines;
		s.setScore(s.getScore() + 40 * (n + 1) + 100 * (n + 1) + 300 * (n + 1)
				+ 1200 * (n + 1));
	}
	
	public void lowerRow(int s1) {
	}

	/*
	 * public boolean hit() { int[] white = new int[] { 255, 255, 255 }; int
	 * lowX = current.getSquares().get(0).getColIndex(); int lowY =
	 * current.getSquares().get(0).getRowIndex(); int highX =
	 * current.getSquares().get(0).getColIndex(); int highY =
	 * current.getSquares().get(0).getRowIndex(); for (Square s:
	 * current.getSquares()) { if (s.getColIndex() < lowX) { } } return false; }
	 */

	public class KeyAction extends AbstractAction {
		private String sequence;
		private KeyStroke keystroke;

		public KeyAction(String sequence) {
			this.sequence = sequence;
		}

		public void actionPerformed(ActionEvent e) {
			switch (sequence) {
			case "clockwise":
				colorizer.rotate(true, current, 1);
				break;
			case "counterclockwise":
				colorizer.rotate(false, current, 1);
				break;
			case "left":
				colorizer.moveLeft(current);
				break;
			case "right":
				colorizer.moveRight(current);
				break;
			}

			/*
			 * if (e.getKeyCode() == KeyEvent.VK_LEFT) { current =
			 * colorizer.moveRight(current); System.out.println("yay"); } else
			 * if (e.getKeyCode() == KeyEvent.VK_RIGHT) { current =
			 * colorizer.moveLeft(current); }
			 */
		}
	}
}
