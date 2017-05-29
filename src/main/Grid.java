package main;

/**
 * Class for the grid in the Main frame. Contains a 2-D array of Squares.
 */
public class Grid {
	public Square[][] grid;
	public int numRows, numCols;
	public int width, height, squareSep, squareSize;

	public Grid(int numRows, int numCols) {
		grid = new Square[numRows][numCols];
		this.numRows = numRows;
		this.numCols = numCols;
	}

	public void setDimensions(int width, int height, int squareSep) {
		this.width = width;
		this.height = height;
		this.squareSep = squareSep;
	}

	public Square getSquare(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Initializes the grid with squares, each of a default color setting
	 */
	public void loadGrid() {
		if((width / (numCols + 3)) <= (height / (numRows + 3))){
			squareSize = width / (numCols + 3);
		} else {
			squareSize = height / (numRows + 3);
		}
		
		int sideMargin = (width - numCols * squareSize - (numCols - 1) * squareSep) / 2;
		int vertMargin = (height - numRows * squareSize - (numRows - 1) * squareSep) / 2;

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