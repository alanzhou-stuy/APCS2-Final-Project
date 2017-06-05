package main;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class Rules {
	private Tile current;
	private static int TIMER;
	private Colorizer colorizer;
	private int SPEED = 5;
	private int FRAMERATE = 60;
	private Grid g;
	private Main main;

	public Rules() {
		TIMER = 0;
	}

	public Rules(Colorizer colorizer, Tile current, Grid g) {
		this();
		this.colorizer = colorizer;
		this.current = current;
		this.g = g;
	}

	public void setSpeed(int speed) {
		SPEED = speed;
	}

	public void setFR(int framerate) {
		FRAMERATE = framerate;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void run() {
		// Polynomial regression
		int run_period = (int) ((-0.0235 * Math.pow(SPEED, 3) + 0.69 * Math.pow(SPEED, 2) - 7.85 * SPEED + 35)
				* (FRAMERATE / 60.0)) + 1;

		if (TIMER % run_period == 0) {
			if (hitBottom() || hitBlock()) {
				System.out.println(current.getSquares().get(0).partOfCurrentBlock);
				current = colorizer.spawnBlock();
			} else {
					current = colorizer.drop(current, 1);
					System.out.println(current.getSquares().get(0).partOfCurrentBlock);

				// current = colorizer.rotate(false, current, 1);

				/*
				 * for (Square s : current.getSquares()) { }
				 * System.out.println(s.getYCor()); }
				 */

				if (main.keyPressed && main.key == main.CODED) {
					if (main.keyCode == main.RIGHT) {
						current = colorizer.moveRight(current);
					} else if (main.keyCode == main.LEFT) {
						current = colorizer.moveLeft(current);
					} else if (main.keyCode == main.UP) {
						current = colorizer.rotate(false, current, 1);
					}
				}
			}
		}
		TIMER++;
	}

	public boolean hitBottom() {
		int lowestY = g.getSquare(g.numRows - 1, 0).getYCor();
		for (Square s : current.getSquares()) {
			if (s.getYCor() == lowestY) {
				return true;
			}
		}
		return false;
	}

	public boolean hitSides() {
		for (Square s : current.getSquares()) {
			if (s.getColIndex() < 0 || s.getColIndex() > g.getNumCols() - 1) {
				return true;
			}
		}
		return false;
	}

	public boolean part(Square t) {
		return t.partOfCurrentBlock;
	}
	
	public boolean hitBlock() {
		for (Square s: current.getSquares()) {
			//if (s.getYCor() == calLowestYCorOfTile(current)) {
				if ((colorizer.colored(s.getRowIndex(),s.getColIndex())) 
						&& !part(g.grid[s.getRowIndex() + 1][s.getColIndex()])){
					return true;
				}
			//}
		}
		return false;
	}

	public boolean blockOffMap() {
		int lowestX = g.getSquare(0, 0).getXCor();
		int highestX = g.getSquare(0, g.numCols - 1).getXCor();
		int lowestY = g.getSquare(g.numRows - 1, 0).getYCor();
		for (Square s : current.getSquares()) {
			// need to change
			if (s.getXCor() <= lowestX || s.getYCor() > lowestY || s.getXCor() > highestX) {
				return true;
			}
		}
		return false;
	}

	public int calLowestYCorOfTile(Tile t) {
		int lowest = t.getSquares().get(0).getYCor();
		for (Square s : t.getSquares()) {
			if (s.getYCor() < lowest) {
				lowest = s.getYCor();
			}
		}
		return lowest;
	}
	
	public int calLowestYCorOfTileX(Tile t) {
		int lowest = t.getSquares().get(0).getYCor();
		for (Square s : t.getSquares()) {
			if (s.getYCor() < lowest) {
				lowest = s.getXCor();
				return lowest;
			}
		}
		return lowest;
	}

	public int calHighestYCorOfTile(Tile t) {
		int highest = t.getSquares().get(0).getYCor();
		for (Square s : t.getSquares()) {
			if (s.getYCor() > highest) {
				highest = s.getYCor();
			}
		}
		return highest;
	}

	public int calNextColoredSquare(Tile t) {
		int[] white = new int[] { 255, 255, 255 };
		for (Square[] rowOfSquares : g.grid) {
			for (Square s : rowOfSquares) {
				if ((calLowestYCorOfTile(t) == s.getYCor()) && (s.getColor() != white)) {
					return s.getXCor();
				}
			}
		}
		return -1;
	}

	public void fullLine() {
		int counter = g.getNumCols();
		int[] white = new int[] { 255, 255, 255 };
		for (Square[] rowOfSquares : g.grid) {
			for (Square s : rowOfSquares) {
				if (s.getColor() != white) {
					counter--;
				}
				if (counter == 0) {
					clearLine();
				}
			}
			counter = g.getNumCols();
		}
	}

	public void clearLine() {
		for (int r = 1; r < g.getNumRows(); r++) {
			for (int s = 0; s < g.getNumCols(); s++) {
				g.grid[r][s].setColor(g.grid[r + 1][s].getColor());
			}
		}
	}
	
	/*public boolean hit() {
		int[] white = new int[] { 255, 255, 255 };
		int lowX = current.getSquares().get(0).getColIndex();
		int lowY = current.getSquares().get(0).getRowIndex();
		int highX = current.getSquares().get(0).getColIndex();
		int highY = current.getSquares().get(0).getRowIndex();
		for (Square s: current.getSquares()) {
			if (s.getColIndex() < lowX) {
			}
		}
		return false;
	}*/

	public class KeyAction extends AbstractAction {
		private String sequence;
		private KeyStroke keystroke;

		public KeyAction(String sequence) {
			this.sequence = sequence;
		}

		public void actionPerformed(ActionEvent e) {
			switch (sequence) {
			case "clockwise":
				colorizer.rotate(true, current, 1);
				break;
			case "counterclockwise":
				colorizer.rotate(false, current, 1);
				break;
			case "left":
				colorizer.moveLeft(current);
				break;
			case "right":
				colorizer.moveRight(current);
				break;
			}

			/*
			 * if (e.getKeyCode() == KeyEvent.VK_LEFT) { current =
			 * colorizer.moveRight(current); System.out.println("yay"); } else
			 * if (e.getKeyCode() == KeyEvent.VK_RIGHT) { current =
			 * colorizer.moveLeft(current); }
			 */
		}
	}
}
