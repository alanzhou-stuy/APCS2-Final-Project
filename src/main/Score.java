package main;

import processing.core.PApplet;

public class Score extends RectangularElement {
	private Grid grid;
	private static int SCORE; // to be displayed
	private static final double SIDE_MULTIPLIER = .75;
	private static final double VERT_MULTIPLIER = .75;
	private Rules r;

	public Score(PApplet pApplet) {
		this(pApplet, null);
	}

	public Score(PApplet pApplet, Grid grid) {
		super(pApplet);
		this.grid = grid; // needed for scaling purposes

		setRectWidth((int) (grid.sideMargin * SIDE_MULTIPLIER));

		int relativeGridHeight = (((grid.getSquare(grid.getNumRows() - 1, grid.getNumCols() - 1)).getYCor()
				+ grid.squareSize) - ((grid.getSquare(0, 0)).getYCor()));

		setRectHeight((int) (relativeGridHeight * VERT_MULTIPLIER));

		sideMargin = (grid.sideMargin - getRectWidth()) / 2;
	}

	public void setScore(int score) {
		SCORE = score;
	}
	
	public int getScore() {
		return SCORE;
	}
	
	public void displaySlider(){
		
	}

	@Override
	public void refresh() {
		/* TESTING REFRESH */
		pApplet.fill(bgColor[0], bgColor[1], bgColor[2]);
		pApplet.rect(sideMargin, grid.vertMargin, getRectWidth(), getRectHeight());
	}

	@Override
	public void create() {
		pApplet.fill(bgColor[0], bgColor[1], bgColor[2]);
		pApplet.rect(sideMargin, grid.vertMargin, getRectWidth(), getRectHeight());
	}
	
	public void updateScore() {
		int n = r.getNumOfLines();
		SCORE  += 40 * (n + 1) + 100 * (n + 1) + 300* (n + 1) + 1200 * (n + 1);
		r.setNumOfLines(0);
	}
}