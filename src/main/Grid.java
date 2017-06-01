package main;

import processing.core.PApplet;

/**
 * Class for the grid in the Main frame. Contains a 2-D array of Squares. Grid
 * has no ability to change visuals in the Main frame directly. Instead, all
 * updates visually must take place through the Colorizer class
 */
public class Grid {
	public Square[][] grid;
	public int numRows, numCols;
	public int pAppletWidth, pAppletHeight, squareSep, squareSize;
	public int sideMargin, vertMargin;

	public Grid(int numRows, int numCols) {
		grid = new Square[numRows][numCols];
		this.numRows = numRows;
		this.numCols = numCols;
	}

	/**
	 * Sets the dimensions of the grid
	 * 
	 * @param pAppletWidth
	 *            width of grid in pixels
	 * @param pAppletHeight
	 *            height of grid in pixels
	 * @param squareSep
	 *            separation of squares in pixels
	 */
	public void setDimensions(int pAppletWidth, int pAppletHeight, int squareSep) {
		this.pAppletWidth = pAppletWidth;
		this.pAppletHeight = pAppletHeight;
		this.squareSep = squareSep;

		if (((pAppletWidth / (numCols)*.75) <= ((pAppletHeight / (numRows)*.75)))){
			//squareSize = pAppletWidth / (numCols + 3);
			squareSize = (int)((pAppletWidth / numCols) * .85);
		} else {
			//squareSize = pAppletHeight / (numRows + 3);
			squareSize = (int)((pAppletHeight / numRows) * .85);
		}

		sideMargin = ((pAppletWidth - numCols * squareSize - (numCols - 1) * squareSep) / 2);
		vertMargin = ((pAppletHeight - numRows * squareSize - (numRows - 1) * squareSep) / 2);
		//sideMargin = (int)(pAppletWidth * .3);
		//vertMargin = (int)(pAppletHeight * .1);
	}

	/**
	 * Updates grid if the number of rows and columns are changed
	 * 
	 * @param newRows
	 * @param newCols
	 */
	public void updateGrid(int newRows, int newCols) {
		System.out.println(newRows + ",  " + newCols);
		
		Square[][] gridNew = new Square[newRows][newCols];
		for (int r = 0; r < newRows; r++) {
			for (int c = 0; c < newCols; c++) {
				if (r < numRows && c < numCols) {
					gridNew[r][c] = grid[r][c];
				} else {
					gridNew[r][c] = new Square();
					gridNew[r][c].setColor(new int[]{255,255,255});
				}
			}
		}

		numRows = newRows;
		numCols = newCols;
		this.grid = gridNew;
	}

	public Square getSquare(int row, int col) {
		return grid[row][col];
	}
	
	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	/**
	 * Initializes the grid with squares, each of a default color setting
	 */
	public void loadGrid() {
		int rowIncrement = 0;
		for (int r = 0; r < numRows; r++) {

			int colIncrement = 0;
			for (int c = 0; c < numCols; c++) {
				grid[r][c] = new Square(sideMargin + colIncrement, vertMargin + rowIncrement, squareSize,
						new int[] { 255, 255, 255 });
				colIncrement += squareSize + squareSep;
			}

			rowIncrement += squareSize + squareSep;
		}
	}
}