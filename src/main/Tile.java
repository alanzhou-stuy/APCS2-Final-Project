package main;

import java.util.ArrayList;

public class Tile implements Tilable {
	private int[][] tiles;
	private ArrayList<Square> squares;
	private int color;
	private Grid g;
	private int pivotX, pivotY;
	
	public Tile() {
		tiles = new int[4][4];
		squares = new ArrayList<Square>();
	}
	
	public Tile(Grid g, int pivotX, int pivotY){
		this();
		this.g = g;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}

	public Tile(String type) {
		tiles = new int[4][4];
		if (type.equals("I")) {
			setIBlock();
		} else if (type.equals("J")) {
			setJBlock();
		} else if (type.equals("L")) {
			setLBlock();
		} else if (type.equals("O")) {
			setOBlock();
		} else if (type.equals("S")) {
			setSBlock();
		} else if (type.equals("T")) {
			setTBlock();
		} else if (type.equals("Z")) {
			setZBlock();
		}
		// setBlockColor();
	}

	// bottom aligned
	public void setIBlock() {
		/*
		tiles[3][0] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
		tiles[3][3] = 1;
		*/
		
		squares.add(g.getSquare(pivotX,pivotY));
		squares.add(g.getSquare(pivotX,pivotY+1));
		squares.add(g.getSquare(pivotX,pivotY+2));
		squares.add(g.getSquare(pivotX,pivotY+3));
	}

	// left
	public void setJBlock() {
		tiles[2][0] = 1;
		tiles[3][0] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
	}

	// right
	public void setLBlock() {
		tiles[2][3] = 1;
		tiles[3][3] = 1;
		tiles[3][2] = 1;
		tiles[3][1] = 1;
	}

	// bottom center
	public void setOBlock() {
		tiles[2][1] = 1;
		tiles[2][2] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
	}

	// bottom right
	public void setSBlock() {
		tiles[2][2] = 1;
		tiles[2][3] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
	}

	// bottom right
	public void setTBlock() {
		tiles[2][2] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
		tiles[3][3] = 1;
	}

	// bottom right
	public void setZBlock() {
		tiles[2][1] = 1;
		tiles[2][2] = 1;
		tiles[4][2] = 1;
		tiles[4][3] = 1;
	}

	public void move() {
	};

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
