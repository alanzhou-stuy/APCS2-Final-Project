package main;

import processing.core.PApplet;
import java.util.LinkedList;
import java.util.Random;

/**
 * Allows for the interaction with the Grid and provides for coloring of squares
 * and tiles, as well as conducting their movement and placement throughout.
 */
public class Colorizer extends PApplet implements Displayable {
	private PApplet pApplet;
	private Grid g;
	private LinkedList<Tile> existingTiles;
	public Tile current;
	public int numRows, numCols;
	public int tileSep = 1; // default is 1

	/**
	 * @param grid
	 *            Grid of which Colorizer obtains the information to be
	 *            displayed onto the visual grid
	 * @param pApplet
	 *            PApplet in which Colorizer has the ability to change visual
	 *            aspects
	 * @param current
	 */
	public Colorizer(Grid g, PApplet pApplet) {
		this.g = g;
		this.pApplet = pApplet;
		numRows = g.getNumRows();
		numCols = g.getNumCols();
		current = new Tile();
	}

	public void setTileSep(int tileSep) {
		this.tileSep = tileSep;
	}

	public void color(int row, int col, int[] color) {
		g.getSquare(row, col).setColor(color);
	}

	public Tile spawnBlock() {
		Random rand = new Random();
		int x = rand.nextInt(7);
		switch (x) {
		case 0:
			return spawnJBlock();
		case 1:
			return spawnIBlock();
		case 2:
			return spawnLBlock();
		case 3:
			return spawnSBlock();
		case 4:
			return spawnTBlock();
		case 5:
			return spawnZBlock();
		case 6:
			return spawnOBlock();
		}
		return spawnIBlock();
	}

	@Override
	public void refresh() {
		g.setDimensions(pApplet.width, pApplet.height, tileSep);
		g.updateGrid(numRows, numCols);
		for (Square[] rowOfSquares : g.grid) {
			for (Square s : rowOfSquares) {
				pApplet.fill(s.getColor()[0], s.getColor()[1], s.getColor()[2]);
				pApplet.rect(s.getXCor(), s.getYCor(), s.getSize(), s.getSize());
			}
		}
	}

	@Override
	public void create() {
		g.setDimensions(pApplet.width, pApplet.height, tileSep);
		g.loadGrid();
		for (Square[] rowOfSquares : g.grid) {
			for (Square s : rowOfSquares) {
				pApplet.fill(s.getColor()[0], s.getColor()[1], s.getColor()[2]);
				pApplet.rect(s.getXCor(), s.getYCor(), s.getSize(), s.getSize());
			}
		}
	}

	public void setRowsCols(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
	}

	public Tile spawnIBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setIBlock();
		int[] color = (new int[] { 189, 219, 249 });
		t.setColor(color);
		return t;
	}

	public Tile spawnJBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setJBlock();
		int[] color = new int[] { 47, 0, 252 };
		t.setColor(color);
		return t;
	}

	public Tile spawnSBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setSBlock();
		int[] color = new int[] { 0, 255, 55 };
		t.setColor(color);
		return t;
	}

	public Tile spawnOBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setOBlock();
		int[] color = new int[] { 212, 243, 48 };
		t.setColor(color);
		return t;
	}

	public Tile spawnTBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setTBlock();
		int[] color = new int[] { 40, 10, 62 };
		t.setColor(color);
		return t;
	}

	public Tile spawnZBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setZBlock();
		int[] color = new int[] { 254, 0, 0 };
		t.setColor(color);
		return t;
	}

	public Tile spawnLBlock() {
		Tile t = new Tile(g, 1, g.getNumCols() / 2 - 1);
		t.setLBlock();
		int[] color = new int[] { 255, 165, 0 };
		t.setColor(color);
		return t;
	}

	public Tile rotateRight(Tile t, int numOfTimes) {
		return t;
	}

	public Tile rotateLeft(Tile t, int numOfTimes) {
		return t;
	}

	public Tile moveLeft(Tile t) {
		int[] white = new int[] { 255, 255, 255 };
		int y = 0;
		int size1 = t.getSquares().size();
		while (y < size1) {
			t.remove().setColor(white);
			y++;
		}
		Tile t1 = new Tile(g, t.getPivotY(), t.getPivotX() - 1);
		t1.setBlock(t.blockType());
		t1.setColor(t.getColor());
		return t1;
	}

	public Tile moveRight(Tile t) {
		int[] white = new int[] { 255, 255, 255 };
		int y = 0;
		int size1 = t.getSquares().size();
		while (y < size1) {
			t.remove().setColor(white);
			y++;
		}
		Tile t1 = new Tile(g, t.getPivotY(), t.getPivotX() + 1);
		t1.setBlock(t.blockType());
		t1.setColor(t.getColor());
		return t1;
	}

	public Tile drop(Tile t, int numberOfDrop) {
		int[] white = new int[] { 255, 255, 255 };
		// t.setPivotY(-1);
		int x = 0;
		int y = 0;
		int size1 = t.getSquares().size();
		while (x < numberOfDrop) {
			y = 0;
			size1 = t.getSquares().size();
			while (y < size1) {
				t.remove().setColor(white);
				y++;
				// System.out.println("Calls remove");
			}
			// while (!hitBottom(t)) {
			Tile t1 = new Tile(g, t.getPivotY() + 1, t.getPivotX());
			t1.setBlock(t.blockType());
			t1.setColor(t.getColor());
			// System.out.println("Calls remove");
			t = t1;
			x++;
		}
		return t;
	}

	/*
	 * public void fall(Tile t) { while (current.getPivotY() < 16) { current =
	 * drop(t); current.setPivotY(t.getPivotY() + 1); } }
	 */

	/*
	 * public boolean hitBottom(Tile t) { int lowestYCor =
	 * t.getSquares().get(0).getYCor(); for (Square s : t.getSquares()) { if
	 * (s.getYCor() < lowestYCor) { lowestYCor = s.getYCor(); } if (lowestYCor
	 * == 0) { return true; } } return false; }
	 */
}