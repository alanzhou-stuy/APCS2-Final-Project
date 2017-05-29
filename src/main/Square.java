package main;

/**
 * Represents each square in the grid. A key component in the Tile class
 */
public class Square {
	public int xCor, yCor, size;
	public int[] color;

	public Square() {
		this(0, 0, 10, new int[] { 255, 255, 255 });
	}

	public Square(int xCor, int yCor, int size, int[] color) {
		this.xCor = xCor;
		this.yCor = yCor;
		this.size = size;
		this.color = color;
	}

	public void setXYCor(int xCor, int yCor) {
		this.xCor = xCor;
		this.yCor = yCor;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setColor(int[] color) {
		this.color = color;
	}

	public int getXCor() {
		return xCor;
	}

	public int getYCor() {
		return yCor;
	}
	
	public void setYCor(int x) {
		yCor = yCor + x;
	}

	public int getSize() {
		return size;
	}

	public int[] getColor() {
		return color;
	}
}