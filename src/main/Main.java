package main;

import processing.core.PApplet;

/**
 * Main class which sets up the frame and background, as well as creates the
 * grid through blocks
 */
public class Main extends PApplet {
	Setup s;
	Colorizer colorizer;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	public void setup() {
		background(50, 50, 50);

		s = new Setup(this);
		s.setColCount(10);
		s.setRowCount(20);
		s.createGrid();

		colorizer = new Colorizer(s.g, this);
	}

	public void settings() {
		size(1200, 1200);
	}

	public void draw() {
		// Test cases to color individual squares
		colorizer.color(0, 0, new int[] { 51, 204, 51 });
		colorizer.color(1, 0, new int[] { 51, 204, 51 });
		colorizer.color(1, 1, new int[] { 51, 204, 51 });
		colorizer.color(1, 2, new int[] { 51, 204, 51 });
		colorizer.refreshGrid();
	}
}