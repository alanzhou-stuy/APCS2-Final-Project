package main;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;

public class Rules extends PApplet {
	private Tile current;
	private Colorizer colorizer;
	private Grid g;
	private GridAnalyzer analyzer;
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
	public boolean GAME_OVER = false;

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

	/*
	 * public void gameOver() { if (!GAME_OVER) { for (int i = 0; i < 3; i++) {
	 * for (int j = 0; j < g.numCols; j++) { if (g.grid[i][j].color[0] != 255 &&
	 * !g.grid[i][j].partOfCurrentBlock) { GAME_OVER = true;
	 * 
	 * } } } } }
	 */

	public void setAnalyzer(GridAnalyzer analyzer) {
		this.analyzer = analyzer;
	}

	public void run(boolean computer) {
		// Time determined by polynomial regression
		int run_period = (int) ((-0.0235 * Math.pow(SPEED, 3) + 0.69 * Math.pow(SPEED, 2) - 7.85 * SPEED + 35)
				* (FRAMERATE / 60.0)) + 1;

		if (TIMER % run_period == 0) {
			if ((hitBottom() || hitBlock()) && !GAME_OVER) {
				if (clearLine()) {
					updateScore();
				}

				current = colorizer.spawnBlock();

				if (computer) {
					int[] possible = analyzer.getDirections(analyzer.returnBestPosition());

					for (int move : possible) {
						registerKeyPress(move);
					}
				}

				numAllowedShifted = 0;
			} else if (!GAME_OVER) {
				current = colorizer.drop(current, 1);
			} else {
				System.out.println("GAME OVER!!!");
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
				// SPEED += 1;
				level += 1;
			}
		} else {
			if (totalLinesCleared == level * 4) {
				// SPEED += 1;
				level += 1;
			}
		}
	}

	public void registerKeyPress(int keyCode) {
		if (keyCode == RIGHT) {
			if (!hitRightSide() && !hitBlockSide(false)) {
				current = colorizer.moveRight(current);
			}
		} else if (keyCode == LEFT) {
			if (!hitLeftSide() && !hitBlockSide(true)) {
				current = colorizer.moveLeft(current);
			}
		} else if (keyCode == UP) {
			if (!rotateHitSides(current, false, g) && !rotateHitBlock(current, false, g)) {
				current = Colorizer.rotate(false, current, 1);
			}
		} else if (keyCode == DOWN) {
			if (!rotateHitSides(current, true, g) && !rotateHitBlock(current, true, g)) {
				current = Colorizer.rotate(true, current, 1);
			}
		} else if (keyCode == SHIFT) {
			current = storeShifted();
		} else if (keyCode == ' ') {
			current = fullDrop();
		}
	}

	/*
	 * private boolean hitBlock() { for (Square s : current.getSquares()) {
	 * Square next = g.getSquare(s.getRowIndex() + 1, s.getColIndex());
	 * 
	 * if (Colorizer.isColored(next) && !partOfCurrent(next, current)) { return
	 * true; } }
	 * 
	 * return false; }
	 */

	private boolean hitBlockSide(boolean left) {
		for (Square s : current.getSquares()) {
			Square next;

			if (left) {
				next = g.getSquare(s.getRowIndex(), s.getColIndex() - 1);
			} else {
				next = g.getSquare(s.getRowIndex(), s.getColIndex() + 1);
			}

			if (Colorizer.isColored(next) && !partOfCurrent(next, current)) {
				return true;
			}
		}
		return false;
	}

	public static boolean rotateHitBlock(Tile current, boolean clockwise, Grid g) {
		return rotateHitBlock(current, clockwise, 1, g);
	}

	public static boolean rotateHitBlock(Tile current, boolean clockwise, int numTimes, Grid g) {
		/* Checks to see if rotating block collides with a block */

		ArrayList<int[]> respectiveCoordsSample = new ArrayList<int[]>();

		for (int[] coord : current.getRespectiveCoords()) {
			respectiveCoordsSample.add(Tile.returnTransformedCoords(clockwise, numTimes, coord));
		}

		for (int[] coord : respectiveCoordsSample) {
			if (isInBounds(current.pivotY + coord[0], current.pivotX + coord[1], g)) {
				Square next = g.getSquare(current.pivotY + coord[0], current.pivotX + coord[1]);

				if (Colorizer.isColored(next) && !partOfCurrent(next, current)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean rotateHitBlock(Tile current, boolean clockwise, int numTimes, Grid g, int r, int c) {
		/* Checks to see if rotating block collides with a block */

		ArrayList<int[]> respectiveCoordsSample = new ArrayList<int[]>();

		for (int[] coord : current.getRespectiveCoords()) {
			respectiveCoordsSample.add(Tile.returnTransformedCoords(clockwise, numTimes, coord));
		}

		for (int[] coord : respectiveCoordsSample) {
			if (isInBounds(r + coord[0], c + coord[1], g)) {
				Square next = g.getSquare(r + coord[0], c + coord[1]);

				if (Colorizer.isColored(next) && !partOfCurrent(next, current)) {
					return true;
				}
			}
		}

		return false;
	}

	/* Checks to see if rotating block collides with a side wall */
	public static boolean rotateHitSides(Tile current, boolean clockwise, Grid g) {
		return rotateHitSides(current, clockwise, 1, g);
	}

	public static boolean rotateHitSides(Tile current, boolean clockwise, int numTimes, Grid g) {
		ArrayList<int[]> respectiveCoordsSample = new ArrayList<int[]>();

		for (int[] coord : current.getRespectiveCoords()) {
			respectiveCoordsSample.add(Tile.returnTransformedCoords(clockwise, numTimes, coord));
		}

		for (int[] coord : respectiveCoordsSample) {
			if (!isInBounds(current.pivotY + coord[0], current.pivotX + coord[1], g)) {
				return true;
			}
		}

		return false;
	}

	public static boolean rotateHitSides(Tile current, boolean clockwise, int numTimes, Grid g, int r, int c) {
		ArrayList<int[]> respectiveCoordsSample = new ArrayList<int[]>();

		for (int[] coord : current.getRespectiveCoords()) {
			respectiveCoordsSample.add(Tile.returnTransformedCoords(clockwise, numTimes, coord));
		}

		for (int[] coord : respectiveCoordsSample) {
			if (!isInBounds(r + coord[0], c + coord[1], g)) {
				return true;
			}
		}

		return false;
	}

	public static boolean partOfCurrent(Square toCheck, Tile current) {
		for (Square square : current.getSquares()) {
			if (toCheck.equals(square)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInBounds(int row, int col, Grid g) {
		return row < g.getNumRows() && row >= 0 && col < g.getNumCols() && col >= 0;
	}

	public static boolean tileInBounds(Tile t, int row, int col, Grid g) {
		boolean inBounds = true;
		for (int[] coords : t.getRespectiveCoords()) {
			if (coords[0] + row < 0 || coords[0] + row >= g.getNumRows()) {
				inBounds = false;
			}

			if (coords[1] + col < 0 || coords[1] + col >= g.getNumCols()) {
				inBounds = false;
			}
		}

		return inBounds;
	}

	public void setNumOfLines(int x) {
		numOfLines = x;
	}

	private boolean hitBottom() {
		int lowestY = g.getSquare(g.numRows - 1, 0).getYCor();
		for (Square s : current.getSquares()) {
			if (s.getYCor() == lowestY) {
				return true;
			}
		}
		return false;
	}

	private boolean hitLeftSide() {
		return leftest() == g.getSquare(0, 0).getXCor();
	}

	private boolean hitRightSide() {
		return rightest() == g.getSquare(0, g.numCols - 1).getXCor();
	}

	public boolean hitSides() {
		return hitLeftSide() || hitRightSide();
	}

	public int leftest() {
		Square leftest = current.getSquares().get(0);
		for (Square s : current.getSquares()) {
			if (s.getColIndex() < leftest.getColIndex()) {
				leftest = s;
			}
		}
		return leftest.getXCor();
	}

	public int rightest() {
		Square rightest = current.getSquares().get(0);
		for (Square s : current.getSquares()) {
			if (s.getColIndex() > rightest.getColIndex()) {
				rightest = s;
			}
		}
		return rightest.getXCor();
	}

	public ArrayList<Square> lowest() {
		ArrayList<Square> ans = new ArrayList<Square>();
		Square lowest = current.getSquares().get(0);
		for (Square s : current.getSquares()) {
			if (s.getRowIndex() < lowest.getRowIndex()) {
				lowest = s;
			}
		}
		int counter = 0;
		for (Square s : current.getSquares()) {
			if (s.getRowIndex() <= lowest.getRowIndex()) {
				ans.get(counter).setXYCor(s.getYCor(), s.getXCor());
				counter++;
			}
		}
		return ans;
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

			if (notPartOfCurrent && Colorizer.isColored(bottomSquare)) {
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

	public Tile getCurrent() {
		return current;
	}
}