package main;

import java.awt.*;
import java.awt.event.*;

public class Rules {
	private Tile current;
	int timer;
	private Colorizer colorizer;
	Grid g;
	

	public Rules() {
		timer = 0;
	}

	public Rules(Colorizer colorizer, Tile current,Grid g) {
		this();
		this.colorizer = colorizer;
		this.current = current;
		this.g = g;
	}

	public void run() {
		if (timer % 20 == 0) {
			if (hitBottom() || hitBlock()) {
				current = colorizer.spawnBlock();
			} else {
				current = colorizer.drop(current, 1);
				current = colorizer.moveRight(current);			
				/*for (Square s : current.getSquares()) {
				}
					System.out.println(s.getYCor());
				}*/
			}
		}
		timer++;

	}

	public boolean hitBottom() {
		for (Square s : current.getSquares()) {
			if (s.getYCor() == 620) {
				return true;
			}
		}
		return false;
	}

	public boolean hitBlock() {
		int[] white = new int[] { 255, 255, 255 };
		for (Square s: current.getSquares()) {
			if (g.getSquare(s.getXCor(), (s.getYCor() + g.squareSize)).getColor() != white) {
				return true;
			}
		}
		return false;
	}

	public boolean blockOffMap() {
		int lowestX = g.getSquare(0,0).getXCor();
		int highestX = g.getSquare(0,g.numCols - 1).getXCor();
		int lowestY = g.getSquare(g.numRows - 1,0).getYCor();
		for (Square s: current.getSquares()) {
			//need to change
			if (s.getXCor() <= lowestX || s.getYCor() > lowestY || s.getXCor() > highestX) { 
				return true;
			}
		}
		return false;
	}
	
	public int calLowestYCorOfTile(Tile t) {
		int lowest = t.getSquares().get(0).getYCor();
		for (Square s: t.getSquares()) {
			if (s.getYCor() < lowest) {
				lowest = s.getYCor();
			}
		}
		return lowest;
	}
	
	public int calHighestYCorOfTile(Tile t) {
		int highest = t.getSquares().get(0).getYCor();
		for (Square s: t.getSquares()) {
			if (s.getYCor() > highest) {
				highest = s.getYCor();
			}
		}
		return highest;
	}
	
	public int calNextColoredSquare(Tile t) {
		int[] white = new int[] { 255, 255, 255 };
		for (Square[] rowOfSquares : g.grid) {
			for (Square s: rowOfSquares) {
				if ((calLowestYCorOfTile(t) == s.getYCor()) && (s.getColor() != white)){
					return s.getXCor();
				}
			}
		}
		return -1;
	}
}
