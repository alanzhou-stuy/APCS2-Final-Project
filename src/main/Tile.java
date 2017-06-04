package main;

import java.util.ArrayList;

public class Tile {
	public ArrayList<Square> squares;
	public ArrayList<int[]> respectiveCoords;
	private Grid g;
	public int pivotX, pivotY;
	private String blockType;
	public int[] color;
	private int height;
	private int phase;
	private int numOfPhases;

	public Tile() {
		squares = new ArrayList<Square>();
		respectiveCoords = new ArrayList<int[]>();
		height = 2;
	}

	public Tile(Grid g, int pivotY, int pivotX) {
		this();
		this.g = g;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}

	public int getHeight() {
		return height;
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
			setIBlock();
			break;
		case "J":
			setJBlock();
			break;
		case "L":
			setLBlock(initiateCoords);
			break;
		case "O":
			setOBlock();
			break;
		case "S":
			setSBlock();
			break;
		case "T":
			setTBlock();
			break;
		case "Z":
			setZBlock();
			break;
		}
	}

	public void setIBlock() {
		squares.add(g.getSquare(pivotY, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY + 3, pivotX));
		height = 4;
		phase = 0;
		blockType = "I";
		numOfPhases = 2;
	}

	public void setJBlock() {
		squares.add(g.getSquare(pivotY, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX + 1));
		height = 3;
		phase = 0;
		blockType = "J";
		numOfPhases = 4;
	}

	public void setLBlock(boolean initiateCoords) {
		if (initiateCoords) {
			/* Coordinates respective to pivotY and pivotX */
			respectiveCoords.add(new int[] { 0, 0 });
			respectiveCoords.add(new int[] { 1, 0 });
			respectiveCoords.add(new int[] { 2, 0 });
			respectiveCoords.add(new int[] { 2, 1 });
		}
		
		for (int[] coord : respectiveCoords) {
			squares.add(g.getSquare(pivotY + coord[0], pivotX + coord[1]));
		}
		
		height = 3;
		phase = 0;
		numOfPhases = 4;
		blockType = "L";
	}

	public void setOBlock() {
		squares.add(g.getSquare(pivotY, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY, pivotX + 1));
		height = 2;
		numOfPhases = 1;
		blockType = "O";
	}

	public void setSBlock() {
		squares.add(g.getSquare(pivotY, pivotX + 1));
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		height = 3;
		numOfPhases = 2;
		blockType = "S";
	}

	public void setTBlock() {
		squares.add(g.getSquare(pivotY, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX + 1));
		height = 3;
		numOfPhases = 4;
		blockType = "T";
	}

	public void setZBlock() {
		squares.add(g.getSquare(pivotY, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX + 1));
		height = 3;
		numOfPhases = 2;
		blockType = "Z";
	}

	public Square remove() {
		return squares.remove(0);
	}

	public int getNumOfPhases() {
		return numOfPhases;
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

	// not sure if it works
	public boolean hitBottom() {
		int lowestYCor = squares.get(0).getYCor();
		int x = 0;
		while (x < squares.size()) {
			if (squares.get(x).getYCor() < lowestYCor) {
				lowestYCor = squares.get(x).getYCor();
			}
			if (lowestYCor == 0) {
				return true;
			}
		}
		return false;
	}

	public void addRespectiveCoord(int[] coord) {
		respectiveCoords.add(coord);
	}
	
	public void setRespectiveCoords(ArrayList<int[]> respectiveCoords){
		this.respectiveCoords = respectiveCoords;
	}

	public static int[] returnTransformedCoords(boolean clockwise, int[] coords) {
		if (!clockwise) {
			return new int[] { coords[1], -1 * coords[0] };
		} else {
			return new int[] { -1 * coords[1], coords[0] };
		}
	}
}