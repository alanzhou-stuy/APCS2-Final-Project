package main;

import java.util.ArrayList;

public class Tile {
	public ArrayList<Square> squares;
	public ArrayList<Integer[]> respectiveCoords;
	private Grid g;
	public int pivotX, pivotY;
	private String blockType;
	public int[] color;
	private int height;
	private int phase;
	private int numOfPhases;

	public Tile() {
		squares = new ArrayList<Square>();
		respectiveCoords = new ArrayList<Integer[]>();
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
		// setBlockColor();
	}

	public ArrayList<Square> getSquares() {
		return squares;
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

	public void setLBlock() {
		squares.add(g.getSquare(pivotY, pivotX)); // 0 0
		squares.add(g.getSquare(pivotY + 1, pivotX)); // 1 0
		squares.add(g.getSquare(pivotY + 2, pivotX)); // 2 0
		squares.add(g.getSquare(pivotY + 2, pivotX + 1)); // 2 1
		
		/*
		squares.remove(0);
		squares.remove(0);
		squares.remove(0);
		squares.remove(0);
		*/
		
		respectiveCoords.add(new Integer[]{0,0});
		respectiveCoords.add(new Integer[]{1,0});
		respectiveCoords.add(new Integer[]{2,0});
		respectiveCoords.add(new Integer[]{2,1});
		
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
		// System.out.println(squares.size());
		// squares.remove(1);
		// squares.remove(2);
		// squares.remove(3);
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

	public String blockType() {
		return blockType;
	}

	public void setBlock(String s) {
		if (s.equals("I")) {
			setIBlock();
		} else if (s.equals("J")) {
			setJBlock();
		} else if (s.equals("L")) {
			setLBlock();
		} else if (s.equals("O")) {
			setOBlock();
		} else if (s.equals("S")) {
			setSBlock();
		} else if (s.equals("T")) {
			setTBlock();
		} else if (s.equals("Z")) {
			setZBlock();
		}
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
}
