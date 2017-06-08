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
	public static int SPEED = 1;
	private static int FRAMERATE = 60;
	public static int SCORE;
	private Tile saved;
	private boolean savedTile;
	private int numAllowedShifted;
	private int level;
	private int totalLinesCleared;

	public Rules() {
		TIMER = 0;
		SCORE = 0;
	}

	public Rules(Colorizer colorizer, Tile current, Grid g) {
		this();
		this.colorizer = colorizer;
		this.current = current;
		this.g = g;
	}
	
	public int getSpeed() {
		return SPEED;
	}
	
	public void setSpeed(int speed) {
		SPEED = speed;
	}

	public void setFR(int framerate) {
		FRAMERATE = framerate;
	}

	public void run() {
		// Polynomial regression
		int run_period = (int) ((-0.0235 * Math.pow(SPEED, 3) + 0.69
				* Math.pow(SPEED, 2) - 7.85 * SPEED + 35) * (FRAMERATE / 60.0)) + 1;

		if (TIMER % run_period == 0) {
			if (hitBottom()) {
				if (clearLine()) {
					updateScore();
				}
				current = colorizer.spawnBlock();
				numAllowedShifted = 0;
			} else if (hitBlock()) {
				if (clearLine()) {
					updateScore();
				}
				current = colorizer.spawnBlock();
				numAllowedShifted = 0;
			} else {
				current = colorizer.drop(current, 1);
			}
		}

		TIMER++;
	}

	public Tile storeShifted() {
		int[] WHITE = { 255, 255, 255 };
		if (numAllowedShifted == 0) {
			if (!savedTile) {
				saved = current;
				// current.setBlock(current.blockType(), false);
				current.pivotY = 0;
				current.pivotX = g.getNumCols() / 2 - 1;
				int numSquares = current.getSquares().size();
				while (numSquares-- > 0) {
					current.remove().setColor(WHITE);
				}
				current = colorizer.spawnBlock();
				savedTile = true;
				numAllowedShifted++;
			} else {
				Tile temp1 = new Tile();
				temp1 = saved;
				saved = current;
				// current.setBlock(current.blockType(), false);
				saved.pivotY = 0;
				saved.pivotX = g.getNumCols() / 2 - 1;
				int numSquares = current.getSquares().size();
				while (numSquares-- > 0) {
					current.remove().setColor(WHITE);
				}
				current = temp1;
				numAllowedShifted++;
			}
		}
		return current;
	}

	public void updateScore() {
		levelUp();
		int n = numOfLines;
		if (n == 1) {
			SCORE += 40 * (level + 1);
		} else if (n == 2) {
			SCORE += 100 * (level + 1);
		} else if (n == 3) {
			SCORE += 300 * (level + 1);
		} else {
			SCORE += 1200 * (level + 1);
		}
		totalLinesCleared += numOfLines;
		numOfLines = 0;
	}

	public void levelUp() {
		// System.out.println("Level: " + level);
		// System.out.println("Total lines cleared: " + totalLinesCleared);
		if (level == 0) {
			if (totalLinesCleared == 4) {
				//SPEED += 1;
				level += 1;
			}
		} else {
			if (totalLinesCleared == level * 4) {
				//SPEED += 1;
				level += 1;
			}
		}
	}

	public void registerKeyPress(int keyCode) {
		if (keyCode == RIGHT) {
			if (!hitSides() && !hitBlockSide(false)) {
				current = colorizer.moveRight(current);
			}
		} else if (keyCode == LEFT) {
			if (!hitSides() && !hitBlockSide(true)) {
				current = colorizer.moveLeft(current);
			}
		} else if (keyCode == UP) {
			if (!hitSides() && !hitBlockSide(false)) {
				current = colorizer.rotate(false, current, 1);
			}
		} else if (keyCode == DOWN) {
			if (!hitSides() && !hitBlockSide(false)) {
				current = colorizer.rotate(true, current, 1);
			}
		} else if (keyCode == SHIFT) {
			current = storeShifted();
		} else if (keyCode == ' '){
			current = fullDrop();
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

			if (notPartOfCurrent && Colorizer.isColored(next)) {
				return true;
			}
		}
		return false;
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

	public boolean hitBlock() {
		for (Square s : current.getSquares()) {
			boolean notPartOfCurrent = true;

			Square bottomSquare = g.getSquare(s.getRowIndex() + 1,
					s.getColIndex());

			for (Square square : current.getSquares()) {
				if (bottomSquare.equals(square)) {
					notPartOfCurrent = false;
				}
			}

			if (notPartOfCurrent
					&& Colorizer.isColored(bottomSquare)) {
				return true;
			}
		}

		return false;
	}

	public Tile fullDrop() {
		while (!hitBottom() && !hitBlock()) {
			current = colorizer.drop(current, 1);
		}
		return current;
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
				if (!(g.grid[r][c].color[0] == 255
						&& g.grid[r][c].color[1] == 255 && g.grid[r][c].color[2] == 255)) {
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
}