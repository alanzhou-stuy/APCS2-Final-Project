package main;

import java.util.ArrayList;

/**
 * Analyzes the grid to check for the best and worst tiles that will fit.
 *
 */
public class GridAnalyzer {
	private Grid g;
	private Rules rule;
	private static boolean DYNAMIC = false;

	private static double HEIGHT_PRIORITY = 0.5;
	private static double FILL_ROW_PRIORITY = 0.5;

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

		if (rule.isInBounds(lowestRowIndex, col)) {
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
			if (rule.isInBounds(r, col) && Colorizer.isColored(g.getSquare(r, col))
					&& !Rules.partOfCurrent(g.getSquare(r, col), rule.getCurrent())) {
				break;
			} else if (rule.isInBounds(r, col)) {
				numUnfilled++;
			}
		}

		return numUnfilled;
	}

	/*
	 * Returns an array containing # of squares unfilled for each column
	 */
	public int[] getColInformation() {
		int[] colInfo = new int[g.getNumCols()];

		for (int c = 0; c < g.getNumCols(); c++) {
			colInfo[c] = getColUnfilled(c);
		}

		return colInfo;
	}

	public int[] getRowInformation() {
		int[] rowInfo = new int[g.getNumRows() - getTopMostColoredRow()];

		int numFilled = 0;
		for (int r = getTopMostColoredRow(); r < g.getNumRows(); r++) {
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
		for (int i : getColInformation()) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	// returns number of squares that don't overlap existing ones
	public int getFit(Tile t, int row, int col) {
		int count = 0;

		for (int[] coord : t.getRespectiveCoords()) {
			if (rule.isInBounds(row + coord[0], col + coord[1])
					&& !Colorizer.isColored(g.getSquare(row + coord[0], col + coord[1]))) {
				count++;
			}
		}

		return count;
	}

	// returns an array containing horizontal key movements to get tile into
	// best position
	public int[] getMovement() {
		/*
		 * KeyCodes left - 37 up - 38 right - 39 down - 40
		 */

		int diff = getClosestDifference(rule.getCurrent().getPivotX(), getIndices(getColInformation(), true));

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

		int[] directions = new int[diff + pos[2]];

		if (Math.abs(diff) != diff) {
			for (int i = 0; i < directions.length - pos[2]; i++) {
				directions[i] = 39;
			}
		} else {
			for (int i = 0; i < directions.length - pos[2]; i++) {
				directions[i] = 37;
			}
		}

		for (int i = pos[2]; i >= 0; i--) {
			directions[directions.length-1-i] = 38;
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
	public int[] returnBestPosition() {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		int[] pos = new int[3]; // first element is row, second is col,
								// third is # rotations
		int toPass = rule.getCurrent().getSquares().size();

		/*
		 * Go through columns and start from lowest uncolored square up,
		 * checking for requirements
		 */
		int top = getTopMostColoredRow();

		for (int c = 0; c < g.getNumCols(); c++) {
			for (int r = getTopMostColoredRow() + getColUnfilled(c) - 1; r >= getTopMostColoredRow(); r--) {
				// check to see if fit
				Tile t = rule.getCurrent();

				for (int i = 0; i < rule.getCurrent().getNumPhases(); i++) {
					if (getFit(rule.getCurrent(), r, c) == rule.getCurrent().getSquares().size()) {

						candidates.add(new Candidate(rule.getCurrent(), i, rule.getCurrent().getPivotX(),
								rule.getCurrent().getPivotY()));

						// if tile touches wall += 6.5
						// if tile touches another block += 4
						// if tile completes row += 1.6
						// if tile touches floor += 0.7

						// if tile adds height -= 3.8 (depends)
						// if tile creates hole -= 2.3
						// if tile blockades -= 0.6
						//
					}

					//if(isInBounds(Colorizer.rotate(true, t, 1).get
					//t = Colorizer.rotate(true, t, 1);
				}
			}
		}

		return new int[] { candidates.get(0).pivotYIndex, candidates.get(0).pivotXIndex,
				candidates.get(0).numRotations };
	}

	public boolean touchesWall(Tile t) {
		return false;
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