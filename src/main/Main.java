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
	private Slider speedSlider, numRowsSlider, numColsSlider;
	private Textlabel score, mode;
	private Textlabel holes, aggHeight, linesCleared, bumpiness, strength;
	private Button start, compStart;

	private static int SPEED = 5;
	private static int FRAMERATE = 60;
	private static int CONTROL_RESPONSIVENESS = 4;
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
		
		rule.setMain(this);
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
			mode.setText("Mode: Computer");
			COMPUTER_PLAYS = true;
		} else {
			compStart.setCaptionLabel("Set to COMPUTER");
			mode.setText("Mode: Player control");
			COMPUTER_PLAYS = false;
		}

		// currentTile = colorizer.drop(currentTile, 1);
		// currentTile = colorizer.rotate(false, currentTile, 1);

		if (start.getBooleanValue() == true && rule.GAME_OVER == false && COMPUTER_PLAYS == false) {
			rule.run(COMPUTER_PLAYS);
			if (keyPressed && key == CODED && COUNTER++ % CONTROL_RESPONSIVENESS == 0) {
				rule.registerKeyPress(keyCode);
				keyPressed = false;
			} else if (keyPressed && key != CODED && key == ' ' && COUNTER++ % CONTROL_RESPONSIVENESS == 0) {
				rule.registerKeyPress(key);
				keyPressed = false;
			}

			start.setCaptionLabel("RESET");
			score.setText("SCORE: " + Rules.SCORE);
		} else if (start.getBooleanValue() == true && rule.GAME_OVER == false && COMPUTER_PLAYS == true) {
			rule.run(COMPUTER_PLAYS);
			keyPressed = false;
		}

		colorizer.refresh();
	}

	public void updateCompInfo() {
		Candidate best = analyzer.returnBestCandidate();

		aggHeight.setText("Aggregate height: " + best.resultingAH).setPosition(4 * width / 5, 500).setSize(200, 60);
		holes.setText("Holes: " + best.resultingH).setPosition(4 * width / 5, 530).setSize(200, 60);
		linesCleared.setText("Lines to clear: " + best.resultingLC).setPosition(4 * width / 5, 560).setSize(200, 60);
		bumpiness.setText("Bumpiness: " + best.resultingB).setPosition(4 * width / 5, 590).setSize(200, 60);
		strength.setText("Overall: " + best.strength).setPosition(4 * width / 5, 620).setSize(200, 60);
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

		numRowsSlider = gui.addSlider("numRows").setSize(sliderWidth, sliderHeight).setRange(5, 50)
				.setPosition(sliderSideMargin, sliderTopMargin + (1 * sliderVertSpacing)).setValue(20)
				.setNumberOfTickMarks(46);

		numColsSlider = gui.addSlider("numCols").setSize(sliderWidth, sliderHeight).setRange(5, 50)
				.setPosition(sliderSideMargin, sliderTopMargin + (2 * sliderVertSpacing)).setValue(10)
				.setNumberOfTickMarks(46);

		score = gui.addTextlabel("score").setText("SCORE: " + Rules.SCORE).setPosition(4 * width / 5, 50).setSize(200,
				60);

		mode = gui.addTextlabel("mode").setText("Mode: " + "Player control").setPosition(4 * width / 5, 300)
				.setSize(200, 60);

		aggHeight = gui.addTextlabel("aggHeight").setText("Aggregate Height: " + 0).setPosition(4 * width / 5, 500)
				.setSize(200, 60);

		holes = gui.addTextlabel("holes").setText("Holes: " + 0).setPosition(4 * width / 5, 530).setSize(200, 60);

		linesCleared = gui.addTextlabel("linesCleared").setText("Holes: " + 0).setPosition(4 * width / 5, 560)
				.setSize(200, 60);

		bumpiness = gui.addTextlabel("bumpiness").setText("Bumpiness: " + 0).setPosition(4 * width / 5, 590)
				.setSize(200, 60);

		strength = gui.addTextlabel("strength").setText("Strength: " + 0).setPosition(4 * width / 5, 620).setSize(200,
				60);

		start = gui.addButton("START").setValue(0).setPosition(4 * width / 5, 150).setSize(200, 60);
		compStart = gui.addButton("COMPUTER").setValue(0).setPosition(3 * width / 4, 350).setSize(300, 60);

		// Setting fonts
		start.getCaptionLabel().setFont(largeFont);
		compStart.getCaptionLabel().setFont(largeFont);
		speedSlider.getCaptionLabel().setFont(textFont);
		numRowsSlider.getCaptionLabel().setFont(textFont);
		numColsSlider.getCaptionLabel().setFont(textFont);

		// Label fonts
		score.setFont(scoreFont);
		holes.setFont(textFont);
		mode.setFont(textFont);
		aggHeight.setFont(textFont);
		linesCleared.setFont(textFont);
		bumpiness.setFont(textFont);
		strength.setFont(textFont);
	}

	public void settings() {
		size(width, height);
		// fullScreen(); // MAYBE INCLUDE THIS IN OPTIONS? (Alt-F4 or Esc to
		// exit fullscreen)
	}
}