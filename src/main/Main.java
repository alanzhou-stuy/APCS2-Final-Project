package main;

import controlP5.*;
import processing.core.PApplet;

/**
 * Main class which sets up the frame and background, as well as creates the
 * grid through blocks
 */
public class Main extends PApplet {
	private Colorizer colorizer;
	private ControlP5 gui;
	private Grid grid;
	private final int height = 800;
	private final int width = 1400;
	private int numRows = 20;
	private int numCols = 10;
	private int[] bgColor = { 20, 20, 20 };
	private int SPEED = 5;
	private int FRAMERATE = 60;
	private Tile currentTile;
	private Rules rule;
	// To be implemented later!
	// private Score score;
	// private Leaderboard lb;
	private Slider speedSlider, diffSlider, varietySlider, numRowsSlider, numColsSlider;
	private Button start;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	// public void actionPerformed(ActionEvent e) {
	// rule.keyPressed(e);
	// }

	public void setup() {
		frameRate(FRAMERATE); // shouldn't be changed

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

		currentTile = colorizer.spawnTBlock(); // DON'T MOVE THIS

		rule = new Rules(colorizer, currentTile, grid);
		rule.setSpeed(SPEED);
		rule.setFR(FRAMERATE);
		rule.setMain(this);
	}

	public void settings() {
		size(width, height);
		// fullScreen(); // MAYBE INCLUDE THIS IN OPTIONS? (Alt-F4 or Esc to
		// exit fullscreen)
	}

	public void draw() {
		background(bgColor[0], bgColor[1], bgColor[2]);
		rule.setSpeed(SPEED);

		if (start.getBooleanValue() == false) {
			colorizer.setRowsCols(numRows, numCols);

			start.setCaptionLabel("START ");
		}

		// currentTile = colorizer.drop(currentTile, 1);
		// currentTile = colorizer.rotate(false, currentTile, 1);

		if (start.getBooleanValue() == true) {
			rule.run();

			

			start.setCaptionLabel("STOP");
		}

		colorizer.refresh();
	}

	public void createGUI() {
		gui = new ControlP5(this);

		ControlFont largeFont = new ControlFont(createFont("Arial", 22));
		ControlFont textFont = new ControlFont(createFont("Arial", 16));

		speedSlider = gui.addSlider("SPEED").setSize(225, 50).setRange(0, 10).setPosition(40, 100).setValue(SPEED)
				.setNumberOfTickMarks(11);

		diffSlider = gui.addSlider("difficulty").setSize(225, 50).setRange(0, 100).setPosition(40, 200).setValue(50)
				.setNumberOfTickMarks(21);

		varietySlider = gui.addSlider("variety").setSize(225, 50).setRange(0, 100).setPosition(40, 300).setValue(50)
				.setNumberOfTickMarks(11);

		numRowsSlider = gui.addSlider("numRows").setSize(225, 50).setRange(4, 40).setPosition(40, 400).setValue(20)
				.setNumberOfTickMarks(37);

		numColsSlider = gui.addSlider("numCols").setSize(225, 50).setRange(4, 40).setPosition(40, 500).setValue(10)
				.setNumberOfTickMarks(37);

		start = gui.addButton("START").setValue(0).setPosition(4 * width / 5, 50).setSize(200, 60);

		start.getCaptionLabel().setFont(largeFont);

		speedSlider.getCaptionLabel().setFont(textFont);
		diffSlider.getCaptionLabel().setFont(textFont);
		varietySlider.getCaptionLabel().setFont(textFont);
		numRowsSlider.getCaptionLabel().setFont(textFont);
		numColsSlider.getCaptionLabel().setFont(textFont);
	}
}