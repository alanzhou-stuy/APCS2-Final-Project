package main;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;

public class Rules extends PApplet {
	private Tile current;
	private Colorizer colorizer;
	private Grid g;
	private int numOfLines;
	private static int TIMER;
	private static int SPEED = 5;
	private static int FRAMERATE = 60;
	public static int SCORE;

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

	public void run() {
		// Polynomial regression
		int run_period = (int) ((-0.0235 * Math.pow(SPEED, 3) + 0.69 * Math.pow(SPEED, 2) - 7.85 * SPEED + 35)
				* (FRAMERATE / 60.0)) + 1;

		if (TIMER % run_period == 0) {
			if (hitBottom()) {
				if (clearLine()) {
					updateScore();
				}
				current = colorizer.spawnOBlock();
			} else if (hitBlock()) {
				if (clearLine()) {
					updateScore();
				}
				System.out.println("Triggered");
				current = colorizer.spawnOBlock();
			} else {
				current = colorizer.drop(current, 1);
			}
		}

		TIMER++;
	}

	public void updateScore() {
		int n = numOfLines;
		SCORE += 40 * (n + 1) + 100 * (n + 1) + 300 * (n + 1) + 1200 * (n + 1);
		numOfLines = 0;
	}

	public void registerKeyPress(int keyCode) {
		if (keyCode == RIGHT) {
			if (!hitSides() && !hitBlockSide(false))
				current = colorizer.moveRight(current);
		} else if (keyCode == LEFT) {
			if (!hitSides() && !hitBlockSide(true))
				current = colorizer.moveLeft(current);
		} else if (keyCode == UP) {
			if (!hitSides() && !hitBlockSide(false))
				current = colorizer.rotate(false, current, 1);
		} else if (keyCode == DOWN) {
			// current = colorizer.drop(current);
		}
	}

	private boolean hitBlockSide(boolean left) {
		for (Square s : current.getSquares()) {
			boolean notPartOfCurrent = true;
			Square next;

			if (left) {
				next = g.getSquare(s.getRowIndex(), s.getColIndex() - 1);
			} else {
				next = g.getSquare(s.getRowIndex(), s.getColIndex() + 1);
			}
			for (Square square : current.getSquares()) {
				if (next.equals(square)) {
					notPartOfCurrent = false;
				}
			}

			if (notPartOfCurrent && next.getColor()[0] != 255 && next.getColor()[1] != 255
					&& next.getColor()[2] != 255) {
				return true;
			}
		}
		return false;
	}

	/*
	 * public boolean hitBlockAround(boolean left) { for (Square s :
	 * current.getSquares()) { boolean notPartOfCurrent = true;
	 * 
	 * 
	 * Square next = s;
	 * 
	 * Square[] toCheck = new Square[] { g.getSquare(s.getRowIndex() + 1,
	 * s.getColIndex()), g.getSquare(s.getRowIndex() - 1, s.getColIndex()),
	 * g.getSquare(s.getRowIndex(), s.getColIndex() + 1),
	 * g.getSquare(s.getRowIndex(), s.getColIndex() - 1) };
	 * 
	 * 
	 * int count = 0; for (Square square : current.getSquares()) { for (Square
	 * next : toCheck) { if (next.equals(square)) { notPartOfCurrent = false;
	 * break; } count++; } }
	 * 
	 * if (notPartOfCurrent && toCheck[count].getColor()[0] != 255 &&
	 * toCheck[count].getColor()[1] != 255 && toCheck[count].getColor()[2] !=
	 * 255) { return true; } }
	 * 
	 * return false; }
	 */

	public int getNumOfLines() {
		return numOfLines;
	}

	public void setNumOfLines(int x) {
		numOfLines = x;
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
		if (leftest() == g.getSquare(0, 0).getXCor() || rightest() == g.getSquare(0, g.numCols - 1).getXCor()) {
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

	public boolean part(Square t) {
		return t.partOfCurrentBlock;
	}

	public boolean hitBlock() {
		for (Square s : current.getSquares()) {
			boolean notPartOfCurrent = true;

			Square bottomSquare = g.getSquare(s.getRowIndex() + 1, s.getColIndex());

			for (Square square : current.getSquares()) {
				if (bottomSquare.equals(square)) {
					notPartOfCurrent = false;
				}
			}

			if (notPartOfCurrent && !(bottomSquare.getColor()[0] == 255 && bottomSquare.getColor()[1] == 255
					&& bottomSquare.getColor()[2] == 255)) {
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
			if (s.getXCor() <= lowestX || s.getYCor() > lowestY || s.getXCor() > highestX) {
				return true;
			}
		}
		return false;
	}

	/*
	 * public int calLowestYCorOfTile(Tile t) { int lowest =
	 * t.getSquares().get(0).getYCor(); for (Square s : t.getSquares()) { if
	 * (s.getYCor() < lowest) { lowest = s.getYCor(); } } return lowest; }
	 * 
	 * public int calHighestYCorOfTile(Tile t) { int highest =
	 * t.getSquares().get(0).getYCor(); for (Square s : t.getSquares()) { if
	 * (s.getYCor() > highest) { highest = s.getYCor(); } } return highest; }
	 * 
	 * public int calNextColoredSquare(Tile t) { int[] white = new int[] { 255,
	 * 255, 255 }; return 1; }
	 */

	/**
	 * Sets the squares in an entire row to have a color of white (255,255,255)
	 * 
	 * @param r
	 *            the row to be cleared
	 */
	public void clearLine(int r) {
		int[] white = new int[] { 255, 255, 255 };
		for (int c = 0; c < g.numCols; c++) {
			g.grid[r][c].color = white;
		}

	}

	public boolean clearLine() {
		ArrayList<Integer> toClear = new ArrayList<Integer>();
		int counter = g.getNumCols();
		boolean cleared = false;

		for (int r = g.getNumRows() - 1; r >= 0; r--) {
			counter = g.getNumCols();
			for (int c = 0; c < g.getNumCols(); c++) {
				if (!(g.grid[r][c].color[0] == 255 && g.grid[r][c].color[1] == 255 && g.grid[r][c].color[2] == 255)) {
					counter--;
				}
			}

			if (counter == 0) {
				numOfLines++;
				toClear.add(r);
				cleared = true;
			}
		}

		Collections.reverse(toClear); // do not remove!!!
		
		for (Integer i : toClear) {
			clearLine(i.intValue());
			lowerRow(i.intValue(), 1);
		}

		return cleared;
	}

	public void lowerRow(int row, int num) {
		for (int r = row - num; r >= 0; r--) {
			for (int c = 0; c < g.getNumCols(); c++) {
				g.grid[r + num][c].setColor(g.grid[r][c].getColor());
			}
		}
	}

	/*
	 * public void clearLine1() { boolean gapBetweenLines; int numOfLines = 0;
	 * int[] white = new int[] { 255, 255, 255 }; for (int r = 0; r <
	 * g.getNumRows(); r++) { gapBetweenLines = false; for (int s = 0; s <
	 * g.getNumCols(); s++) { if (g.grid[r][s].color == white) { gapBetweenLines
	 * = true; break; } } if (!gapBetweenLines) { lowerRow(r); r --;
	 * numOfLines++; } } int n = numOfLines; score.setScore(score.getScore() +
	 * 40 * (n + 1) + 100 * (n + 1) + 300 * (n + 1) + 1200 * (n + 1)); }
	 */

	/*
	 * public boolean hit() { int[] white = new int[] { 255, 255, 255 }; int
	 * lowX = current.getSquares().get(0).getColIndex(); int lowY =
	 * current.getSquares().get(0).getRowIndex(); int highX =
	 * current.getSquares().get(0).getColIndex(); int highY =
	 * current.getSquares().get(0).getRowIndex(); for (Square s:
	 * current.getSquares()) { if (s.getColIndex() < lowX) { } } return false; }
	 */
}