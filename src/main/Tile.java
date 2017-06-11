package main;

import java.util.ArrayList;

public class Tile {
	public ArrayList<Square> squares;
	public ArrayList<int[]> respectiveCoords;
	private Grid g;
	public int pivotX, pivotY;
	private String blockType;
	public int[] color;
	private int maxHeight;
	private int phase;
	private int numPhases;

	public Tile() {
		squares = new ArrayList<Square>();
		respectiveCoords = new ArrayList<int[]>();
	}

	public Tile(Grid g, int pivotY, int pivotX) {
		this();
		intializeCurrentBlock();
		this.g = g;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}

	public void intializeCurrentBlock() {
		for (Square s : squares) {
			s.setPartOfCurrentBlock(true);
			System.out.println("OK");
		}
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public int[] getColor() {
		return color;
	}

	public void setColor(int[] color) {
		this.color = color;
		for (Square s : squares) {
			s.setColor(color);
		}
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void setBlock(String s, boolean initiateCoords) {
		switch (s) {
		case "I":
			setIBlock(initiateCoords);
			break;
		case "J":
			setJBlock(initiateCoords);
			break;
		case "L":
			setLBlock(initiateCoords);
			break;
		case "O":
			setOBlock(initiateCoords);
			break;
		case "S":
			setSBlock(initiateCoords);
			break;
		case "T":
			setTBlock(initiateCoords);
			break;
		case "Z":
			setZBlock(initiateCoords);
			break;
		}
	}

	public void setIBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { -1, 0 });
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 1, 0 });
			respectiveCoords.add(new int[] { 2, 0 });
		}
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}

		maxHeight = 4;
		phase = 0;
		blockType = "I";
		numPhases = 2;
	}

	public void setJBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { -1, 0 });
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 1, 0 });
			respectiveCoords.add(new int[] { 1, -1 });
		}
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}

		maxHeight = 3;
		phase = 0;
		blockType = "J";
		numPhases = 4;
	}

	public void setLBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { -1, 0 });
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 1, 0 });
			respectiveCoords.add(new int[] { 1, 1 });
		}
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}

		maxHeight = 3;
		phase = 0;
		numPhases = 4;
		blockType = "L";
	}

	public void setOBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 1, 0 });
			respectiveCoords.add(new int[] { 1, 1 });
			respectiveCoords.add(new int[] { 0, 1 });
		}
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}

		numPhases = 1;
		maxHeight = 2;
		blockType = "O";
	}

	public void setSBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { 0, 1 });
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 1, 0 });
			respectiveCoords.add(new int[] { 1, -1 });
		}
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}

		maxHeight = 3;
		numPhases = 2;
		blockType = "S";
	}

	public void setTBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 0, 1 });
			respectiveCoords.add(new int[] { 0, -1 });
			respectiveCoords.add(new int[] { 1, 0 });
		}
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}

		numPhases = 4;
		maxHeight = 3;
		blockType = "T";
	}

	public void setZBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { 0, -1 });
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 1, 0 });
			respectiveCoords.add(new int[] { 1, 1 });
		}
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}

		numPhases = 2;
		maxHeight = 3;
		blockType = "Z";
	}

	public Square remove() {
		return squares.remove(0);
	}

	public int getNumOfPhases() {
		return numPhases;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public void setPivotX(int x) {
		pivotX = x;
	}

	public void setPivotY(int y) {
		pivotY = y;
	}

	public int getPivotX() {
		return pivotX;
	}

	public int getPivotY() {
		return pivotY;
	}

	public ArrayList<int[]> getRespectiveCoords() {
		return respectiveCoords;
	}

	public String blockType() {
		return blockType;
	}

	public int calDistanceDrop() {
		return 10;
	}

	public void addRespectiveCoord(int[] coord) {
		respectiveCoords.add(coord);
	}

	public void setRespectiveCoords(ArrayList<int[]> respectiveCoords) {
		this.respectiveCoords = respectiveCoords;
	}

	private boolean isInBounds(int row, int col) {
		return row < g.getNumRows() && row >= 0 && col < g.getNumCols() && col >= 0;
	}

	public static int[] returnTransformedCoords(boolean clockwise, int numTimes, int[] coords) {
		if(numTimes == 0){
			return coords;
		}
		
		if (!clockwise) {
			return returnTransformedCoords(clockwise, numTimes-1, new int[] { coords[1], -1 * coords[0] });
		} else {
			return returnTransformedCoords(clockwise, numTimes-1, new int[] { -1 * coords[1], coords[0] });
		}
	}
}