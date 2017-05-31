package main;

import processing.core.PApplet;
import controlP5.*;

/**
 * Main class which sets up the frame and background, as well as creates the
 * grid through blocks
 */
public class Main extends PApplet {
	private Colorizer colorizer;
	private ControlP5 gui;
	private Grid grid;
	private Score score;
	private Leaderboard lb;
	private Slider s;
	private final int height = 800;
	private final int width = 1400;
	private int numRows, numCols;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	public void setup() {
		numRows = 20;
		numCols = 10;

		gui = new ControlP5(this);

		gui.addSlider("speed").setSize(225, 50).setRange(0, 100).setPosition(40, 100).setValue(50)
				.setNumberOfTickMarks(21);

		gui.addSlider("difficulty").setSize(225, 50).setRange(0, 100).setPosition(40, 200).setValue(50)
				.setNumberOfTickMarks(21);

		gui.addSlider("variety").setSize(225, 50).setRange(0, 100).setPosition(40, 300).setValue(50)
				.setNumberOfTickMarks(11);

		gui.addSlider("numRows").setSize(225, 50).setRange(4, 40).setPosition(40, 400).setValue(20)
				.setNumberOfTickMarks(37);

		gui.addSlider("numCols").setSize(225, 50).setRange(4, 40).setPosition(40, 500).setValue(10)
				.setNumberOfTickMarks(37);

		background(50, 50, 50);
		grid = new Grid(numRows, numCols);

		colorizer = new Colorizer(grid, this);
		colorizer.setTileSep(1);
		colorizer.create(); // create grid

		score = new Score(this, grid);
		score.setBackgroundColor(new int[] { 200, 200, 200 });
		score.create();

		lb = new Leaderboard(this, grid);
		lb.setBackgroundColor(new int[] { 200, 200, 200 });
		lb.create();
	}

	public void settings() {
		size(width, height);
	}

	public void draw() {
		// Test cases to color individual square
		background(50, 50, 50);
		grid.updateGrid(numRows, numCols);; // should only be if changes
		colorizer.refresh();
	}
}