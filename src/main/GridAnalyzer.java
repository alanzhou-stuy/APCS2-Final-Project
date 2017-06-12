package main;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Analyzes the grid to check for the best and worst tiles that will fit.
 *
 */
public class GridAnalyzer {
	private Grid g;
	private Rules rule;
	private static boolean DYNAMIC = false;

	public GridAnalyzer(Grid g) {
		this.g = g;
	}

	public void setRule(Rules rule) {
		this.rule = rule;
	}

	public int getTotalColoredSquares(int startRow, int endRow) {
		int numColored = 0;

		for (int r = startRow; r <= endRow; r++) {
			for (int c = 0; c < g.getNumCols(); c++) {
				if (Colorizer.isColored(g.grid[r][c])) {
					numColored++;
				}
			}

		}
		return numColored;
	}

	public int getTopMostColoredRow() {
		int topRow = 0;
		boolean stop = false;

		for (int r = 0; r < g.getNumRows() && stop == false; r++) {
			for (int c = 0; c < g.getNumCols() && stop == false; c++) {
				if (Colorizer.isColored(g.grid[r][c]) && !Rules.partOfCurrent(g.grid[r][c], rule.getCurrent())) {
					topRow = r;
					stop = true;
				}
			}
		}

		return topRow;
	}

	public Square getLowestUnfilledSquareInColumn(int col) {
		int lowestRowIndex = rule.getCurrent().getPivotY() + getColUnfilled(col);

		if (Rules.isInBounds(lowestRowIndex, col, g)) {
			return g.getSquare(rule.getCurrent().getPivotY() + getColUnfilled(col), col);
		} else {
			return g.getSquare(g.getNumRows() - 1, col);
		}
	}

	/*
	 * Returns number of rows in a column which must be filled to a degree
	 */
	public int getColUnfilled(int col) {
		int numUnfilled = 0;
		int startRow;

		if (DYNAMIC) {
			startRow = rule.getCurrent().getPivotY();
		} else {
			startRow = getTopMostColoredRow();
		}

		// make this instead getTopMostColoredRow()???
		for (int r = startRow; r < g.getNumRows(); r++) {
			if (Rules.isInBounds(r, col, g) && Colorizer.isColored(g.getSquare(r, col))
					&& !Rules.partOfCurrent(g.getSquare(r, col), rule.getCurrent())) {
				break;
			} else if (Rules.isInBounds(r, col, g)) {
				numUnfilled++;
			}
		}

		return numUnfilled;
	}

	/*
	 * Returns an array containing # of squares unfilled for each column
	 */
	public int[] getColInformation(Grid g) {
		int[] colInfo = new int[g.getNumCols()];

		for (int c = 0; c < g.getNumCols(); c++) {
			colInfo[c] = getColUnfilled(c);
		}

		return colInfo;
	}

	public int[] getRowInformation(Grid g) {
		int[] rowInfo = new int[g.getNumRows()];

		for (int r = 0; r < g.getNumRows(); r++) {
			int numFilled = 0;
			for (int c = 0; c < g.getNumCols(); c++) {
				if (Colorizer.isColored(g.getSquare(r, c))) {
					numFilled++;
				}
			}
			rowInfo[r] = numFilled;
		}

		return rowInfo;
	}

	public void DEBUG() {
		for (int i : getColInformation(g)) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	// returns number of squares that don't overlap existing ones
	public int getFit(Tile t, Grid g, int row, int col) {
		int count = 0;

		for (int[] coord : t.getRespectiveCoords()) {
			if (Rules.isInBounds(row + coord[0], col + coord[1], g)
					&& !Colorizer.isColored(g.getSquare(row + coord[0], col + coord[1]))) {
				count++;
			}
		}

		return count;
	}

	/* Returns HEIGHT up topmost colored in column */
	public int getHeightCol(Grid aggregate, int c, Tile current) {
		if (getTopMostColoredSquare(aggregate, c, current) == -1) {
			return 0;
		} else {
			return aggregate.getNumRows() - getTopMostColoredSquare(aggregate, c, current);
		}
	}

	public int getTopMostColoredSquare(Grid aggregate, int c, Tile current) {
		int first = -1;

		for (int r = 0; r < aggregate.getNumRows(); r++) {
			if (Colorizer.isColored(aggregate.getSquare(r, c)) && !Rules.partOfCurrent(g.getSquare(r, c), current)) {
				first = r;
				break;
			}
		}

		return first;
	}

	// returns an array containing horizontal key movements to get tile into
	// best position
	public int[] getMovement() {
		/*
		 * KeyCodes left - 37 up - 38 right - 39 down - 40
		 */

		int diff = getClosestDifference(rule.getCurrent().getPivotX(), getIndices(getColInformation(g), true));

		System.out.println("DIFF: " + diff);
		int[] movement = new int[Math.abs(diff)];

		if (Math.abs(diff) != diff) {
			for (int i = 0; i < movement.length; i++) {
				movement[i] = 39;
			}
		} else {
			for (int i = 0; i < movement.length; i++) {
				movement[i] = 37;
			}
		}

		return movement;
	}

	/*
	 * Returns keyboard movements to get to position provided
	 */
	public int[] getDirections(int[] pos) {
		int diff = rule.getCurrent().getPivotX() - pos[1];
		System.out.println("Horizontal Move: " + diff);
		System.out.println("Num rotations: " + pos[2]);

		int[] directions = new int[Math.abs(diff) + pos[2]];

		// 0 -> (diff-1)
		// (diff) -> end-1
		if (Math.abs(diff) != diff) {
			for (int i = 0; i < directions.length - pos[2]; i++) {
				directions[i] = 39;
			}
		} else {
			for (int i = 0; i < directions.length - pos[2]; i++) {
				directions[i] = 37;
			}
		}

		if (directions.length != 0) {
			for (int i = 0; i < pos[2]; i++) {
				directions[Math.abs(diff) + i] = 38;
			}
		}

		return directions;
	}

	/*
	 * Requirements for candidate squares:
	 * 
	 * Is in bounds, and can reach position by dropping directly (without tricky
	 * movements) Tries to minimize height (if possible) Tries to complete lines
	 * (for higher score)
	 */
	public Candidate returnBestCandidate() {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		/*
		 * Go through columns and start from lowest uncolored square up,
		 * checking for requirements
		 */

		Tile t;

		for (int c = 0; c < g.getNumCols(); c++) {
			for (int r = g.getNumRows() - 1; r >= 0; r--) {

				ArrayList<Tile> rotes = new ArrayList<Tile>();

				for (int i = 0; i < rule.getCurrent().getNumOfPhases(); i++) {
					try {
						t = new Tile(g, rule.getCurrent(), r, c);
						Grid copy = implant(t, g, r, c);

						if (Rules.tileInBounds(t, r, c, copy) && !Rules.rotateHitSides(t, true, i, copy, r, c)) {
							rotes.add(Colorizer.nonChangingRotate(true, t, i, copy));
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}

				for (Tile rote : rotes) {
					Grid copy = implant(rote, g, rote.pivotY, rote.pivotX);

					if (getFit(rote, g, r, c) == rote.getSquares().size() && tileInDirectLineOfSight(rote, r, c)) {

						int strength = 0;
						int numRotations = 0;

						if (rote.NR != 0) {
							numRotations = rote.NR;
						}

						Candidate cand = new Candidate(rote, numRotations, r, c, strength);

						cand.resultingLC = getCompleteRows(rote, r, c);
						cand.resultingAH = getAggregateHeight(rote, r, c, copy);
						cand.resultingH = numHoles(rote, r, c);
						cand.resultingB = getBumpiness(rote, r, c);

						cand.calculateStrength();
						candidates.add(cand);
					}
				}
			}
		}

		Collections.sort(candidates);

		/*
		 * System.out.println("total: " + getTotalColoredSquares(0,
		 * g.getNumRows() - 1)); System.out.println("Num Candidates: " +
		 * numTimes + " or " + candidates.size());
		 * System.out.println("Strongest: " + candidates.get(0).strength);
		 * System.out.println("Strongest AH: " + candidates.get(0).resultingAH);
		 * System.out.println("Strongest H: " + candidates.get(0).resultingH);
		 * System.out.println("Strongest B: " + candidates.get(0).resultingB);
		 * System.out.println("Strongest LC: " + candidates.get(0).resultingLC);
		 */

		return candidates.get(0);

	}

	public int[] returnBestPosition() {
		Candidate best = returnBestCandidate();

		return new int[] { best.pivotYIndex, best.pivotXIndex, best.numRotations };
	}

	public Grid implant(Tile t, Grid orig, int row, int col) {
		Grid copy = new Grid(orig.getNumRows(), orig.getNumCols());

		for (int r = 0; r < orig.getNumRows(); r++) {
			for (int c = 0; c < orig.getNumCols(); c++) {
				Square origSquare = g.getSquare(r, c);
				if (!Rules.partOfCurrent(origSquare, rule.getCurrent())) {
					copy.grid[r][c] = new Square(r, c, origSquare.color);
				} else {
					copy.grid[r][c] = new Square(r, c, new int[] { 255, 255, 255 });
				}
			}
		}

		for (int[] coord : t.getRespectiveCoords()) {
			if (Rules.isInBounds(row + coord[0], col + coord[1], copy)) {
				copy.grid[row + coord[0]][col + coord[1]].setColor(t.getColor());
			}
		}

		return copy;
	}

	/*
	 * Returns true if there is no square above which is colored
	 */
	public boolean inDirectLineOfSight(Square s) {
		boolean direct = true;
		for (int r = s.getRowIndex() - 1; r >= 0; r--) {
			if (Colorizer.isColored(g.getSquare(r, s.getColIndex()))) {
				direct = false;
			}
		}
		return direct;
	}

	public boolean tileInDirectLineOfSight(Tile t, int r, int c) {
		for (int[] coord : t.getRespectiveCoords()) {
			if (!inDirectLineOfSight(g.getSquare(r + coord[0], c + coord[1]))) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Returns row index of topmost square of tile
	 */
	public int getTopHeight(Tile t, int r, int c) {
		int topHeight = r;

		for (int[] coord : t.getRespectiveCoords()) {
			if (r + coord[1] < topHeight) {
				topHeight = r + coord[1];
			}
		}

		return topHeight;
	}

	public boolean touchesWall(Tile t, int r, int c) {
		for (int[] coord : t.getRespectiveCoords()) {
			if (c + coord[1] == 0 || c + coord[1] == g.getNumCols()) {
				return true;
			}
		}
		return false;
	}

	/* Returns sum of heights in each column with tile implanted */
	public int getAggregateHeight(Tile t, int row, int col, Grid g) {
		int height = 0;
		// Grid copy = implant(t, g, row, col);

		for (int c = 0; c < g.getNumCols(); c++) {
			height += getHeightCol(g, c, t);
		}

		return height;
	}

	public int getBumpiness(Tile t, int row, int col) {
		int bumpiness = 0;
		Grid copy = implant(t, g, row, col);

		for (int c = 0; c < copy.getNumCols() - 2; c++) {
			bumpiness += Math.abs(getHeightCol(copy, c, t) - getHeightCol(copy, c + 1, t));
		}

		return bumpiness;
	}

	public int getCompleteRows(Tile t, int row, int col) {
		int complete = 0;
		Grid copy = implant(t, g, row, col);

		for (int i : getRowInformation(copy)) {
			if (i == copy.getNumCols()) {
				complete++;
			}
		}

		return complete;
	}

	public int numHoles(Tile t, int row, int col) {
		Grid copy = implant(t, g, row, col);
		int holes = 0;

		for (int c = 0; c < copy.getNumCols(); c++) {
			for (int r = copy.getNumRows() - 1; r >= 0; r--) {
				if (!Colorizer.isColored(copy.getSquare(r, c)) && coloredSquareAbove(copy, r, c, t)) {
					holes++;
				}
			}
		}

		return holes;
	}

	public boolean coloredSquareAbove(Grid agg, int row, int col, Tile current) {
		if (getTopMostColoredSquare(agg, col, current) == -1) {
			return false;
		} else {
			return row > getTopMostColoredSquare(agg, col, current);
		}
	}

	/*
	 * Returns array of indices of largest elements in array 'arr'
	 */
	public static ArrayList<Integer> getIndices(int[] arr, boolean max) {
		int toPass = arr[0];
		ArrayList<Integer> indices = new ArrayList<Integer>();

		if (max) {
			for (int i = 1; i < arr.length; i++) {
				if (arr[i] > toPass) {
					toPass = arr[i];
				}
			}
		} else {
			for (int i = 1; i < arr.length; i++) {
				if (arr[i] < toPass) {
					toPass = arr[i];
				}
			}
		}

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == toPass) {
				indices.add(i);
			}
		}

		return indices;
	}

	private static int getClosestDifference(int a, ArrayList<Integer> arr) {
		int closestDiff = a - arr.get(0);

		for (int i = 1; i < arr.size(); i++) {
			if (Math.abs(a - arr.get(i)) < Math.abs(closestDiff)) {
				closestDiff = a - arr.get(i);
			}
		}

		return closestDiff;
	}

	public boolean twoConsecutive(int row) {
		for (int c = 0; c < g.getNumCols() - 1; c++) {
			if (g.grid[c][row].color[0] == g.grid[c + 1][row].color[0] && g.grid[c][row].color[0] == 255) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Returns relative strength or capability of a tile based on current grid
	 * layout. WILL be used for predicting worst tiles
	 */
	public double getTileStrength(Tile t) {
		return 0.0;
	}
}