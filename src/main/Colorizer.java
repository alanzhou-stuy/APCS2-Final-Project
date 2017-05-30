package main;

import processing.core.PApplet;
import java.util.Random;

/**
 * Allows for the interaction with the Grid and provides for coloring of squares
 * and tiles, as well as conducting their movement and placement throughout.
 */
public class Colorizer extends PApplet implements Displayable {
	private PApplet pApplet;
	private Grid g;
	public int numRows, numCols;
	public int tileSep = 2;
	private int[] color;

	/**
	 * @param grid
	 *            Grid of which Colorizer obtains the information to be
	 *            displayed onto the visual grid
	 * @param pApplet
	 *            PApplet in which Colorizer has the ability to change visual
	 *            aspects
	 */
	public Colorizer(Grid g, PApplet pApplet) {
		this.g =  g;
		this.pApplet = pApplet;
		numRows = g.getNumRows();
		numCols = g.getNumCols();
	}
	
	public void setTileSep(int tileSep){
		this.tileSep = tileSep;
	}

	public void color(int row, int col, int[] color) {
		g.getSquare(row, col).setColor(color);
	}
	
	public void spawnBlock() {
		Random rand = new Random();
		int x = rand.nextInt(7); 
		if (x == 0) {
			spawnJBlock();	
		}	 
		else if(x == 1) {
			spawnIBlock();
		}
		else if(x == 2){
			spawnLBlock();
		}
		else if(x == 3) {
			spawnSBlock();
		}
		else if(x == 4){
			spawnTBlock();
		}
		else if(x == 5) {
			spawnZBlock();
		}
		else if (x == 6) {
			spawnOBlock();
		}
	}

	@Override
	public void refresh() {
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
	
	public Tile spawnIBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setIBlock();
		color = (new int[] { 189, 219, 249});
		t.setColor(color);
		return t;
	}
	
	public Tile spawnJBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setJBlock();
		color = new int[] {47, 0, 252};
		t.setColor(color);	
		return t;
	}
	
	public Tile spawnSBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setSBlock();
		color = new int[] {0, 255, 55};
		t.setColor(color);	
		return t;
	}
	
	public Tile spawnOBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setOBlock();
		color = new int[] {212, 243, 48};
		t.setColor(color);
		return t;
	}
	
	public Tile spawnTBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setTBlock();
		color = new int[] {40, 10, 62};
		t.setColor(color);
		return t;
	}
	
	public Tile spawnZBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setZBlock();
		color = new int[] {254, 0, 0};
		t.setColor(color);
		return t;
	}
	
	public Tile spawnLBlock() {
		Tile t = new Tile(g, 0, g.getNumCols() / 2 - 1);
		t.setJBlock();
		color = new int[] {255,165,0};
		t.setColor(color);
		return t;
	}
	
	public Tile drop(Tile t) {
		t.setPivotY(-1);
		Tile t1 = new Tile(g,t.getPivotX(),t.getPivotY());
		t.remove();
		t1.setBlock(t.blockType());
		t1.setColor(color);
		return t1;
	}
	
	public boolean hitBottom(Tile t) {
		int lowestYCor = t.getPivotY();
		for (Square s: t.squares){
			if (s.getPivotY() < lowestYCor) {
				lowestYCor = s.getPivotY();
			}
		}
		return (lowestYCor == 0);	
	}
	
	
}