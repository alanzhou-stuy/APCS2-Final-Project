package main;

/**
 * A component in the grid. Represents a square in the visual grid in the Main
 * frame.
 */
public class Square {
	public int xCor, yCor, size;
	public int[] color;

	public Square() {
		this(0, 0, 10, new int[] { 255, 255, 255 });
	}

	/**
	 * @param xCor
	 *            x-coordinate of the top-left-most pixel of the Square
	 * @param yCor
	 *            y-coordinate of the top-left-most pixel of the Square
	 * @param size
	 *            size of the Square horizontally and vertically
	 * @param color
	 *            color of the square to be displayed in the visual grid
	 */
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

	public int getSize() {
		return size;
	}

	public int[] getColor() {
		return color;
	}
}