package main;

import java.util.ArrayList;

public class Tile implements Tilable {
	public ArrayList<Square> squares;
	private Grid g;
	public int pivotX, pivotY;
	private String blockType;
	public int[] color;

	public Tile() {
		squares = new ArrayList<Square>();
	}

	public Tile(Grid g, int pivotY, int pivotX) {
		this();
		this.g = g;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
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

	public void setIBlock() {
		squares.add(g.getSquare(pivotY, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY + 3, pivotX));
		blockType = "I";
	}

	public void setJBlock() {
		squares.add(g.getSquare(pivotY, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 2, pivotX ));
		squares.add(g.getSquare(pivotY + 2, pivotX + 1));
		blockType = "J";
	}

	public void setLBlock() {
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY , pivotX + 1));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX + 1));
		blockType = "L";
	}

	public void setOBlock() {
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY, pivotX + 1));
		blockType = "O";
	}

	public void setSBlock() {
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY, pivotX + 1));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		blockType = "S";
	}

	public void setTBlock() {
		squares.add(g.getSquare(pivotY + 2, pivotX));
		squares.add(g.getSquare(pivotY, pivotX + 1));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX + 1));
		blockType = "T";
	}

	public void setZBlock() {
		squares.add(g.getSquare(pivotY, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX));
		squares.add(g.getSquare(pivotY + 1, pivotX + 1));
		squares.add(g.getSquare(pivotY + 2, pivotX + 2));
		blockType = "Z";
	}

	public Square remove() {
		return squares.remove(0);
		// System.out.println(squares.size());
		// squares.remove(1);
		// squares.remove(2);
		// squares.remove(3);
	}

	public void setPivotX(int x) {
		pivotX += x;
	}

	public void setPivotY(int y) {
		pivotY += y;
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
		} else if (s.equals("S'")) {
			setSBlock();
		} else if (s.equals("T")) {
			setTBlock();
		} else if (s.equals("Z")) {
			setZBlock();
		}
	}

	public void move() {
	};

	/*
	 * public void move() {
	 * squares.add(g.getSquare(squares.get(0).,squares.get(0).));
	 * squares.add(g.getSquare(squares.get(0).,squares.get(1).);
	 * squares.add(g.getSquare(squares.get(0).,squares.get(2).);
	 * squares.add(g.getSquare(squares.get(0),squares.get(3).); };
	 */

	public void rotate() {
	};

	// set all the color of the tile the same from Square file
	/*
	 * public void setBlockColor() { for (int i = 0;i < size; i++) { for(int j =
	 * 0;j < size; j++) { if (tiles[i][j] == 1) {
	 * 
	 * } } }
	 * 
	 * public void rotateLeft() {
	 * 
	 * }
	 * 
	 * public void rotateRight() { }
	 */

}
