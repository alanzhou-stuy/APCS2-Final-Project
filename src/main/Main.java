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
	private Tile currentTile;
	private Rules rule;
	private GridAnalyzer analyzer;
	private Slider speedSlider, diffSlider, varietySlider, numRowsSlider, numColsSlider;
	private Textlabel score, info1, info2;
	private Button start, compStart;

	private static int SPEED = 5;
	private static int FRAMERATE = 60;
	private static int CONTROL_RESPONSIVENESS = 4;
	private static int COMPUTER_PLAY_SPEED = 3;
	private static int DIFFICULTY, VARIETY;
	private static boolean COMPUTER_PLAYS = true;
	private int COUNTER = 0;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	public void setup() {
		frameRate(FRAMERATE); // shouldn't be changed
		background(bgColor[0], bgColor[1], bgColor[2]);

		setupColorizer();
		createGUI();
		currentTile = colorizer.spawnBlock(); // DON'T MOVE THIS
		setupRules();
	}

	private void setupRules() {
		rule = new Rules(colorizer, currentTile, grid);
		rule.setSpeed(SPEED);
		rule.setFR(FRAMERATE);

		if (analyzer != null) {
			rule.setAnalyzer(analyzer);
			analyzer.setRule(rule);
		}
	}

	private void setupColorizer() {
		grid = new Grid(numRows, numCols);

		colorizer = new Colorizer(grid, this);
		colorizer.setTileSep(1);
		colorizer.create(); // create grid

		analyzer = new GridAnalyzer(grid);
	}

	public void draw() {
		background(bgColor[0], bgColor[1], bgColor[2]);
		rule.setSpeed(SPEED);

		if (start.getBooleanValue() == false) {
			colorizer.setRowsCols(numRows, numCols);
			start.setCaptionLabel("START");
		}

		if (compStart.getBooleanValue() == true) {
			compStart.setCaptionLabel("Set to PLAYER");
			info2.setText("Mode: Computer");
			COMPUTER_PLAYS = true;
		} else {
			compStart.setCaptionLabel("Set to COMPUTER");
			info2.setText("Mode: Player control");
			COMPUTER_PLAYS = false;
		}

		// currentTile = colorizer.drop(currentTile, 1);
		// currentTile = colorizer.rotate(false, currentTile, 1);

		if (start.getBooleanValue() == true && rule.GAME_OVER == false && COMPUTER_PLAYS == false) {
			rule.run();
			if (keyPressed && key == CODED && COUNTER++ % CONTROL_RESPONSIVENESS == 0) {
				rule.registerKeyPress(keyCode);
				keyPressed = false;
			} else if (keyPressed && key != CODED && key == ' ' && COUNTER++ % CONTROL_RESPONSIVENESS == 0) {
				rule.registerKeyPress(key);
				keyPressed = false;
			}

			start.setCaptionLabel("RESET");
			score.setText("SCORE: " + rule.SCORE);
		} else if (start.getBooleanValue() == true && rule.GAME_OVER == false && COMPUTER_PLAYS == true) {
			int[] possible = analyzer.getDirections(analyzer.returnBestPosition());

			for (int move : possible) {
				rule.registerKeyPress(move);
			}

			rule.run();

			keyPressed = false;
		}

		info1.setText("Num squares: "
				+ analyzer.getTotalColoredSquares(analyzer.getTopMostColoredRow(), grid.getNumRows() - 1))
				.setPosition(4 * width / 5, 250).setSize(200, 60);
		colorizer.refresh();
	}

	public void createGUI() {
		gui = new ControlP5(this);

		ControlFont scoreFont = new ControlFont(createFont("Arial", 34));
		ControlFont largeFont = new ControlFont(createFont("Arial", 22));
		ControlFont textFont = new ControlFont(createFont("Arial", 18));

		int sliderWidth = (int) (width * .17);
		int sliderHeight = (int) (height * 0.075);
		int sliderSideMargin = (int) (width * .03);
		int sliderTopMargin = (int) (height * .1);
		int sliderVertSpacing = 130;

		speedSlider = gui.addSlider("SPEED").setSize(sliderWidth, sliderHeight).setRange(0, 10)
				.setPosition(sliderSideMargin, sliderTopMargin).setValue(SPEED).setNumberOfTickMarks(11);

		diffSlider = gui.addSlider("DIFFICULTY").setSize(sliderWidth, sliderHeight).setRange(0, 100)
				.setPosition(sliderSideMargin, sliderTopMargin + (sliderVertSpacing)).setValue(50)
				.setNumberOfTickMarks(21);

		numRowsSlider = gui.addSlider("numRows").setSize(sliderWidth, sliderHeight).setRange(5, 50)
				.setPosition(sliderSideMargin, sliderTopMargin + (2 * sliderVertSpacing)).setValue(20)
				.setNumberOfTickMarks(46);

		numColsSlider = gui.addSlider("numCols").setSize(sliderWidth, sliderHeight).setRange(5, 50)
				.setPosition(sliderSideMargin, sliderTopMargin + (3 * sliderVertSpacing)).setValue(10)
				.setNumberOfTickMarks(46);

		score = gui.addTextlabel("score").setText("SCORE: " + Rules.SCORE).setPosition(4 * width / 5, 50).setSize(200,
				60);
		info1 = gui.addTextlabel("info")
				.setText("Num squares: "
						+ analyzer.getTotalColoredSquares(analyzer.getTopMostColoredRow(), grid.getNumRows() - 1))
				.setPosition(4 * width / 5, 250).setSize(200, 60);

		info2 = gui.addTextlabel("info2").setText("Mode: " + "Player control").setPosition(4 * width / 5, 300)
				.setSize(200, 60);

		start = gui.addButton("START").setValue(0).setPosition(4 * width / 5, 150).setSize(200, 60);
		compStart = gui.addButton("COMPUTER").setValue(0).setPosition(3 * width / 4, 350).setSize(300, 60);

		// Setting fonts
		start.getCaptionLabel().setFont(largeFont);
		compStart.getCaptionLabel().setFont(largeFont);
		speedSlider.getCaptionLabel().setFont(textFont);
		diffSlider.getCaptionLabel().setFont(textFont);
		numRowsSlider.getCaptionLabel().setFont(textFont);
		numColsSlider.getCaptionLabel().setFont(textFont);

		// Label fonts
		score.setFont(scoreFont);
		info1.setFont(textFont);
		info2.setFont(textFont);
	}

	public void settings() {
		size(width, height);
		// fullScreen(); // MAYBE INCLUDE THIS IN OPTIONS? (Alt-F4 or Esc to
		// exit fullscreen)
	}
}