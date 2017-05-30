package main;

import processing.core.PApplet;

/**
 * Allows for the interaction with the Grid and provides for coloring of squares
 * and tiles, as well as conducting their movement and placement throughout.
 */
public class Colorizer extends PApplet implements Displayable {
	private PApplet pApplet;
	private Grid grid;
	public int numRows, numCols;
	public int tileSep = 2;

	/**
	 * @param grid
	 *            Grid of which Colorizer obtains the information to be
	 *            displayed onto the visual grid
	 * @param pApplet
	 *            PApplet in which Colorizer has the ability to change visual
	 *            aspects
	 */
	public Colorizer(Grid grid, PApplet pApplet) {
		this.grid = grid;
		this.pApplet = pApplet;
		
		numRows = grid.getNumRows();
		numCols = grid.getNumCols();
	}
	
	public void setTileSep(int tileSep){
		this.tileSep = tileSep;
	}

	public void color(int row, int col, int[] color) {
		grid.getSquare(row, col).setColor(color);
	}

	@Override
	public void refresh() {
		for (Square[] rowOfSquares : grid.grid) {
			for (Square s : rowOfSquares) {
				pApplet.fill(s.getColor()[0], s.getColor()[1], s.getColor()[2]);
				pApplet.rect(s.getXCor(), s.getYCor(), s.getSize(), s.getSize());
			}
		}
	}

	@Override
	public void create() {
		grid.setDimensions(pApplet.width, pApplet.height, tileSep);
		grid.loadGrid();

		for (Square[] rowOfSquares : grid.grid) {
			for (Square s : rowOfSquares) {
				pApplet.fill(s.getColor()[0], s.getColor()[1], s.getColor()[2]);
				pApplet.rect(s.getXCor(), s.getYCor(), s.getSize(), s.getSize());
			}
		}
	}
}