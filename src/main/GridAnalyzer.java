package main;

/**
 * Analyzes the grid to check for the best and worst tiles that will fit.
 *
 */
public class GridAnalyzer {
	private Grid g;
	private Rules rule;

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

	/*
	 * Returns number of rows in a column which must be filled to a degree up to
	 * the topmost colored row (EXCLUDING those which are already 'buried')
	 */
	public int getColUnfilled(int col) {
		int numUnfilled = 0;

		for (int r = getTopMostColoredRow(); r < g.getNumRows(); r++) {
			if (Colorizer.isColored(g.getSquare(r, col))) {
				break;
			} else {
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
	
	public void DEBUG(){
		for(int i : getColInformation()){
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public boolean twoConsecutive(int row){
		for (int c = 0; c < g.getNumCols() - 1; c++) {
			if (g.grid[c][row].color[0] == g.grid[c + 1][row].color[0]
					&& g.grid[c][row].color[0] == 255) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Returns relative strength or capability of a tile based on current grid
	 * layout
	 */
	public double getTileStrength(Tile t) {
		return 0.0;
	}
}