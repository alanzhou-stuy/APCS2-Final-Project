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
	private Textlabel holes, aggHeight, linesToClear, bumpiness, strength;
	private Textlabel level, linesCleared, highScore;
	private Button start, compStart;
	private boolean onStart;
	private static int SPEED = 1;
	private static int FRAMERATE = 60;
	private static int CONTROL_RESPONSIVENESS = 4;
	private static boolean COMPUTER_PLAYS = true;
	private int COUNTER = 0;
	private static int HIGHSCORE = 0;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	public void setup() {
		frameRate(FRAMERATE); // shouldn't be changed
		background(bgColor[0], bgColor[1], bgColor[2]);
		setupColorizer();
		createGUI();
		setupRules();
		onStart = true;
	}

	private void setupRules() {
		rule = new Rules(colorizer, grid);
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

			if (onStart) {
				currentTile = colorizer.spawnBlock(); // DON'T MOVE THIS
				rule.setCurrent(currentTile);
				onStart = false;
			}
			rule.run(COMPUTER_PLAYS);
			if (colorizer.reset) {
				rule.setLevel(1);
				rule.setTotalLinesCleared(0);
				colorizer.reset = false;
			}
			if (rule.latestScore > HIGHSCORE) {
				HIGHSCORE = rule.latestScore;
				// System.out.println(HIGHSCORE);
			}
			if (rule.SPEED != SPEED) {
				SPEED = rule.SPEED;
			}

			if (keyPressed && key == CODED && COUNTER++ % CONTROL_RESPONSIVENESS == 0) {
				rule.registerKeyPress(keyCode);
				keyPressed = false;
			} else if (keyPressed && key != CODED && key == ' ' && COUNTER++ % CONTROL_RESPONSIVENESS == 0) {
				rule.registerKeyPress(key);
				keyPressed = false;
			}

			start.setCaptionLabel("RESET");
			score.setText("SCORE: " + Rules.SCORE);
			rule.setSpeed(rule.getSpeed());

		} else if (start.getBooleanValue() == true && rule.GAME_OVER == false && COMPUTER_PLAYS == true) {
			rule.run(COMPUTER_PLAYS);
			keyPressed = false;
		}

		colorizer.refresh();

		level.setText("Level: " + rule.level);
		linesCleared.setText("Total Lines Cleared: " + rule.totalLinesCleared);
		highScore.setText("High Score: " + HIGHSCORE);
	}

	public void updateCompInfo() {
		Candidate best = analyzer.returnBestCandidate();

		aggHeight.setText("Aggregate height: " + best.resultingAH);
		holes.setText("Holes: " + best.resultingH);
		linesToClear.setText("Lines to clear: " + best.resultingLC);
		bumpiness.setText("Bumpiness: " + best.resultingB);
		strength.setText("Overall: " + best.strength);
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
				.setPosition(sliderSideMargin, sliderTopMargin).setValue(rule.SPEED).setNumberOfTickMarks(11);

		numRowsSlider = gui.addSlider("numRows").setSize(sliderWidth, sliderHeight).setRange(5, 50)
				.setPosition(sliderSideMargin, sliderTopMargin + (1 * sliderVertSpacing)).setValue(20)
				.setNumberOfTickMarks(46);

		numColsSlider = gui.addSlider("numCols").setSize(sliderWidth, sliderHeight).setRange(5, 50)
				.setPosition(sliderSideMargin, sliderTopMargin + (2 * sliderVertSpacing)).setValue(10)
				.setNumberOfTickMarks(46);

		score = gui.addTextlabel("score").setText("SCORE: " + Rules.SCORE).setPosition(3 * width / 4, 50).setSize(200,
				60);

		level = gui.addTextlabel("level").setText("Level: " + 1).setPosition(3 * width / 4, 100).setSize(200, 60);

		mode = gui.addTextlabel("mode").setText("Mode: " + "Player control").setPosition(3 * width / 4, 320)
				.setSize(200, 60);

		aggHeight = gui.addTextlabel("aggHeight").setText("Aggregate Height: " + 0).setPosition(3 * width / 4, 480)
				.setSize(200, 60);

		holes = gui.addTextlabel("holes").setText("Holes: " + 0).setPosition(3 * width / 4, 510).setSize(200, 60);

		linesToClear = gui.addTextlabel("linesCleared").setText("Lines to clear: " + 0).setPosition(3 * width / 4, 540)
				.setSize(200, 60);

		bumpiness = gui.addTextlabel("bumpiness").setText("Bumpiness: " + 0).setPosition(3 * width / 4, 570)
				.setSize(200, 60);

		strength = gui.addTextlabel("strength").setText("Strength: " + 0).setPosition(3 * width / 4, 600).setSize(200,
				60);

		start = gui.addButton("START").setValue(0).setPosition(3 * width / 4, 210).setSize(200, 60);
		compStart = gui.addButton("COMPUTER").setValue(0).setPosition(3 * width / 4, 360).setSize(300, 60);

		linesCleared = gui.addTextlabel("lines cleared").setText("Total Lines Cleared: " + 0)
				.setPosition(3 * width / 4, 160).setSize(200, 60);
		highScore = gui.addTextlabel("high score").setText("High Score: " + HIGHSCORE).setPosition(3 * width / 4, 130)
				.setSize(200, 60);

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
		linesToClear.setFont(textFont);
		bumpiness.setFont(textFont);
		strength.setFont(textFont);
		level.setFont(textFont);
		linesCleared.setFont(textFont);
		highScore.setFont(textFont);
	}

	public void settings() {
		size(width, height);
		// fullScreen(); // MAYBE INCLUDE THIS IN OPTIONS? (Alt-F4 or Esc to
		// exit fullscreen)
	}
}