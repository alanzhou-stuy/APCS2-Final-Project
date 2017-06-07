package main;

/**
 * Analyzes the grid to check for the best and worst tiles that will fit.
 *
 */
public class GridAnalyzer {
	private Grid g;

	public GridAnalyzer(Grid g) {
		this.g = g;
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
				if(Colorizer.isColored(g.grid[r][c])){
					topRow = r;
					stop = true;
				}
			}
		}

		return topRow;
	}

	/*
	 * Returns relative strength or capability of a tile based on current grid
	 * layout
	 */
	public double getTileStrength(Tile t) {
		return 0.0;
	}
}