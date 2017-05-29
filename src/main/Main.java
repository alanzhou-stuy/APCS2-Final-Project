package main;

import processing.core.PApplet;

/**
 * Main class which sets up the frame and background, as well as creates the
 * grid through blocks
 */
public class Main extends PApplet {
	Setup s;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	public void setup() {
		background(50, 50, 50);
		s = new Setup(this);
		s.setColCount(10);
		s.setRowCount(20);
		s.createGrid();
	}

	public void settings() {
		size(1200, 1200);
	}

	public void draw() {

	}
}