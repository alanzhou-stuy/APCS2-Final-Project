package main;

import processing.core.PApplet;

/**
 * Sets up the most important elements in the frame, including the grid, score
 * count, and menu options (TBF)
 */
public class Setup extends PApplet {
	private PApplet pApplet;
	private int numRows, numCols;
	private final int tileSep = 2;

	public Setup(PApplet pApplet) {
		this.pApplet = pApplet;
	}

	public void setRowCount(int numRows) {
		this.numRows = numRows;
	}

	public void setColCount(int numCols) {
		this.numCols = numCols;
	}

	/**
	 * Creates a grid of tiles, each calculated for an optimal fit onto the
	 * board
	 */
	public void createGrid() {
		Grid g = new Grid(numRows, numCols);
		g.setDimensions(pApplet.width, pApplet.height, tileSep);
		g.loadGrid();

		for (Square[] rowOfSquares : g.grid) {
			for (Square s : rowOfSquares) {
				pApplet.fill(s.getColor()[0], s.getColor()[1], s.getColor()[2]);
				pApplet.rect(s.getXCor(), s.getYCor(), s.getSize(), s.getSize());
			}
		}
	}

	public void createScore() {
	}
}