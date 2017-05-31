package main;

/**
 * Class for the grid in the Main frame. Contains a 2-D array of Squares. Grid
 * has no ability to change visuals in the Main frame directly. Instead, all
 * updates visually must take place through the Colorizer class
 */
public class Grid {
	public Square[][] grid;
	public int numRows, numCols;
	public int width, height, squareSep, squareSize;
	public int sideMargin, vertMargin;
	
	public Grid(int numRows, int numCols) {
		grid = new Square[numRows][numCols];
		this.numRows = numRows;
		this.numCols = numCols;
	}

	/**
	 * Sets the dimensions of the grid
	 * 
	 * @param width
	 *            width of grid in pixels
	 * @param height
	 *            height of grid in pixels
	 * @param squareSep
	 *            separation of squares in pixels
	 */
	public void setDimensions(int width, int height, int squareSep) {
		this.width = width;
		this.height = height;
		this.squareSep = squareSep;
		
		if ((width / (numCols + 3)) <= (height / (numRows + 3))) {
			squareSize = width / (numCols + 3);
		} else {
			squareSize = height / (numRows + 3);
		}

		sideMargin = (width - numCols * squareSize - (numCols - 1) * squareSep) / 2;
		vertMargin = (height - numRows * squareSize - (numRows - 1) * squareSep) / 2;
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