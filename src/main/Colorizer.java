package main;

import processing.core.PApplet;
import java.util.Random;

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
	
	public void spawnJBlock() {
		color(0, g.numCols / 2 - 1, new int[] {47, 0, 252});
		color(1, g.numCols / 2 - 1, new int[] { 47, 0, 252 });
		color(1, g.numCols / 2 , new int[] { 47, 0, 252});
		color(1, g.numCols / 2 + 1, new int[] {47, 0, 252});

	}
	
	public void spawnIBlock() {
		color(0, g.numCols / 2 - 2, new int[] { 189, 219, 249});
		color(0, g.numCols / 2 - 1, new int[] { 189, 219, 249 });
		color(0, g.numCols / 2 , new int[] { 189, 219, 249 });
		color(0, g.numCols / 2 + 1, new int[] { 189, 219, 249 });
	}
	
	public void spawnLBlock() {
		color(0, g.numCols / 2 + 1, new int[] { 252, 166, 55});
		color(1, g.numCols / 2 - 1, new int[] { 252, 166, 55 });
		color(1, g.numCols / 2 , new int[] { 252, 166, 55 });
		color(1, g.numCols / 2 + 1, new int[] {252, 166, 55});
	}
	
	public void spawnSBlock() {
		color(0, g.numCols / 2 , new int[] {0, 255, 55});
		color(0, g.numCols / 2 + 1, new int[] {0, 255, 55});
		color(1, g.numCols / 2 , new int[] {0, 255, 55 });
		color(1, g.numCols / 2 - 1, new int[] {0, 255, 55});
	}
	
	public void spawnOBlock() {
		color(0, g.numCols / 2 - 1, new int[] {212, 243, 48});
		color(0, g.numCols / 2 , new int[] {212, 243, 48});
		color(1, g.numCols / 2 - 1, new int[] {212, 243, 48 });
		color(1, g.numCols / 2 , new int[] {212, 243, 48});
	}
	
	public void spawnTBlock() {
		color(0, g.numCols / 2, new int[] {40, 10, 62});
		color(1, g.numCols / 2 + 1, new int[] {40, 10, 62});
		color(1, g.numCols / 2 , new int[] {40, 10, 62});
		color(1, g.numCols / 2 - 1, new int[] {40, 10, 62});
	}
	
	public void spawnZBlock() {
		color(0, g.numCols / 2 , new int[] {254, 0, 0});
		color(0, g.numCols / 2 - 1, new int[] {254, 0, 0});
		color(1, g.numCols / 2 , new int[] {254, 0, 0});
		color(1, g.numCols / 2  +1, new int[] {254, 0, 0});
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