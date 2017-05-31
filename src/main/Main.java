package main;

import processing.core.PApplet;

/**
 * Main class which sets up the frame and background, as well as creates the
 * grid through blocks
 */
public class Main extends PApplet {
	private Colorizer colorizer;
	private Grid grid;
	private Score score;
	private Leaderboard lb;
	private final int height = 800;
	private final int width = 1000;
	private Tile current;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	public void setup() {
		background(50, 50, 50);
		grid = new Grid(20, 10);

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
		// Test cases to color individual squares
		current = colorizer.spawnIBlock();
		current = colorizer.rotateRight(current,1);
		//current = colorizer.rotateRight(current,1);
		//current = colorizer.drop(current,grid.getNumRows() - current.getHeight());
		//System.out.println(current.hitBottom());
		//current = colorizer.drop(current);
		//current = colorizer.drop(current);
		//current = colorizer.drop(current);
		//current = colorizer.drop(current);
		colorizer.refresh();
		score.refresh();
		lb.refresh();
	}
}