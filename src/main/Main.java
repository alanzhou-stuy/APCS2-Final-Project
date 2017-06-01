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
	private final int height = 900;
	private final int width = 1600;
	private int numRows, numCols;
	private int[] bgColor = { 20, 20, 20 };
	private Tile current;
	int timer = 0;
	private Rules rule;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	public void setup() {
<<<<<<< HEAD

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

		background(bgColor[0], bgColor[1], bgColor[2]);
		grid = new Grid(numRows, numCols);
=======
		background(50, 50, 50);
		grid = new Grid(20, 10);
		
>>>>>>> TestingAlanRotating

		colorizer = new Colorizer(grid, this);
		colorizer.setTileSep(1);
		colorizer.create(); // create grid
		current = colorizer.spawnIBlock();

		score = new Score(this, grid);
		score.setBackgroundColor(new int[] { 200, 200, 200 });
		score.create();

		lb = new Leaderboard(this, grid);
		lb.setBackgroundColor(new int[] { 200, 200, 200 });
		lb.create();

		current = colorizer.spawnLBlock();
		
		rule = new Rules(colorizer,current,grid);
	}

	public void settings() {
		size(width, height);
	}

	public void draw() {
<<<<<<< HEAD
		background(bgColor[0], bgColor[1], bgColor[2]);
		grid.updateGrid(numRows, numCols);
		; // should only be if changes

		if (timer % 40 == 0) {
			current = colorizer.drop(current,
					// grid.getNumRows() - current.getHeight()
					1);
		}

=======
		// Test cases to color individual squares
		// current = colorizer.rotateRight(current,1);
		// current = colorizer.rotateRight(current,1);
		//if (timer % 40 == 0) {
			//current = colorizer.drop(current,
			// grid.getNumRows() - current.getHeight()
					//1);
		//}
		// System.out.println(current.hitBottom());
		// current = colorizer.drop(current);
		// current = colorizer.drop(current);
		// current = colorizer.drop(current);
		// current = colorizer.drop(current);
		rule.run();
>>>>>>> TestingAlanRotating
		colorizer.refresh();
		score.refresh();
		lb.refresh();

		//timer++;
	}
}