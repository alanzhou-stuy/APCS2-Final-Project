package main;

import controlP5.ControlP5;
import processing.core.PApplet;

/**
 * Main class which sets up the frame and background, as well as creates the
 * grid through blocks
 */
public class Main extends PApplet {
	private Colorizer colorizer;
	private ControlP5 gui;
	private Grid grid;
	private final int height = 700;
	private final int width = 1400;
	private int numRows = 20;
	private int numCols = 10;
	private int[] bgColor = { 20, 20, 20 };
	private Tile currentTile;
	private Rules rule;
	// To be implemented later!
	// private Score score;
	// private Leaderboard lb;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	// public void actionPerformed(ActionEvent e) {
	// rule.keyPressed(e);
	// }

	public void setup() {
		frameRate(60); // shouldn't be changed
		
		createGUI();

		background(bgColor[0], bgColor[1], bgColor[2]);
		grid = new Grid(numRows, numCols);

		colorizer = new Colorizer(grid, this);
		colorizer.setTileSep(1);
		colorizer.create(); // create grid

		/*
		 * score = new Score(this, grid); score.setBackgroundColor(new int[] {
		 * 200, 200, 200 }); score.create();
		 * 
		 * lb = new Leaderboard(this, grid); lb.setBackgroundColor(new int[] {
		 * 200, 200, 200 }); lb.create();
		 */

		currentTile = colorizer.spawnLBlock(); // DON'T MOVE THIS
		
		rule = new Rules(colorizer, currentTile, grid);
	}

	public void settings() {
		size(width, height);
		//fullScreen(); // MAYBE INCLUDE THIS IN OPTIONS? (Alt-F4 or Esc to exit fullscreen)
	}

	public void draw() {
		background(bgColor[0], bgColor[1], bgColor[2]);
		colorizer.setRowsCols(numRows, numCols);
		
		currentTile = colorizer.drop(currentTile, 1);
		currentTile = colorizer.rotate(false, currentTile, 1);
		
		//rule.run();

		colorizer.refresh();
	}
	
	public void createGUI() {
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
	}
}