package main;

import java.util.ArrayList;

public class Tile implements Tilable {
	public ArrayList<Square> squares;
	private Grid g;
	public int pivotX, pivotY;
	
	public Tile() {
		squares = new ArrayList<Square>();
	}
	
	public Tile(Grid g, int pivotX, int pivotY){
		this();
		this.g = g;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}
	
	public void setColor(int[] color) {
		for (Square s: squares) {
			s.setColor(color);
		}
	}

	public void setIBlock() {
		squares.add(g.getSquare(pivotX,pivotY));
		squares.add(g.getSquare(pivotX + 1,pivotY));
		squares.add(g.getSquare(pivotX + 2,pivotY));
		squares.add(g.getSquare(pivotX + 3,pivotY));
	}

	public void setJBlock() {
		squares.add(g.getSquare(pivotX , pivotY));
		squares.add(g.getSquare(pivotX + 1,pivotY));
		squares.add(g.getSquare(pivotX + 1,pivotY + 1));
		squares.add(g.getSquare(pivotX + 1,pivotY + 2));
	}

	public void setLBlock() {
		squares.add(g.getSquare(pivotX ,pivotY + 2));
		squares.add(g.getSquare(pivotX + 1,pivotY));
		squares.add(g.getSquare(pivotX + 1,pivotY + 1));
		squares.add(g.getSquare(pivotX + 1,pivotY + 2));
	}

	public void setOBlock() {
		squares.add(g.getSquare(pivotX ,pivotY + 1));
		squares.add(g.getSquare(pivotX ,pivotY));
		squares.add(g.getSquare(pivotX + 1,pivotY + 1));
		squares.add(g.getSquare(pivotX + 1,pivotY));
	}

	public void setSBlock() {
		squares.add(g.getSquare(pivotX ,pivotY + 1));
		squares.add(g.getSquare(pivotX ,pivotY + 2));
		squares.add(g.getSquare(pivotX + 1,pivotY ));
		squares.add(g.getSquare(pivotX + 1,pivotY + 1));
	}

	public void setTBlock() {
		squares.add(g.getSquare(pivotX ,pivotY + 2));
		squares.add(g.getSquare(pivotX + 1,pivotY));
		squares.add(g.getSquare(pivotX + 1,pivotY + 1));
		squares.add(g.getSquare(pivotX + 1,pivotY + 2));
	}

	public void setZBlock() {
		squares.add(g.getSquare(pivotX,pivotY));
		squares.add(g.getSquare(pivotX ,pivotY + 1));
		squares.add(g.getSquare(pivotX + 1,pivotY + 1));
		squares.add(g.getSquare(pivotX + 1,pivotY + 2));
	}
	
	public void move(){
	};
	/*public void move() {
		squares.add(g.getSquare(squares.get(0).,squares.get(0).));
		squares.add(g.getSquare(squares.get(0).,squares.get(1).);
		squares.add(g.getSquare(squares.get(0).,squares.get(2).);
		squares.add(g.getSquare(squares.get(0),squares.get(3).);
	};*/

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
