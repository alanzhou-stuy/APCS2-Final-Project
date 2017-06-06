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
	// private LinkedList<Tile> existingTiles;
	public Tile current;
	public int numRows, numCols;
	public int tileSep = 1; // default is 1
	public static int[] WHITE = { 255, 255, 255 };
	public Rules r;

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
		r = new Rules();
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

		return null;
	}

	@Override
	public void refresh() {
		g.setDimensions(pApplet.width, pApplet.height, tileSep);
		g.updateGrid(numRows, numCols);
		g.loadGrid();
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
		g.updateGrid(numRows, numCols);
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
		Tile t = new Tile(g, 1, g.getNumCols() / 2 - 1);
		t.setIBlock(true);
		int[] color = (new int[] { 189, 219, 249 });
		t.setColor(color);
		return t;
	}

	public Tile spawnJBlock() {
		Tile t = new Tile(g, 1, g.getNumCols() / 2 - 1);
		t.setJBlock(true);
		int[] color = new int[] { 47, 0, 252 };
		t.setColor(color);
		return t;
	}

	public Tile spawnSBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setSBlock(true);
		int[] color = new int[] { 0, 255, 55 };
		t.setColor(color);
		return t;
	}

	public Tile spawnOBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setOBlock(true);
		int[] color = new int[] { 212, 243, 48 };
		t.setColor(color);
		return t;
	}

	public Tile spawnTBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setTBlock(true);
		int[] color = new int[] { 153, 51, 255 }; // 40 10 62
		t.setColor(color);
		return t;
	}

	public Tile spawnZBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setZBlock(true);
		int[] color = new int[] { 254, 0, 0 };
		t.setColor(color);
		return t;
	}

	public Tile spawnLBlock() {
		Tile t = new Tile(g, 1, g.getNumCols() / 2 - 1);
		t.setLBlock(true);
		int[] color = new int[] { 255, 165, 0 };
		t.setColor(color);
		return t;
	}

	// eventually add EDGE DETECTION for moveLeft and moveRight
	public Tile moveLeft(Tile t) {
		int y = 0;
		int size1 = t.getSquares().size();
		while (y < size1) {
			t.remove().setColor(WHITE);
			y++;
		}
		Tile t1 = new Tile(g, t.getPivotY(), t.getPivotX() - 1);
		t1.setRespectiveCoords(t.getRespectiveCoords());
		t1.setBlock(t.blockType(), false);
		t1.setColor(t.getColor());
		return t1;
	}

	public Tile moveRight(Tile t) {
		int y = 0;
		int size1 = t.getSquares().size();
		while (y < size1) {
			t.remove().setColor(WHITE);
			y++;
		}
		Tile t1 = new Tile(g, t.getPivotY(), t.getPivotX() + 1);
		t1.setRespectiveCoords(t.getRespectiveCoords());
		t1.setBlock(t.blockType(), false);
		t1.setColor(t.getColor());
		return t1;
	}
	
	public boolean colored(int y, int x) {
		return g.grid[y][x].color != WHITE; 
	}
	
	

	/**
	 * Rotates a tile either clockwise or counter clockwise. It utilizes the
	 * respectiveCoords of a square, creates a coordinate transformation, and
	 * then reassigns the grid values
	 * 
	 * @param left
	 *            if true, rotate left, otherwise, rotate right
	 * @param t
	 *            tile that is to be rotated
	 * @param numTimes
	 *            number of times to rotate the tile
	 * @return the tile with the rotated coordinates in the grid
	 */
	public Tile rotate(boolean clockwise, Tile t, int numTimes) {
		while (numTimes-- > 0) {
			int numSquares = t.getSquares().size();

			// Adds new coords, then removes old ones!!!

			for (int i = 0; i < numSquares; i++) {
				int[] coord = t.respectiveCoords.get(i);
				t.addRespectiveCoord(Tile.returnTransformedCoords(clockwise, coord));
			}
			
			while (numSquares-- > 0) {
				t.respectiveCoords.remove(0);
				t.remove().setColor(WHITE);
			}
			
			t.setBlock(t.blockType(), false);
			t.setColor(t.getColor());
		}

		return t;
	}

	public Tile drop(Tile t, int amount) {
		int numSquares = t.getSquares().size();

		while (amount-- > 0) {
			numSquares = t.getSquares().size();

			while (numSquares-- > 0) {
				t.remove().setColor(WHITE);
			}

			Tile t1 = new Tile(g, t.getPivotY() + 1, t.getPivotX());
			t1.setRespectiveCoords(t.getRespectiveCoords());
			t1.setBlock(t.blockType(), false);
			t1.setColor(t.getColor());
			t = t1;
			for (Square s: t.getSquares()) {
				s.setPartOfCurrentBlock(true);
			}
		}
		
		return t;
	}
	
	public Tile drop(Tile t) {
		return drop(t, r.calNextColoredSquare(t));
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