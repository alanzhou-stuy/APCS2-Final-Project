package main;

import processing.core.PApplet;

public class Leaderboard extends RectangularElement {
	private Grid grid;
	private static final double SIDE_MULTIPLIER = .75;
	private static final double VERT_MULTIPLIER = .75;

	public Leaderboard(PApplet pApplet) {
		this(pApplet, null);

	}

	public Leaderboard(PApplet pApplet, Grid grid) {
		super(pApplet);
		this.grid = grid;

		setRectWidth((int) (grid.sideMargin * SIDE_MULTIPLIER));

		int relativeGridHeight = (((grid.getSquare(grid.getNumRows() - 1, grid.getNumCols() - 1)).getYCor()
				+ grid.squareSize) - ((grid.getSquare(0, 0)).getYCor()));

		setRectHeight((int) (relativeGridHeight * VERT_MULTIPLIER));

		sideMargin = grid.width - (grid.sideMargin - getRectWidth())/2 - getRectWidth();
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

}