package main;

import processing.core.PApplet;

/**
 * Main class for interacting with the grid; allows for coloring of squares and
 * tiles, as well as conducting their movement and placement throughout.
 */
public class Colorizer extends PApplet {
	private PApplet pApplet;
	private Grid g;

	public Colorizer(Grid g, PApplet pApplet) {
		this.g = g;
		this.pApplet = pApplet;
	}

	public void color(int row, int col, int[] color) {
		g.getSquare(row, col).setColor(color);
	}

	public void refreshGrid() {
		for (Square[] rowOfSquares : g.grid) {
			for (Square s : rowOfSquares) {
				pApplet.fill(s.getColor()[0], s.getColor()[1], s.getColor()[2]);
				pApplet.rect(s.getXCor(), s.getYCor(), s.getSize(), s.getSize());
			}
		}
	}
}