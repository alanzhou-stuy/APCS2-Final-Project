package main;

import processing.core.PApplet;

public class Setup extends PApplet {
	private PApplet pApplet;
	private int numRows, numCols;
	private final int tileSep = 1;

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
		int tileSize = pApplet.width / (numCols + 3);
		int margin = (pApplet.width - numCols * tileSize - (numCols - 1) * tileSep) / 2;

		int rowIncrement = 0;
		for (int r = 0; r < numRows; r++) {

			int colIncrement = 0;
			for (int c = 0; c < numCols; c++) {
				pApplet.fill(120,120,120);
				pApplet.rect(margin + colIncrement, margin + rowIncrement, tileSize, tileSize);
				colIncrement += tileSize + tileSep;
			}

			rowIncrement += tileSize + tileSep;
		}
	}
	
	public void createScore(){
		
	}
}