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
				for (Square s : current.getSquares()) {
					System.out.println(s.getXCor());
				}
			}
		}
		timer++;

	}

	public boolean hitBottom() {
		for (Square s : current.getSquares()) {
			if (s.getYCor() == 715) {
				return true;
			}
		}
		return false;
	}

	public boolean hitBlock() {
/*		int[] white = new int[] { 255, 255, 255 };
		for (Square s: current.getSquares()) {
			if (g.getColor((s.getYCor() + 35)) != white) {
				return true;
			}
		}*/
		return false;
	}

	public boolean blockOffMap() {
		for (Square s: current.getSquares()) {
			if ((s.getXCor() < 325) || s.getYCor() > 715 || s.getXCor() > 640) { //need to change
				return true;
			}
		}
		return false;
	}
}
